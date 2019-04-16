package com.soyardee.elementaryGame.entity;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.level.Level;


public class Entity {

    public int x, y;
    private boolean removed = false;
    protected Level level;

    public void update() {

    }

    public void render(Screen screen) {

    }

    public void remove() {
        //remove entity from level
        removed = true;
    }

    public boolean isRemoved() {return removed;}
}
