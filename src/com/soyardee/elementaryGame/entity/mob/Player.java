package com.soyardee.elementaryGame.entity.mob;

import com.soyardee.elementaryGame.entity.particle.ParticleHandler;
import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.input.Keyboard;
import com.soyardee.elementaryGame.level.AsteroidField;
import com.soyardee.elementaryGame.level.StarField;

public class Player extends Mob {

    private int MAX_HP = 10;

    private Keyboard input;

    private Screen screen;

    private boolean upHold = false;
    private boolean downHold = false;
    private boolean requestQuestion = false;

    private int maxFireCount;
    public int fireCount;
    private int reloadCount, maxHP, currentHP;

    public int getCount = 0;
    private int totalScore = 0;

    public Player(int x, int y, int maxFireCount, int reloadCount, Keyboard input, Screen screen) {
        this.x = x;
        this.y = y;
        this.input = input;
        this.screen = screen;
        this.fireCount = 0;
        this.reloadCount = reloadCount;
        this.maxFireCount = maxFireCount;
        this.maxHP = MAX_HP;
        this.currentHP = MAX_HP;
    }

    public void reload() {
        fireCount = (fireCount + reloadCount > maxFireCount) ? maxFireCount : fireCount + reloadCount;
    }

    public void addTotal() {
        totalScore += getCount;
        getCount = 0;
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
            currentHP--;
        }

        if(starField.isOverlapPlayer(x, y, 32, 32)) {
            getCount+=3;
        }

        if(xa != 0) move(xa, ya);
    }

    public void render(Screen screen) {
        screen.renderPlayer(x, y, Sprite.player0, 0xFFFF00FF);
    }

    public int getHP() {return currentHP;}
    public int getMaxHP() {return maxHP;}
    public void rechargeHP() {currentHP = maxHP;}
    public int getGetCount() {return getCount;}
    public int getFireCount() {return fireCount;}
    public int getMaxFireCount() {return maxFireCount;}
    public boolean getRequestQuestion() {return requestQuestion;}
    public void increaseGetCount(int amount) { getCount += amount;}
    public int getTotalScore() {return totalScore;}
    public void clearShots() {fireCount = 0;}
}
