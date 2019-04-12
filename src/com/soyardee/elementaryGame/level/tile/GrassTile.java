package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;

public class GrassTile extends Tile {

    public GrassTile(Sprite sprite) {
        super(sprite);
    }

    public void render(int x, int y, Screen screen) {
        //render this particular tile to the screen
        screen.renderTile(x * 16, y * 16, this);
    }
}
