package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;

import java.util.Random;

public class Tile {

    public int x,y;
    public Sprite sprite;
    protected int speed;
    protected Random random;
    private boolean discard, isBelowTop, visible;

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public Tile (int startx, int starty, Sprite sprite) {
        this.x = startx;
        this.y = starty;
        discard = false;
        isBelowTop = starty <= 0;
        random = new Random();
        speed = 1;
        visible = true;
        this.sprite = sprite;
    }

    public void update(Screen screen) {
        y += speed;
        discard = y > screen.height;
        isBelowTop = y > 0;
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x *sprite.SIZE, y*sprite.SIZE, this);
    }

    public void render(Screen screen) {
        if(visible) screen.renderTile(x*sprite.SIZE, y, this, 0xffff00ff);
    }

    public boolean isDiscard() {
        return discard;
    }

    public boolean isBelowTop(){
        return isBelowTop;
    }

    public boolean isVisible(){return visible;}

    public void setVisible(boolean visible) {this.visible = visible;}

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean isSolid() {
        return false;
    }
}
