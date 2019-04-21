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
    private boolean downHold = false;
    private boolean requestQuestion = false;

    private int maxFireCount;
    public int fireCount;

    public int hitCount = 0;
    public int getCount = 0;

    public Player(int x, int y, int maxFireCount, Keyboard input, Screen screen) {
        this.x = x;
        this.y = y;
        this.input = input;
        this.screen = screen;
        this.fireCount = 0;
        this.maxFireCount = maxFireCount;
    }

    public void reload() {
        this.fireCount = maxFireCount;
    }

    public void update(AsteroidField asteroidField, StarField starField, ParticleHandler particle) {
        int xa = 0, ya = 0;

        requestQuestion = false;

        //the numbers are offset due to the border around the sprite
        if(input.left && x > -3) xa-=2;
        if(input.right && x+29 < screen.width) xa+=2;
        if(input.up && !upHold && fireCount > 0) {
            particle.createParticle(x+15, y, 5, 10);
            fireCount--;
            upHold = true;
        }
        if(input.down && !downHold) {
            requestQuestion = true;
            downHold = true;
        }
        upHold = input.up;
        downHold = input.down;

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
    public int getFireCount() {return fireCount;}
    public int getMaxFireCount() {return maxFireCount;}
    public boolean getRequestQuestion() {return requestQuestion;}
}
