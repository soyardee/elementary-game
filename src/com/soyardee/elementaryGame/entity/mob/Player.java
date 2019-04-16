package com.soyardee.elementaryGame.entity.mob;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.input.Keyboard;

public class Player extends Mob{

    private Keyboard input;

    private Screen screen;

    public Player (Keyboard input) {
        this.input = input;
    }

    public Player(int x, int y, Keyboard input, Screen screen) {
        this.x = x;
        this.y = y;
        this.input = input;
        this.screen = screen;
    }

    public void update() {
        int xa = 0, ya = 0;

        //y direction is not necessary, but I'm leaving it in.
        //if(input.up) ya--;
        //if(input.down) ya++;

        //the numbers are offset thanks to the border around the sprite
        if(input.left && x > -3) xa--;
        if(input.right && x+29 < screen.width) xa++;

        if(xa != 0 || ya != 0) move(xa, ya);
    }

    public void render(Screen screen) {
        //TODO make less stupid
        screen.renderPlayer(x, y, Sprite.player0);
        screen.renderPlayer(x+16, y, Sprite.player1);
        screen.renderPlayer(x, y+16, Sprite.player2);
        screen.renderPlayer(x+16, y+16, Sprite.player3);

    }
}
