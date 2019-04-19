package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Sprite;


public class Asteroid extends Tile {

    public Asteroid(int startx, int starty, Sprite sprite) {
        super(startx, starty, sprite);
        speed = random.nextInt(2) + 1;
    }
}
