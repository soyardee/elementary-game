package com.soyardee.elementaryGame.level;

//holds all the asteroids in memory and updates them all.

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.level.tile.Asteroid;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidField {

    private ArrayList<Asteroid> field;
    private Screen screen;
    public int maxOnScreen;
    public float spawnChance;
    private Random random;

    public AsteroidField(int maxOnScreen, float spawnChance, Screen screen) {
        this.maxOnScreen = maxOnScreen;
        this.spawnChance = spawnChance;
        this.screen = screen;
        field = new ArrayList<>();
        random = new Random();
    }

    public void update(){
        //if no asteroids in the field, create a new one

        boolean allowNewAsteroid = true;
        boolean chance = Math.random() < spawnChance;


        for(Asteroid a: field) {
            a.update(screen);
            if(!a.isOnScreen()) allowNewAsteroid = false;
        }


        if(field.size() < maxOnScreen && allowNewAsteroid && chance) {
            int xstart = random.nextInt(screen.width/16);
            field.add(new Asteroid(xstart, -16, Sprite.asteroid0));
        }

        field.removeIf(n -> (n.isDiscard()));
    }

    public void render() {
        for (Asteroid a: field){
            a.render(screen);
        }
    }


}