package com.example.dodgegame;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Enemy {
    private final int recWidth = 70;
    private final int recHeight = 50;
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private final double speed;
    public static double dy = 0;
    private final Rectangle rec;
    private static final Random rdm = new Random();

    public Enemy(){
        rec = new Rectangle(recWidth,recHeight,Color.RED);
        rec.setTranslateX(rdm.nextDouble()* (windowWidth-recWidth) - (windowWidth/2 - recWidth/2));
        System.out.println(rec.getTranslateX());
        rec.setTranslateY(0-windowHeight/2);

        this.speed = 5/2;
    }

    public Rectangle getNode(){
        return rec;
    }

    public void moveEnemy(){
        rec.setTranslateY(rec.getTranslateY() + speed);
    }

    public boolean shouldBeDestroyed() {
        return rec.getTranslateY() - recHeight/2 > windowHeight/2;
    }

    public boolean checkForCollision(Rectangle player)
    {
        double playerLeft = player.getTranslateX() - player.getWidth()/2;
        double playerRight = player.getTranslateX() + player.getWidth()/2;
        double playerTop = player.getTranslateY() - player.getHeight()/2;
        double playerBottom = player.getTranslateY() + player.getHeight()/2;

        double enemyLeft = rec.getTranslateX() - rec.getWidth()/2;
        double enemyRight = rec.getTranslateX() + rec.getWidth()/2;
        double enemyTop = rec.getTranslateY() - rec.getHeight()/2;
        double enemyBottom = rec.getTranslateY() + rec.getHeight()/2;

        boolean collisionX = playerRight >= enemyLeft && playerLeft <= enemyRight;
        boolean collisionY = playerBottom >= enemyTop && playerTop <= enemyBottom;

        return collisionX && collisionY;
    }
}
