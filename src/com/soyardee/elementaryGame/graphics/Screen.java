package com.soyardee.elementaryGame.graphics;

import com.soyardee.elementaryGame.entity.mob.Player;
import com.soyardee.elementaryGame.level.StarField;
import com.soyardee.elementaryGame.level.tile.Tile;

import java.util.Arrays;
import java.util.Random;

public class Screen {

    private final int MAP_SIZE = 16;
    private final int MAP_MASK = MAP_SIZE - 1;

    public int xOffset, yOffset;

    public int width, height;
    private int tileSize = MAP_SIZE/4;
    public int[] pixels;
    public int[] tiles = new int[MAP_SIZE*MAP_SIZE];

    private Random random = new Random();

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        //random tile colors
        for(int i = 0; i< MAP_SIZE*MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }


    public void bufferOut(int[] copyTo) {
        System.arraycopy(pixels, 0, copyTo, 0, pixels.length);
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }

    public void renderTile(int xPos, int yPos, Tile tile) {
        xPos -= xOffset;
        yPos -= yOffset;
        for (int y=0; y<tile.sprite.SIZE; y++) {
            int yAbs = y + yPos;
            for (int x=0; x<tile.sprite.SIZE; x++) {
                int xAbs = x + xPos;
                //only render what you can see
                if(xAbs < -tile.sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height) break;
                if(xAbs < 0) xAbs = 0;
                pixels[xAbs +yAbs*width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];

            }
        }
    }

    //MAKE SURE THE MASK HAS THE TRANSPARENCY VALUE ASSIGNED AT THE FRONT!
    public void renderTile(int xOffset, int yOffset, Tile tile, int mask){
        int starTileSize = tile.sprite.SIZE;
        for (int y=0; y<starTileSize; y++) {
            int dy = y + yOffset;
            for (int x=0; x<starTileSize; x++) {
                int dx = x + xOffset;
                if(dx < -starTileSize || dx >= width || dy < 0 || dy >= height) break;
                if(dx < 0) dx = 0;

                int col = tile.sprite.pixels[x + y*tile.sprite.SIZE];
                if(col != mask) {
                    pixels[dx + dy * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
                }

            }
        }
    }

    //PRE: array must have coordinates updated externally
    //render an array of colors in tile form
    public void renderField(int xOffset, int yOffset, int[] colorRange) {
        for(int y = 0; y < height; y++){
            int dy = y + yOffset;
            for(int x = 0; x < width; x++) {
                int dx = x + xOffset;
                //bitwise operators to generate the tile offset
                int tileIndex = ((dx >> (tileSize+1)) & MAP_MASK) + ((dy >> (tileSize+1)) & MAP_MASK) * (MAP_SIZE);
                pixels[x + y * width] = colorRange[tileIndex];
            }
        }
    }

    public void renderPlayer(int xPos, int yPos, Sprite sprite) {
        xPos -= xOffset;
        yPos -= yOffset;
        for (int y=0; y<16; y++) {
            int yAbs = y + yPos;
            for (int x=0; x<16; x++) {
                int xAbs = x + xPos;
                //only render what you can see
                if (xAbs < -16 || xAbs >= width || yAbs < 0 || yAbs >= height) break;
                if(xAbs < 0) xAbs = 0;
                int col = sprite.pixels[x + y * 16];

                if(col != 0xFFFF00FF)
                    pixels[xAbs +yAbs*width] = col;

            }
        }
    }


    public void setOffset(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
