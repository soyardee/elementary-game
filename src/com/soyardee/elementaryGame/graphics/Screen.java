package com.soyardee.elementaryGame.graphics;

import java.util.Arrays;
import java.util.Random;

public class Screen {

    private final int MAP_SIZE = 16;
    private final int MAP_MASK = MAP_SIZE - 1;

    private int width, height;
    private int tileSize = MAP_SIZE/4;
    public int[] pixels;
    public int[] tiles = new int[MAP_SIZE*MAP_SIZE];

    private Random random = new Random();

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];

        for(int i = 0; i< MAP_SIZE*MAP_SIZE; i++) {
            tiles[i] = random.nextInt(0xffffff);
        }
    }

    public void render(int xOffset, int yOffset) {
        for(int y = 0; y < height; y++){
            int dy = y + yOffset;
            //if(dy < 0 || dy >= height) break;
            for(int x = 0; x < width; x++) {
                int dx = x + xOffset;
                //if(dx < 0 || dx >= width) break;
                //perhaps use bitwise operators to generate the tile offset
                int tileIndex = ((dx >> tileSize) & MAP_MASK) + ((dy >> tileSize) & MAP_MASK) * (MAP_SIZE);
                pixels[x + y * width] = Sprite.grass.pixels[(dx&15) + (dy&15) * Sprite.grass.SIZE];
            }
        }
    }

    public void bufferOut(int[] copyTo) {
        System.arraycopy(pixels, 0, copyTo, 0, pixels.length);
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }
}
