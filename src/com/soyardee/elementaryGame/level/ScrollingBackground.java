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

    // a value of 0 (black) is ignored when called in the screen render method.
    public SpriteSheet sheet;
    public int x,y;
    public final int SIZE;
    private int yOffset = 0;
    private int xOffset = 0;
    private Random random = new Random();

    //representative of the size of the screen in tile size
    //TODO actually grab the size of the screen
    private int height = 20;
    private int width = 20;

    private int[] colorField = new int[height * width];
    private int[] starArrayOrder = new int[height * width];

    //starfield tiles in order
    public static Tile[] starArrayTiles = new Tile[(SpriteSheet.stars.SIZE * SpriteSheet.stars.SIZE) / 16];


    //load in a few different sprites from the provided sheet

    public ScrollingBackground(int size, int x, int y, SpriteSheet sheet) {
        this.SIZE = size;
        this.x = x;
        this.y = y;
        this.sheet = sheet;
        load();
    }

    public ScrollingBackground(int size) {
        this.SIZE = size;
        this.x = 0;
        this.y = 0;
        this.sheet = SpriteSheet.stars;
        load();
        generateField(colorField, 16);
        generateField(starArrayOrder, SIZE);
    }

    //load the specified segment of stars in from the file at the coordinate (x,y)
    public void load(){
        int bound = SpriteSheet.stars.SIZE / 16;
        for(int y = 0; y<bound; y++){
            for(int x = 0; x<bound; x++){
                starArrayTiles[x + y*bound] = new Tile(new Sprite(this.SIZE, x, y, SpriteSheet.stars));
            }
        }
    }

    //this works best when we can adapt to the size of the screen itself
    //it will work for now
    private void generateField(int[] arr, int bound){
        for(int i = 0; i<starArrayOrder.length; i++){
            arr[i] = random.nextInt(bound);
        }
    }

    public void update(){
        yOffset--;
    }

    public void render(Screen screen) {
        screen.renderField(x, yOffset, colorField);

        //pick out the tiles from the tiles storage class from the int array
        //screen.renderTileLoop(x, yOffset, starArrayOrder, 0xff000000);
    }

    public Tile getTile(int x, int y) {
        return starArrayTiles[starArrayOrder[x + y * height]];
    }

    public Tile getTile(int x) {
        return starArrayTiles[starArrayOrder[x]];
    }



}
