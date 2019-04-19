package com.soyardee.elementaryGame.level;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.level.tile.Star;

import java.util.ArrayList;
import java.util.Random;

public class StarField {

    private ArrayList<Star> starArrayList;
    private Screen screen;
    public int maxOnScreen;
    private int maxSpawnTime;
    private int minSpawnTime;
    private int randomSpawnTime;
    private int currentSpawnTime;
    private Random random;

    public StarField(int maxOnScreen, int maxSpawnTime, int minSpawnTime, Screen screen) {
        starArrayList = new ArrayList<>();
        this.maxOnScreen = maxOnScreen;
        this.maxSpawnTime = maxSpawnTime;
        this.minSpawnTime = minSpawnTime;
        this.screen = screen;
        random = new Random();
        randomSpawnTime = random.nextInt(maxSpawnTime-minSpawnTime) + minSpawnTime;
        currentSpawnTime = 0;

    }

    public void update() {

        for (Star s: starArrayList) {
            s.update(screen);
        }

        if(starArrayList.size() < maxOnScreen) {

            if (currentSpawnTime >= randomSpawnTime) {
                //add a new star
                int xStart = random.nextInt(screen.width/16);
                starArrayList.add(new Star(xStart, -16, Sprite.starLocked));

                currentSpawnTime = 0;
                randomSpawnTime = random.nextInt(maxSpawnTime - minSpawnTime) + minSpawnTime;
            } else {
                currentSpawnTime++;
            }
        }
        starArrayList.removeIf(n -> (n.isDiscard()));
    }

    public void render(Screen screen) {
        for(Star s: starArrayList) {
            s.render(screen);
        }
    }

    public boolean isOverlapPlayer(int x, int y, int width, int height) {
        for (Star s: starArrayList) {
            boolean intersects =
                    x+width > s.x * s.sprite.SIZE &&
                    x < s.x * s.sprite.SIZE + s.sprite.SIZE &&
                    y + height > s.y &&
                    y < s.y+s.sprite.SIZE;
            if(intersects && s.isVisible() && s.isUnlocked()){
                s.setVisible(false);
                return true;
            }
        }
        return false;
    }

    public boolean isOverlapParticle(int x, int y, int width, int height) {
        for (Star s: starArrayList) {
            boolean intersects =
                    x+width > s.x * s.sprite.SIZE &&
                            x < s.x * s.sprite.SIZE + s.sprite.SIZE &&
                            y + height > s.y &&
                            y < s.y+s.sprite.SIZE;
            if(intersects && s.isVisible() && !s.isUnlocked()){
                s.unlock();
                return true;
            }
        }
        return false;
    }
}
