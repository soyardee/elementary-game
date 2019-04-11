package com.soyardee.elementaryGame.graphics;

import java.util.Arrays;

public class Sprite {
    //size of the individual sprite, can span multiple spaces
    public final int SIZE;
    private int x,y;
    public int[] pixels;
    private SpriteSheet sheet;



    //static sprite types go here. Perhaps we can automate this?
    public static Sprite emptySprite = new Sprite(16, 0);
    public static Sprite grass = new Sprite(16, 0,0, SpriteSheet.tiles);

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
