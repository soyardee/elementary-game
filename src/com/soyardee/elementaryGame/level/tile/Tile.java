package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;

public class Tile {

    public int x,y;
    public Sprite sprite;


    public static Tile empty = new EmptyTile(Sprite.emptySprite);

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x *sprite.SIZE, y*sprite.SIZE, this);
    }

    public boolean isSolid() {
        return false;
    }
}
