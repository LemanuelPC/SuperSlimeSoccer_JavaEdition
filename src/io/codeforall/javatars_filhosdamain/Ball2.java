package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Ball2 {
    Ellipse ellipse;
    Vector position;
    Vector velocity;
    double diameter;

    public Ball2(double xTopLeftCorner, double yTopLeftCorner, double diameter) {
        this.ellipse = new Ellipse(xTopLeftCorner, yTopLeftCorner, diameter, diameter);
        this.position = new Vector(xTopLeftCorner + diameter/2, yTopLeftCorner);
        this.velocity = new Vector(0, 0); // Start with no initial velocity
        this.diameter = diameter;
    }

    // Method to update the ball's position based on its velocity
    public void update(Field field, Player2[] pArray) {
        // Apply velocity to position
        double nextX = position.x + velocity.x;
        double nextY = position.y + velocity.y;

        // Adjust for the radius of the ball for boundary checks
        double radius = diameter / 2;

        // Left boundary
        if (nextX < 10 + radius) {
            velocity.x *= -0.5; // Stop horizontal movement
            nextX = 10 + radius; // Adjust position to boundary
        }
        // Right boundary
        if (nextX > field.height  - radius) {
            velocity.x *= -0.5; // Stop horizontal movement
            nextX = field.height - radius; // Adjust position
        }
        // Bottom boundary
        if (intersects(pArray[0])){
            //velocity.y *= 0; // Stop vertical movement
            nextY = field.getObjectPosition(pArray[0].position).y - diameter - 10; // Adjust position
        }
        if (intersects(pArray[1])){
            velocity.y *= -0.5; // Stop vertical movement
            nextY = field.getObjectPosition(pArray[1].position).y - diameter - 10; // Adjust position
        }
        if(!intersects(pArray[0]) && !intersects(pArray[1])) {
            if (nextY > field.height - radius) {
                velocity.y *= -0.5; // Stop vertical movement
                nextY = field.height - radius; // Adjust position
            }
        }
        // Top boundary
        if (nextY < 10 + radius) {
            velocity.y *= -0.5; // Stop vertical movement
            nextY = 10 + radius; // Adjust position
        }

        // Calculate the translation needed
        double translateX = nextX - position.x;
        double translateY = nextY - position.y;

        // Apply the translation to the ellipse for visual update
        ellipse.translate(translateX, translateY);

        // Update the position to the new corrected position
        position.x = nextX;
        position.y = nextY;

    }

    public boolean intersects(Player2 player) {
        double ellipseCenterX = position.x;
        double ellipseCenterY = position.y;
        double ellipseRadius = diameter / 2;
        double playerX = player.position.x - player.width / 2;
        double playerY = player.position.y - player.height / 2;

        // Find the closest point on the Rectangle to the center of the Ellipse
        double closestX = clamp(ellipseCenterX, playerX, playerX + player.width);
        double closestY = clamp(ellipseCenterY, playerY, playerY + player.height);

        // Calculate the distance between the Ellipse's center and the closest point
        double distanceX = ellipseCenterX - closestX;
        double distanceY = ellipseCenterY - closestY;

        // If the distance is less than the radius, they intersect
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY) <= ellipseRadius;
    }

    // Helper method to clamp values
    public double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public boolean isOnGround(Field field) {
        return field.getObjectPosition(this.position).y + diameter >= field.field.getHeight();
    }

    public boolean isMoving() {
        return this.velocity.x != 0;
    }
}


