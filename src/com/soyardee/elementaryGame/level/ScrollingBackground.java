package com.soyardee.elementaryGame.level;

/*
 * The background starfield that is rendered behind everything
 */

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.graphics.SpriteSheet;
import com.soyardee.elementaryGame.level.tile.Tile;

import java.util.Random;

public class ScrollingBackground {
    public int x,y;
    public final int SIZE;
    private int yOffset = 0;
    private Random random = new Random();

    public final static int BLUE = 0;
    public final static int GREEN = 1;
    public final static int RED = 2;

    //representative of the size of the screen in tile size
    //TODO must be at least 16x16 I'm so sorry. Requires fix in the screen class.
    private int height = 16;
    private int width = 16;

    private int[] colorField = new int[height * width];

    public ScrollingBackground(int size, int color) {
        this.SIZE = size;
        this.x = 0;
        this.y = 0;
        generateField(colorField, 16, color);
    }

    //this works best when we can adapt to the size of the screen itself
    //it will work for now
    private void generateField(int[] arr, int bound, int startColor){
        for(int i = 0; i<arr.length; i++){
            arr[i] = random.nextInt(bound) << (startColor * 8);
        }
    }

    public void update(){
        yOffset--;
    }

    public void render(Screen screen) {
        screen.renderField(x, yOffset, colorField);
    }
}
