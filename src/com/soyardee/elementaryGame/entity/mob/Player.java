package com.soyardee.elementaryGame.entity.mob;

import com.soyardee.elementaryGame.entity.particle.ParticleHandler;
import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.input.Keyboard;
import com.soyardee.elementaryGame.level.AsteroidField;
import com.soyardee.elementaryGame.level.StarField;

public class Player extends Mob {

    private Keyboard input;

    private Screen screen;

    private boolean upHold = false;

    public int hitCount = 0;
    public int getCount = 0;

    public Player (Keyboard input) {
        this.input = input;
    }

    public Player(int x, int y, Keyboard input, Screen screen) {
        this.x = x;
        this.y = y;
        this.input = input;
        this.screen = screen;
    }

    public void update(AsteroidField asteroidField, StarField starField, ParticleHandler particle) {
        int xa = 0, ya = 0;


        //the numbers are offset due to the border around the sprite
        if(input.left && x > -3) xa-=2;
        if(input.right && x+29 < screen.width) xa+=2;
        if(input.up && !upHold) {
            particle.createParticle(x+15, y, 5, 10);
            upHold = true;
        }
        upHold = input.up;


        if(asteroidField.isOverlap(x+8, y, 16, 32)) {
            hitCount++;
        }

        if(starField.isOverlapPlayer(x, y, 32, 32)) {
            getCount++;
        }

        if(xa != 0) move(xa, ya);
    }

    public void render(Screen screen) {
        screen.renderPlayer(x, y, Sprite.player0, 0xFFFF00FF);
    }

    public int getHitCount() {return hitCount;}
    public int getGetCount() {return getCount;}
}
