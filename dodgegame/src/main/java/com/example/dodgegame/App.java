package com.example.dodgegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class App extends Application {
        private boolean upPressed = false;
        private boolean downPressed = false;
        private boolean leftPressed  =false;
        private boolean rightPressed = false;
        private final int windowWidth = 800;
        private final int windowHeight = 600;
        
        private double dy = 0;
        private double dx = 0;


    @Override
    public void start(Stage stage) {
        // Starting Scene
        StackPane root = new StackPane();
        Scene scene = new Scene(root, windowWidth, windowHeight,Color.rgb(10,15,31));

        // Creating Player
        Player player  = new Player();
        root.getChildren().add(player.getNode());

        root.getChildren().add(player.getHitbox());
        // Creating Enemies
        List<Enemy> enemies = new ArrayList<>();
        Timeline enemySpawner = new Timeline(new KeyFrame(Duration.millis(1500), event ->{
            Enemy newEnemy = new Enemy();
            if(enemies.size()<20){

            enemies.add(newEnemy);
            root.getChildren().add(newEnemy.getNode());
            }
        }));
        enemySpawner.setCycleCount(Timeline.INDEFINITE);
        enemySpawner.play();


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
                player.move(dx * player.speed, dy * player.speed);
                //Boundaries
                player.clampPosition(windowWidth, windowHeight);

                // Destroy Enemy if out of bounds
                for (Iterator<Enemy> it= enemies.iterator(); it.hasNext();) {
                    Enemy e = it.next();
                    e.moveEnemy();
                    if(e.shouldBeDestroyed()){
                        root.getChildren().remove(e.getNode());
                        it.remove();
                        System.out.println("Enemy Destroyed");
                    }
                    if(e.checkForCollision(player)){
                        System.out.println("Player destroyed");
                        stage.close();
                    }
                }

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