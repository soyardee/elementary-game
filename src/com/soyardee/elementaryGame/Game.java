package com.soyardee.elementaryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.soyardee.elementaryGame.entity.mob.Player;
import com.soyardee.elementaryGame.entity.particle.ParticleHandler;
import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.input.Keyboard;
import com.soyardee.elementaryGame.level.AsteroidField;
import com.soyardee.elementaryGame.level.ScrollingBackground;
import com.soyardee.elementaryGame.level.StarField;
import com.soyardee.questionParser.QuestionList;
import com.soyardee.questionPrompt.PromptHandler;


/*
 * The base engine code is done with assistance from TheChernoProject's java game programming
 * series where noted. (threads, custom screen class, and tile handling).
 * Otherwise, all game design was by me.
 * This program is in no way sufficiently advanced or optimized, but requires no additional
 * dependencies.
 *
 * If I had a few months, I'd consider learning the LWJGL API to create low level access calls to OpenGL for
 * far better performance if I were to program in java at all :P
 */

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    //the tick rate of the game
    private final int updateRate = 60;

    private int level = 0;

    private static int width = 300;
    private static int height = width / 16 * 9;
    private static int scale = 3;                //the multiplier for the upscaled resolution


    private int timeMax, currentTimeGlobal, pointsThreshold;

    private static String titleString = "Test Game";

    private Keyboard keyMap;

    private Thread thread;                      //the game subprocess
    private boolean running = false;
    private boolean pause = false;

    private Screen screen;
    private ScrollingBackground scrollingBG;
    private AsteroidField asteroidField;
    private StarField starField;
    private ParticleHandler particles;
    private Player player;
    private QuestionList questionList;
    private PromptHandler promptHandler;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    //convert the window raster into an array of integers
    private int[] pixelMap = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    //Interface elements
    private JFrame frame;


    public Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);
        screen = new Screen(width, height);
        keyMap = new Keyboard();

        setLevel(1);
    }

    private void initFrame() {
        frame = new JFrame();
        frame.addKeyListener(keyMap);
        frame.setResizable(true);
        frame.setTitle("Test Game");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //no overlaps in the call order
    public synchronized void start() {
        //mark the game as running to enter the gameloop
        running = true;
        //open this instance of the game in a new thread.
        thread = new Thread(this, "Display");
        //boot up the thread into a new instance
        thread.start();
    }

    //kill the thread instance
    //required for the runnable interface
    public synchronized void stop() {
        //stop the game loop when the stop command is called
        running = false;

        if(thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //the code executed when the game starts
    public void run() {
        initFrame();
        //get the precise time since the game start
        long lastTime = System.nanoTime();

        //for use in the update method
        long timer = System.currentTimeMillis();

        //calculated to the division of a second (ex 60 for 1/60th of a second)
        final double nano = 1000000000.0 / (double) updateRate;
        double delta = 0;
        int frames = 0;     //how many frames to render
        int updates = 0;    //how many times the update method is called


        //upon window creation, make sure that the user is active in this component
        //(for keyboard/mouse input)
        frame.requestFocus();

        boolean promptPause = false;


        //the game loop itself
        while(running) {
            if(!promptPause && !pause) {
                long currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / nano;
                lastTime = currentTime;
                while (delta >= 1) {
                    update();
                    updates++;
                    delta = 0;
                }
                render();
                frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    timer = System.currentTimeMillis();
                    currentTimeGlobal--;
                    frame.setTitle(titleString + "  |  " + updates + " ups, " + frames + " fps");

                    //fixes the bug where the user clicks away from the window when the question prompt is active
                    //also apparently bugs the user when it isn't focused. hmm.
                    frame.requestFocus();
                    updates = 0;
                    frames = 0;
                }
            }
            promptPause = promptHandler.isPaused();
        }
    }


    public void update() {
        scrollingBG.update();
        asteroidField.update();
        starField.update();
        keyMap.update();

        //looks a bit ugly, but I did not place an object reference into the collision handling
        //upon construction
        player.update(asteroidField, starField, particles);
        particles.update(asteroidField, starField, player);

        questionUpdate();

        //this runs when the timer ticks to 0
        checkWinCondition();
    }

    private void questionUpdate() {
        if(player.getRequestQuestion()) {
            promptHandler.openFrame();
            keyMap.refresh();
        }

        if(promptHandler.isCorrectAnswer()) {
            player.reload();
        }
    }



    public void render() {
        //get the parent buffering type from the awt canvas
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }


        //reset the screen calculations this frame
        screen.clear();

        //calculate the relations of the tiles and entities to the actual screen
        scrollingBG.render(screen);
        starField.render(screen);
        asteroidField.render(screen);
        player.render(screen);
        particles.render(screen);

        //once the pixels have been calculated in the screen class,
        //spit the resulting array into the global int array
        //to be used in the BufferedImage
        screen.bufferOut(pixelMap);

        //create a new graphics object with the same properties as the int pixel buffer
        Graphics g = bs.getDrawGraphics();

        //access the BufferedImage's pixel map and format it to the size of the screen
        g.drawImage(image, 0,0, getWidth(), getHeight(), null);

        //works but has a 10% performance hit each line :(
        //also we can't easily set the location of this in the game space.
        renderStringOverlay(g);

        //clear the current graphics buffer from memory
        g.dispose();

        //blit the buffer to the screen (in case of multiple buffers this frame)
        bs.show();
    }

    private void renderStringOverlay(Graphics g) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.white);

        g.drawString("Points: " + player.getGetCount(), 0, 30);
        g.drawString("Health: " + player.getHP() + "/" + player.getMaxHP() , 0, 60);
        g.drawString("Shots : " + player.getFireCount() + "/" + player.getMaxFireCount(), 0, 90);
        g.drawString("Time  : " + currentTimeGlobal, 0, 120);
    }

    private void checkWinCondition() {
        if(currentTimeGlobal <= 0) {
            pause = true;
            if (player.getGetCount() >= pointsThreshold) {
                endGameWin();
            }
            else
                endGame();
        }

        if(player.getHP() <= 0) {
            pause = true;
            endGame();
        }
    }

    //if the lose condition is met (or game is beaten), do this
    private void endGame() {
        JOptionPane.showMessageDialog(this, "Game Over");
        //enter hi score
        int replayOption = JOptionPane.showConfirmDialog(this, "replay?");
        if(replayOption == JOptionPane.YES_OPTION) {
            reset();
            setLevel(level);
        }
        else {
            System.exit(0);
        }

    }

    //if the win condition on a lower lever is met, do this
    private void endGameWin() {
        if(level < 3) {
            level++;
            JOptionPane.showMessageDialog(this, "Point goal met, advancing to level " + level);
            setLevel(level);
        }
        else if(level == 3) {
            JOptionPane.showMessageDialog(this, "You made it through all three levels! Good job!");
            endGame();
        }
        else {
            endGame();
        }
    }

    //level ranges from 1 to 3 for each level. Use the static game reference for clarity.
    //when loading a new level, you can pass the previous score in to continue counting up
    public void setLevel(int level) {
        this.level = level;

        pause = false;

        if(player == null) {
            player = new Player(width / 2 - 16, height - 32, 10, 5, keyMap, screen);
        }

        player.rechargeHP();
        keyMap.refresh();

        //I know, a really bad way to implement level loading :(
        //unsure how to proceed with storing the data, and this is horribly not scalable.
        //Will work for a second grade learning game though.
        switch(level) {
            case 1:
                timeMax = 30;
                currentTimeGlobal = timeMax;
                pointsThreshold = 10;
                scrollingBG = new ScrollingBackground(16, ScrollingBackground.BLUE);
                asteroidField = new AsteroidField(20, 0.1f, screen);
                starField = new StarField(2, 4*updateRate, 3*updateRate, screen.width, screen.height);
                particles = new ParticleHandler();
                questionList = new QuestionList("/questions/questions.xml");
                promptHandler = new PromptHandler(questionList);
                break;

            case 2:
                timeMax = 60;
                currentTimeGlobal = timeMax;
                pointsThreshold = 10;
                scrollingBG = new ScrollingBackground(16, ScrollingBackground.GREEN);
                asteroidField = new AsteroidField(20, 0.1f, screen);
                starField = new StarField(2, 4*updateRate, 3*updateRate, screen.width, screen.height);
                particles = new ParticleHandler();
                questionList = new QuestionList("/questions/questions.xml");
                promptHandler = new PromptHandler(questionList);
                break;

            case 3:
                timeMax = 5;
                currentTimeGlobal = timeMax;
                pointsThreshold = 10;
                scrollingBG = new ScrollingBackground(16, ScrollingBackground.RED);
                asteroidField = new AsteroidField(20, 0.1f, screen);
                starField = new StarField(2, 4*updateRate, 3*updateRate, screen.width, screen.height);
                particles = new ParticleHandler();
                questionList = new QuestionList("/questions/questions.xml");
                promptHandler = new PromptHandler(questionList);
                break;

            default:
                System.err.println("not a valid level number");
                break;
        }
    }

    private void reset() {
        player = new Player(width / 2 - 16, height - 32, 10, 5, keyMap, screen);
        level = 1;
    }
}
