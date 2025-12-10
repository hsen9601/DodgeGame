package com.example.dodgegame;

import java.util.Random;

import com.example.dodgegame.Enemy.EnemyType;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Enemy {
    public enum EnemyType {
        CIRCLE,
        RECT,
        PENTAGON,
        TRIANGLE
    }

    private static final Random rdm = new Random();

    private final Shape shape;
    private final double speed;
    private final int windowWidth = 800;
    private final int windowHeight = 600;
    private final double padding = 10;

    public Enemy() {

        double randomSpawn = rdm.nextDouble();
        double random = rdm.nextDouble();

        EnemyType type;

        if (randomSpawn < 0.25)
            type = EnemyType.CIRCLE;

        else if (randomSpawn < 0.50 && randomSpawn > 0.25)
            type = EnemyType.PENTAGON;

        else if (randomSpawn < 0.75 && randomSpawn > 0.50)
            type = EnemyType.TRIANGLE;
        else
            type = EnemyType.RECT;

        this.shape = createShape(type);
        double shapeWidth = getWidth(shape);
        double leftLimit = -windowWidth / 2 + padding + shapeWidth / 2;
        double rightLimit = windowWidth / 2 - padding - shapeWidth / 2;

        double spawnPointX = leftLimit + random * (rightLimit - leftLimit);

        double spawnPointY = -windowHeight / 2 + getHeight(shape) / 2;
        shape.setTranslateX(spawnPointX);
        shape.setTranslateY(spawnPointY);

        this.speed = 0.1;
    }

    private Shape createShape(EnemyType type) {

        return switch (type) {
            case CIRCLE -> {
                Circle c = new Circle(20, Color.rgb(255, 198, 43));
                c.setStroke(Color.WHITE);
                c.setStrokeWidth(2);
                yield c;
            }

            case RECT -> {
                Rectangle r = new Rectangle(40, 40, Color.rgb(255, 59, 59));
                r.setStroke(Color.WHITE);
                r.setStrokeWidth(2);
                yield r;
            }

            case PENTAGON -> {
                Polygon poly = createRegularPolygon(
                        5, // 5 Ecken
                        20, // Radius
                        Color.rgb(162, 89, 255) // Lila Gegner-Farbe
                );
                poly.setStroke(Color.WHITE);
                poly.setStrokeWidth(2);
                yield poly;
            }

            case TRIANGLE -> {
                Polygon poly = createRegularPolygon(
                        3,
                        20,
                        Color.rgb(77, 168, 255));
                poly.setStroke(Color.WHITE);
                poly.setStrokeWidth(2);
                yield poly;
            }
        };
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

    public Shape getNode() {
        return shape;
    }

    public void moveEnemy() {
        shape.setTranslateY(shape.getTranslateY() + speed);
    }

    public boolean shouldBeDestroyed() {
        return shape.getTranslateY() - getHeight(shape) / 2 > windowHeight / 2;
    }

    // Kollision
    public boolean checkForCollision(Player player) {
        Shape intersection = Shape.intersect(shape, player.getHitbox());
        if (intersection.getBoundsInLocal().getWidth() != -1) { // Wenn interseption existiert
            return true;
        }
        return false;
    }
    
    private double getWidth(Shape s) {
        return s.getBoundsInLocal().getWidth();

    }

    private double getHeight(Shape s) {
        return s.getBoundsInLocal().getHeight();
    }
}
