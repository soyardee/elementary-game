package com.soyardee.elementaryGame.entity.particle;

import com.soyardee.elementaryGame.entity.Entity;
import com.soyardee.elementaryGame.graphics.Sprite;

public class Particle extends Entity {

    public Sprite sprite;

    //remember, speed is in terms of ticks. Usually set to 60px/s at speed = 1.
    public double speed, life;
    //double precision
    public double xx, yy;

    public Particle(int x, int y, int speed, int life, Sprite sprite) {
        this.sprite = sprite;
        this.xx = x;
        this.yy = y;
        this.speed = speed;
        this.life = life;
    }
}
