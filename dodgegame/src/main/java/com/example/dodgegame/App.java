package com.example.dodgegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class App extends Application {
        private boolean upPressed = false;
        private boolean downPressed = false;
        private boolean leftPressed  =false;
        private boolean rightPressed = false;
        private final int recWidth = 70;
        private final int recHeight = 50;
        private final int windowWidth = 1400;
        private final int windowHeight = 800;
        private final double speed = 5;

        
        private double dy = 0;
        private double dx = 0;


    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, windowWidth, windowHeight,Color.rgb(90,96,99));

        Rectangle rec = new Rectangle(recWidth,recHeight,Color.rgb(25,55, 71));

        List<Enemy> enemies = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Enemy e = new Enemy(); 
            enemies.add(e);
            root.getChildren().add(e.getNode());
        }



        rec.setTranslateX(0);
        rec.setTranslateY(0);


        root.getChildren().add(rec);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W -> upPressed = true;
                case S -> downPressed = true;
                case A -> leftPressed = true;
                case D -> rightPressed = true;
            }
        });
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W -> upPressed = false;
                case S -> downPressed = false;
                case A -> leftPressed = false;
                case D -> rightPressed = false;
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now){
                dx = 0;
                dy = 0;
                if (upPressed)      dy -= 1;
                if( downPressed)    dy += 1;
                if(leftPressed)     dx -= 1;
                if(rightPressed)    dx += 1;

                // Normalize movement
                if(dx != 0 && dy != 0){
                    dx *= 1/Math.sqrt(2);
                    dy *= 1/Math.sqrt(2);
                }

                // Movement
                rec.setTranslateX(rec.getTranslateX() + dx * speed);
                rec.setTranslateY(rec.getTranslateY() + dy * speed);

                // Destroy Enemy if out of bounds
                for (Iterator<Enemy> it= enemies.iterator(); it.hasNext();) {
                    Enemy e = it.next();
                    e.moveEnemy();
                    if(e.shouldBeDestroyed()){
                        root.getChildren().remove(e.getNode());
                        it.remove();
                        System.out.println("Enemy Destroyed");
                    }
                    if(e.checkForCollision(rec)){
                        System.out.println("Player destroyed");
                    }
                }

                // Boundaries
                double minX = -windowWidth/2 + recWidth/2;
                double maxX = windowWidth/2  - recWidth/2;
                double minY = -windowHeight/2 + recHeight/2;
                double maxY = windowHeight/2 - recHeight/2;

                

                if(rec.getTranslateX()< minX) rec.setTranslateX(minX);
                if(rec.getTranslateX()> maxX) rec.setTranslateX(maxX);
                if(rec.getTranslateY()< minY) rec.setTranslateY(minY);
                if(rec.getTranslateY()> maxY) rec.setTranslateY(maxY);
            }
        };

        timer.start();
        stage.setTitle("Dodge Game");
        stage.setScene(scene);
        stage.show();
        
        root.requestFocus(); 
    }

    public static void main(String[] args) {
        launch();
    }
}