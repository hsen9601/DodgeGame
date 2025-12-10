package com.example.dodgegame;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;



public class Player {
    
    private static final Random rdm = new Random();

    private final ImageView player;      

    public final double speed = 4;
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private final int padding = 10;


    private final int playerWidth = 50;
    private final int playerHeight = 50;
    private final Polygon hitbox;

    public Player() {

        Image image = new Image(getClass().getResource("/player.png").toExternalForm());


        player = new ImageView(image);

        player.setTranslateX(0);
        player.setTranslateY(0);
        
        player.setFitHeight(playerHeight);
        player.setFitWidth(playerWidth);

        player.setTranslateX(0- getWidth(player)/2);
        player.setTranslateY(windowHeight - getHeight(player)/2);


        hitbox = createRegularPolygon(3,20,Color.RED);
        hitbox.setTranslateX(player.getTranslateX());
        hitbox.setTranslateY(player.getTranslateY());
        hitbox.setFill(null);        
        hitbox.setStroke(Color.RED);  
        hitbox.setStrokeWidth(2);

    }

    public ImageView getNode() {
        return player;
    }

    // Helper: Dynamic width for rectangle/circle
    public final double getWidth(ImageView s) {
        return s.getBoundsInLocal().getWidth();

    }
    public final double getHeight(ImageView s) {
        return s.getBoundsInLocal().getHeight();
    }


    private Polygon createRegularPolygon(int sides, double radius, Color color) {
        Polygon p = new Polygon();
        p.setFill(color);

        for (int i = 0; i < sides; i++) {
            double angle = Math.toRadians(-90 + i * (360.0 / sides));
            double x = radius * Math.cos(angle);
            double y = radius * Math.sin(angle);
            p.getPoints().addAll(x, y);
        }
        return p;
    }
    public Polygon getHitbox() {
        return hitbox;
    }
    
    public void move(double dx, double dy) {
        player.setTranslateX(player.getTranslateX() + dx);
        player.setTranslateY(player.getTranslateY() + dy);

        hitbox.setTranslateX(player.getTranslateX());
        hitbox.setTranslateY(player.getTranslateY());
    }
    public void clampPosition(int windowWidth, int windowHeight) {
        double minX = -windowWidth / 2 + getWidth(player) / 2;
        double maxX = windowWidth / 2 - getWidth(player) / 2;
        double minY = -windowHeight / 2 + getHeight(player) / 2;
        double maxY = windowHeight / 2 - getHeight(player) / 2;

        if (player.getTranslateX() < minX) player.setTranslateX(minX);
        if (player.getTranslateX() > maxX) player.setTranslateX(maxX);
        if (player.getTranslateY() < minY) player.setTranslateY(minY);
        if (player.getTranslateY() > maxY) player.setTranslateY(maxY);

        hitbox.setTranslateX(player.getTranslateX());
        hitbox.setTranslateY(player.getTranslateY());
    }
    
}
