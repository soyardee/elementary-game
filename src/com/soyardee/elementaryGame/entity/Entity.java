package com.soyardee.elementaryGame.entity;

import com.soyardee.elementaryGame.graphics.Screen;


public class Entity {

    public int x, y;
    private boolean removed = false;
    protected boolean isVisible;

    public void update() {

    }

    public void render(Screen screen) {

    }

    public void remove() {
        //remove entity from level
        removed = true;
    }

    public boolean isVisible() { return isVisible;}
    public void setVisible(boolean visible) {this.isVisible = visible;}

    public boolean isRemoved() {return removed;}
    public void setRemoved(){this.removed = true;}
}
