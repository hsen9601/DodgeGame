package com.example.dodgegame;

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
        private int recWidth = 100;
        private int recHeight = 50;
        private int windowWidth = 1400;
        private int windowHeight = 800;
        private double dy = 0;
        private double dx = 0;


    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, windowWidth, windowHeight);

        Rectangle rec = new Rectangle(recWidth,recHeight,Color.BLUE);

        
        
        double speed  = 5;
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
                dy  = 0;
                if (upPressed) dy -=1;
                if( downPressed) dy +=1;
                if(leftPressed) dx-=1;
                if(rightPressed) dx+=1;

                if(dx != 0 && dy != 0){
                    dx *= 1/Math.sqrt(2);
                    dy *= 1/Math.sqrt(2);
                }

                rec.setTranslateX(rec.getTranslateX() + dx * speed);
                rec.setTranslateY(rec.getTranslateY() + dy * speed);

                if(rec.getTranslateX()< -windowWidth/2 + recWidth/2){
                    rec.setTranslateX(-700+ rec.getWidth()/2);
                }
                if(rec.getTranslateX()> windowWidth/2 - recWidth/2){
                    rec.setTranslateX(700- rec.getWidth()/2);
                }
                if(rec.getTranslateY()< -windowHeight/2 + recHeight/2){
                    rec.setTranslateY(-400+ rec.getHeight()/2);
                }
                if(rec.getTranslateY()> windowHeight/2 - recHeight/2){
                    rec.setTranslateY(400- rec.getHeight()/2);
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