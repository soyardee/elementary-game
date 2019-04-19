package com.soyardee.elementaryGame.entity;

import com.soyardee.elementaryGame.graphics.Screen;


public class Entity {

    public int x, y;
    private boolean removed = false;

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
