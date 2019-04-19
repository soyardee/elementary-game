package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Sprite;

public class Star extends Tile {


    //needs to be shot at to unlock
    private boolean isUnlocked;


    public Star(int startx, int starty, Sprite sprite) {
        super(startx, starty, sprite);
        isUnlocked = false;
    }

    public boolean isUnlocked() {return isUnlocked;}
    public void unlock() {
        isUnlocked = true;
        this.setSprite(Sprite.starUnlocked);
    }
}
