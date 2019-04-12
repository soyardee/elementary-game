package com.soyardee.elementaryGame.entity.mob;

import com.soyardee.elementaryGame.entity.Entity;
import com.soyardee.elementaryGame.graphics.Sprite;

public abstract class Mob extends Entity {

    protected Sprite sprite;
    protected int dir = 0;

    protected boolean moving = false;

    public void move(int xPos, int yPos) {

        if(xPos > 0) dir = 1;
        if(xPos < 0) dir = 3;
        if(yPos > 0) dir = 2;
        if(yPos < 0) dir = 0;

        //handle collision before allowing to move
        if(!collision()) {
            x+=xPos;
            y+=yPos;
        }
    }

    public void update() {

    }

    public void render() {

    }

    private boolean collision() {
        return false;
    }

}
