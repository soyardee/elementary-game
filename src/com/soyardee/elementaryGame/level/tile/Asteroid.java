package com.soyardee.elementaryGame.level.tile;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;

import java.util.Random;

public class Asteroid extends Tile {

    private int speed;
    private Random random;
    private boolean discard, onScreen;

    public Asteroid (int startx, int starty, Sprite sprite){
        super(sprite);
        this.x = startx;
        this.y = starty;
        discard = false;
        onScreen = starty <= 0;
        random = new Random();
        speed = random.nextInt(2) + 1;
    }

    public void update(Screen screen) {
        y += speed;
        discard = y > screen.height;
        onScreen = y > 0;
    }

    public void render(Screen screen) {
        screen.renderTile(x*sprite.SIZE, y, this, 0xffff00ff);
    }

    public boolean isDiscard() {
        return discard;
    }

    public boolean isOnScreen(){
        return onScreen;
    }
}
