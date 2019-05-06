package com.soyardee.elementaryGame.graphics;

import java.util.Arrays;

public class Sprite {
    //size of the individual sprite, can span multiple spaces
    public final int SIZE;
    private int x,y;
    public int[] pixels;
    private SpriteSheet sheet;

    //entity sprites
    public static Sprite player0 = new Sprite(32, 0, 1, SpriteSheet.tiles);

    //asteroid sprites
    public static Sprite asteroid0 = new Sprite(16, 0, 0, SpriteSheet.tiles);
    public static Sprite starLocked = new Sprite(16, 0, 1, SpriteSheet.tiles);
    public static Sprite starUnlocked = new Sprite(16, 1, 1, SpriteSheet.tiles);

    //particle sprites
    public static Sprite particle0 = new Sprite(3, 0xFF0000);



    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        SIZE = size;
        pixels = new int[SIZE*SIZE];

        this.x = x*SIZE;
        this.y = y*SIZE;
        this.sheet = sheet;
        load();
    }

    public Sprite(int size, int color) {
        SIZE = size;
        pixels = new int[SIZE*SIZE];
        setColor(color);
    }

    private void setColor(int color) {
        Arrays.fill(pixels, color);
    }

    private void load() {
        for(int y = 0; y<SIZE; y++) {
            for(int x = 0; x< SIZE; x++) {
                pixels[x+y*SIZE] = sheet.pixels[(x+this.x) + (y+this.y)*sheet.SIZE];
            }
        }
    }
}
