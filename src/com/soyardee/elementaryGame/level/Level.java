package com.soyardee.elementaryGame.level;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.level.tile.Tile;

public class Level {

    protected int width, height;          //the size of the level
    protected int[] tiles;                  //data member array

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new int[width*height];
        createLevel();
    }

    public Level(String path) {
        loadLevel(path);
    }

    private void loadLevel(String path) {
        //read the width and height from the file itself
    }

    protected void createLevel() {
    }

    protected void update() {

    }

    public void render(int xScroll, int yScroll, Screen screen) {
        //set the offset to the actual location of the player
        screen.setOffset(xScroll, yScroll);

        //TODO adjust tile size scale as variable
        int x0 = xScroll / 16;          //the size scale of the tile at the leftmost position
        int x1 = (xScroll + screen.width + 16) / 16; //rightmost position in tile scale
        int y0 = yScroll / 16;                  //topmost position in tile scale
        int y1 = (yScroll + screen.height + 16) /16; //bottom position in tile scale

        for(int y = y0; y < y1; y++) {
            for (int x = x0; x <x1; x++) {
                //processing the map tile by tile and calling its individual render method.
                getTile(x,y).render(x, y, screen);
            }
        }

    }

    public Tile getTile(int x, int y) {
        if(x < 0 || y< 0 || x >= width || y>=height) return Tile.empty;
        if(tiles[x+y * width] == 0) return Tile.grass;
        return Tile.empty;
    }
}
