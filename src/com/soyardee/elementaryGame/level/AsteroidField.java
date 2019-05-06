package com.soyardee.elementaryGame.level;

//holds all the asteroids in memory and updates them all.

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.level.tile.Asteroid;

import java.util.ArrayList;
import java.util.Random;

public class AsteroidField {

    private ArrayList<Asteroid> asteroidArrayList;
    private Screen screen;
    public int maxOnScreen;
    private int minSpeed;
    public float spawnChance;
    private Random random;
    private boolean wait;

    public AsteroidField(int maxOnScreen, float spawnChance, int minSpeed, boolean wait, Screen screen) {
        this.maxOnScreen = maxOnScreen;
        this.spawnChance = spawnChance;
        this.minSpeed = minSpeed;
        this.screen = screen;
        this.wait = wait;
        asteroidArrayList = new ArrayList<>();
        random = new Random();
    }

    public void update(){
        //if no asteroids/stars in the asteroidArrayList, create a new one

        boolean allowNewAsteroid = true;
        boolean chance = Math.random() < spawnChance;


        for(Asteroid a: asteroidArrayList) {
            a.update(screen.height);
            allowNewAsteroid = a.isBelowTop() || !wait;
        }

        if(asteroidArrayList.size() < maxOnScreen && allowNewAsteroid && chance) {
            int xstart = random.nextInt((screen.width/16)+1);
            if(minSpeed == 0) {
                asteroidArrayList.add(new Asteroid(xstart, -16, Sprite.asteroid0));
            }
            else{
                asteroidArrayList.add(new Asteroid(xstart, -16, minSpeed, Sprite.asteroid0));
            }
        }

        asteroidArrayList.removeIf(n -> (n.isDiscard()));
    }

    public void render(Screen screen) {
        for (Asteroid a: asteroidArrayList){
            a.render(screen);
        }
    }

    public boolean isOverlap(int x, int y, int width, int height) {
        for(Asteroid a : asteroidArrayList) {
            int rectOneRight = x + width;
            int rectOneLeft = x;
            int rectOneTop = y;
            int rectOneBottom = y+height;

            int rectTwoRight = a.x * a.sprite.SIZE + a.sprite.SIZE;
            int rectTwoLeft = a.x * a.sprite.SIZE;
            int rectTwoTop = a.y;
            int rectTwoBottom = a.y+a.sprite.SIZE;

            boolean intersects =
                    rectOneRight > rectTwoLeft
                    && rectOneLeft < rectTwoRight
                    && rectOneBottom > rectTwoTop
                    && rectOneTop < rectTwoBottom;

            if(intersects && a.isVisible()) {
                //destroy animation here
                a.setVisible(false);
                return true;
            }
        }
        return false;
    }


}
