package com.example.dodgegame;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Player {
    
    private static final Random rdm = new Random();

    private final Shape shape;        
    private final double speed;
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private final int padding = 10;


    private final int playerWidth = 50;
    private final int playerHeight = 50;

    public Player() {

        double random = rdm.nextDouble();

        double leftLimit = -windowWidth/2 + padding + playerWidth/2;
        double rightLimit = windowWidth/2 - padding - playerHeight/2;

        Rectangle player = new Rectangle(playerWidth,playerHeight,Color.RED);

        
        
        player.setTranslateX(0- getWidth(player)/2);
        player.setTranslateY(windowHeight - getHeight(player)/2);

        shape = player;
        this.speed = 4;
    }

    public Shape getNode() {
        return shape;
    }

    // Helper: Dynamic width for rectangle/circle
    public double getWidth(Shape s) {
        return s.getBoundsInLocal().getWidth();

    }

    public double getHeight(Shape s) {
        return s.getBoundsInLocal().getHeight();
    }
}
