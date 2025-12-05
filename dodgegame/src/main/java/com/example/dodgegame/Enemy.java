package com.example.dodgegame;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Enemy {
    private final int recWidth = 70;
    private final int recHeight = 50;
    private final int windowWidth = 1400;
    private final int windowHeight = 800;
    private final double speed;
    public static double dy = 0;
    private final Rectangle rec;
    private static final Random rdm = new Random();

    public Enemy(){
        rec = new Rectangle(recWidth,recHeight,Color.RED);
        rec.setTranslateX(rdm.nextDouble()* (windowWidth-recWidth) - (windowWidth/2 - recWidth/2));
        System.out.println(rec.getTranslateX());
        rec.setTranslateY(0-windowHeight/2);

        this.speed = 1 + rdm.nextDouble() * 1.5;
    }

    public Rectangle getNode(){
        return rec;
    }

    public void moveEnemy(){
        rec.setTranslateY(rec.getTranslateY() + speed /5);
    }

    public boolean shouldBeDestroyed() {
    return rec.getTranslateY() - recHeight/2 > windowHeight/2;
    }

    
}
