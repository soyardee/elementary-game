package com.soyardee.elementaryGame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

//custom code
import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.input.Keyboard;


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
    int tileDx = 0, tileDy = 0;

    private int updateRate = 60;

    private static int width = 300;
    private static int height = width / 16 * 9;
    private static int scale = 3;                //the multiplier for the upscaled resolution

    private static String titleString = "Test Game";

    private Keyboard keyMap;

    private Thread thread;                      //the game subprocess
    private boolean running = false;            //watch out for global declaration

    private Screen screen;

    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    //convert the window raster into an array of integers
    private int[] pixelMap = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    //Interface elements
    private JFrame frame;

    public Game() {
        Dimension size = new Dimension(width*scale, height*scale);
        setPreferredSize(size);

        screen = new Screen(width, height);
        frame = new JFrame();
        keyMap = new Keyboard();
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
        long timer = System.currentTimeMillis();
        final double nano = 1000000000.0 / (double) updateRate;
        double delta = 0;
        int frames = 0;     //how many frames to render
        int updates = 0;    //how many times the update method is called


        frame.requestFocus();

        while(running) {
            long currentTime = System.nanoTime();
            //calculated to the division of a second (ex 60 for 1/60th of a second)
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

        if(keyMap.up) tileDy--;
        if(keyMap.down) tileDy++;
        if(keyMap.left) tileDx--;
        if(keyMap.right) tileDx++;
    }

    public void render() {
        //get the parent buffering type from the awt canvas
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }


        screen.clear();
        //calculate the pixel colors and stuff
        screen.render(tileDx,tileDy);

        //once the pixels have been calculated, spit that out into the global int array
        //to be used in the BufferedImage
        screen.bufferOut(pixelMap);

        //feed some data into the buffers from a graphics object

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0,0, getWidth(), getHeight());

        g.drawImage(image, 0,0, getWidth(), getHeight(), null);

        //clear the current graphics buffer from memory
        g.dispose();

        //blit the buffer to the screen
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
