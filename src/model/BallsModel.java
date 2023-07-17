package model;


import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import view.BallsView;

public class BallsModel {

    private static final double GRAVITY = 9.81;

    private double x;
    private double y;
    private double xVelocity;
    private double yVelocity;
    private double radius;


    public BallsModel(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.xVelocity = 0;
        this.yVelocity = 0;
        this.radius = radius;

    }

    public void update(double DeltaTime) {

        yVelocity += GRAVITY * DeltaTime;
        x += xVelocity * DeltaTime;
        y += yVelocity * DeltaTime;

        if (y + radius > BallsView.FLOOR) {
            y = BallsView.FLOOR - radius;
            yVelocity = -yVelocity * 0.8;  // Simulate bounce with some loss of energy
        }

    // Check for wall collisions
    if (x - radius < BallsView.WALL_LEFT + 1.5 * radius || x + 0.5 * radius > BallsView.WALL_RIGHT) {
        xVelocity = -xVelocity;
    }

    if (y - radius < BallsView.WALL_TOP || y + radius > BallsView.WALL_BOTTOM) {
        yVelocity = -yVelocity;
    }

            // Check for collisions with other balls
        for (BallsModel otherBall : BallsView.balls) {
            if (otherBall != this && collidesWith(otherBall)) {
                resolveCollision(otherBall);
            }
        }
    }

    private boolean collidesWith(BallsModel otherBall) {
        double distance = Math.sqrt(Math.pow((x - otherBall.x), 2) + Math.pow((y - otherBall.y), 2));
        return distance < (radius + otherBall.radius);
    }

    private void resolveCollision(BallsModel otherBall) {
        // Calculate new velocities based on the laws of physics
        double dx = otherBall.x - x;
        double dy = otherBall.y - y;
        double collisionAngle = Math.atan2(dy, dx);
        double magnitude1 = Math.sqrt(xVelocity * xVelocity + yVelocity * yVelocity);
        double magnitude2 = Math.sqrt(otherBall.xVelocity * otherBall.xVelocity + otherBall.yVelocity * otherBall.yVelocity);
        double direction1 = Math.atan2(yVelocity, xVelocity);
        double direction2 = Math.atan2(otherBall.yVelocity, otherBall.xVelocity);
    
        // Calculate new velocities
        double newVelocityX1 = magnitude1 * Math.cos(direction1 - collisionAngle);
        double newVelocityY1 = magnitude1 * Math.sin(direction1 - collisionAngle);
        double newVelocityX2 = magnitude2 * Math.cos(direction2 - collisionAngle);
        double newVelocityY2 = magnitude2 * Math.sin(direction2 - collisionAngle);
    
        // Apply the new velocities
        xVelocity = Math.cos(collisionAngle) * newVelocityX2 + Math.cos(collisionAngle + Math.PI/2) * newVelocityY1;
        yVelocity = Math.sin(collisionAngle) * newVelocityX2 + Math.sin(collisionAngle + Math.PI/2) * newVelocityY1;
        otherBall.xVelocity = Math.cos(collisionAngle) * newVelocityX1 + Math.cos(collisionAngle + Math.PI/2) * newVelocityY2;
        otherBall.yVelocity = Math.sin(collisionAngle) * newVelocityX1 + Math.sin(collisionAngle + Math.PI/2) * newVelocityY2;
    }

    Random rand = new Random();
    Color randomColor = new Color(rand.nextInt(0xFFFFFF));
    public void draw(Graphics2D g2) {

        int diameter = (int) (radius * 2);
        int x = (int) this.x - diameter;
        int y = (int) this.y - diameter;

        g2.setColor(randomColor);
        g2.fillOval(x, y, diameter, diameter);

    }

    public boolean ballCollision(ArrayList<BallsModel> balls) {
        for (BallsModel otherBall : balls) {
            if (otherBall == this) {
                continue; // Skip self-comparison
            }
            double distance = Math.sqrt(Math.pow((x - otherBall.x), 2) + Math.pow((y - otherBall.y), 2));
            if (distance < radius + otherBall.radius) {
                return true; // Collision detected
            }
        }
        return false; // No collision
    }


}