package com.soyardee.elementaryGame.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


//Helper class to load the sprite sheet into memory
public class SpriteSheet {

    private String path;
    public final int SIZE;
    public int[] pixels;

    //SpriteSheets paths and callable constructors go here
    public static SpriteSheet tiles = new SpriteSheet("/tex/sprites.png", 256);
    public static SpriteSheet stars = new SpriteSheet("/tex/starfield.png", 64);

    public SpriteSheet(String path, int size) {
        this.SIZE = size;
        this.path = path;

        pixels = new int[SIZE * SIZE];
        load();
    }

    private void load() {
        try {
            BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
            int w = image.getWidth();
            int h = image.getHeight();
            //translates the image to the pixels
            image.getRGB(0,0,w,h, pixels, 0, w);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
