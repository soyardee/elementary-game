package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;

public class EmptyTile extends Tile {
    public EmptyTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        screen.renderTile(x*16, y*16, this);
    }
}
