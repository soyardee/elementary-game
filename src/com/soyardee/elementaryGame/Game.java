package com.soyardee.elementaryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

//custom code
import com.soyardee.elementaryGame.entity.mob.Player;
import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.input.Keyboard;
import com.soyardee.elementaryGame.level.Level;
import com.soyardee.elementaryGame.level.RandomLevel;


/*
 * The base engine code is done with assistance from TheChernoProject's java game programming
 * series where noted. Otherwise, all game design was by me.
 * This program is in no way sufficiently advanced or optimized, but requires no additional
 * dependencies.
 *
 * If I had a few months, I'd consider learning the LWJGL API to create low level access calls to OpenGL for
 * far better performance.
 */

public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    //simple tile animation
    //TODO remove class reference

    private int updateRate = 60;

    private static int width = 300;
    private static int height = width / 16 * 9;
    private static int scale = 3;                //the multiplier for the upscaled resolution

    private static String titleString = "Test Game";

    private Keyboard keyMap;

    private Thread thread;                      //the game subprocess
    private boolean running = false;            //watch out for global declaration

    private Screen screen;
    private Level level;
    private Player player;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    //convert the window raster into an array of integers
    private int[] pixelMap = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    //Interface elements
    private JFrame frame;

    public Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        level = new RandomLevel(64, 64);
        keyMap = new Keyboard();
        player = new Player(keyMap);
        frame = new JFrame();
        frame.addKeyListener(keyMap);
    }

    //no overlaps in the call order
    public synchronized void start() {
        //start running the game
        running = true;
        //open the instance of the game in a new thread.
        thread = new Thread(this, "Display");
        //boot up the thread into a new instance
        thread.start();
    }

    //kill the thread instance
    public synchronized void stop() {
        //stop the game loop when the stop command is called
        running = false;
        try {
            thread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //the code executed when the game starts
    public void run() {
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

        //the game loop itself
        while(running) {
            long currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / nano;
            lastTime = currentTime;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(titleString + "  |  " + updates + " ups, " + frames + " fps");
                updates = 0;
                frames = 0;
            }
        }
    }

    public void update() {
        keyMap.update();

        //TODO update the game with all the mobs
        player.update();
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

        //center the player sprite into the center of the screen
        int xScroll = player.x - screen.width / 2;
        int yScroll = player.y - screen.height / 2;

        //calculate the relations of the tiles and entities to the actual screen
        level.render(xScroll, yScroll, screen);
        player.render(screen);

        //once the pixels have been calculated in the screen class,
        //spit the resulting array into the global int array
        //to be used in the BufferedImage
        screen.bufferOut(pixelMap);

        //create a new graphics object with the same properties as the int pixel buffer
        Graphics g = bs.getDrawGraphics();

        //access the BufferedImage's pixel map and format it to the size of the screen
        g.drawImage(image, 0,0, getWidth(), getHeight(), null);

        //clear the current graphics buffer from memory
        g.dispose();

        //blit the buffer to the screen (in case of multiple buffers this frame)
        bs.show();

    }

    //TODO DEFINITELY REFACTOR
    public static void main(String[] args) {
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle("Test Game");
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);

        game.start();
    }


}
