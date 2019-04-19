package com.soyardee.elementaryGame.entity.particle;

import com.soyardee.elementaryGame.graphics.Screen;
import com.soyardee.elementaryGame.graphics.Sprite;
import com.soyardee.elementaryGame.level.AsteroidField;
import com.soyardee.elementaryGame.level.StarField;

import java.util.ArrayList;
import java.util.List;

public class ParticleHandler {
    private List<Particle> particles = new ArrayList<>();

    public void update(AsteroidField asteroidField, StarField starField) {
        for(Particle p : particles) {
            p.yy -= p.speed;
            boolean offScreen = p.yy < 0-p.sprite.SIZE;
            boolean asteroidFieldOverlap = asteroidField.isOverlap((int)p.xx, (int)p.yy, p.sprite.SIZE, p.sprite.SIZE);
            boolean starFieldOverlap = starField.isOverlapParticle((int)p.xx, (int)p.yy, p.sprite.SIZE, p.sprite.SIZE);

            if(offScreen || asteroidFieldOverlap || starFieldOverlap){
                p.setRemoved();
            }
        }

        particles.removeIf(n -> (n.isRemoved()));
    }

    public void render(Screen screen) {
        for (Particle p : particles) {
            screen.renderSprite((int) p.xx, (int) p.yy, p.sprite);
        }
    }

    public void createParticle(int x, int y, int speed, int life) {
        particles.add(new Particle(x, y, speed, life, Sprite.particle0));
    }
}
