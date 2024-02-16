package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Ball{
    Ellipse ellipse;
    Vector position;
    Vector velocity;

    public Ball(double xTopLeftCorner, double yTopLeftCorner, double diameter) {
        this.ellipse = new Ellipse(xTopLeftCorner, yTopLeftCorner, diameter, diameter);
        this.position = new Vector(xTopLeftCorner + diameter/2, yTopLeftCorner);
        this.velocity = new Vector(0, 0); // Start with no initial velocity
    }

    // Method to update the ball's position based on its velocity
    public void update(int CANVAS_WIDTH, int CANVAS_HEIGHT) {
        // Apply velocity to position
        double nextX = position.x + velocity.x;
        double nextY = position.y + velocity.y;

        // Adjust for the radius of the ball for boundary checks
        double radius = (double) ellipse.getWidth() / 2;

        // Left boundary
        if (nextX < 10 + radius) {
            velocity.x = 0; // Stop horizontal movement
            nextX = 10 + radius; // Adjust position to boundary
        }
        // Right boundary
        if (nextX > CANVAS_WIDTH  - radius) {
            velocity.x = 0; // Stop horizontal movement
            nextX = CANVAS_WIDTH - radius; // Adjust position
        }
        // Bottom boundary
        if (nextY > CANVAS_HEIGHT - 10 - radius) {
            velocity.y = 0; // Stop vertical movement
            nextY = CANVAS_HEIGHT - 10 - radius; // Adjust position
        }
        // Top boundary
        if (nextY < 10 + radius) {
            velocity.y = 0; // Stop vertical movement
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

    public boolean intersects(Rectangle rect) {
        double ellipseCenterX = position.x;
        double ellipseCenterY = position.y;
        double ellipseRadius = (double) ellipse.getWidth() / 2;

        // Find the closest point on the Rectangle to the center of the Ellipse
        double closestX = clamp(ellipseCenterX, rect.getX(), rect.getX() + rect.getWidth());
        double closestY = clamp(ellipseCenterY, rect.getY(), rect.getY() + rect.getHeight());

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

    public boolean isOnGround(int CANVAS_HEIGHT) {
        return this.position.y + (double) this.ellipse.getHeight() / 2 >= CANVAS_HEIGHT - 10;
    }

    public boolean isMoving() {
        return this.velocity.x != 0;
    }
}
