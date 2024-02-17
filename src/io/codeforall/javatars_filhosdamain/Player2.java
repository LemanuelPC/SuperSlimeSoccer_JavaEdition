package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Player2 {
    Rectangle rectangle;
    Position logicalPosition;
    Position graphicalPosition;
    Movement movement;
    double width;
    double height;

    public Player2(double x, double y, double width, double height) {
        this.rectangle = new Rectangle(x, y, width, height);
        this.logicalPosition = new Position(x + width / 2, height); // Assuming center bottom position
        this.graphicalPosition = new Position(x + width / 2, height);
        this.movement = new Movement(); // Start with no initial movement
        this.width = width;
        this.height = height;
    }

    // Method to update the player's position based on its velocity
    public void updateLogicalPosition(int CANVAS_WIDTH, int CANVAS_HEIGHT) {
        // Apply velocity to position
        double nextX = logicalPosition.x + movement.velocity.x;
        double nextY = logicalPosition.y + movement.velocity.y;

        // Check for left boundary
        if (nextX < 10 + width / 2) {
            movement.velocity.x = 0; // Stop horizontal movement
            nextX = 10 + width / 2; // Adjust position to boundary
        }
        // Check for right boundary
        if (nextX > CANVAS_WIDTH - width / 2) {
            movement.velocity.x = 0; // Stop horizontal movement
            nextX = CANVAS_WIDTH - width / 2; // Adjust position
        }
        // Check for bottom boundary
        if (nextY > CANVAS_HEIGHT - logicalPosition.y) { // Consider padding
            movement.velocity.y = 0; // Stop vertical movement
            nextY = CANVAS_HEIGHT - logicalPosition.y; // Adjust position
        }

        // Update the position
        logicalPosition.x = nextX;
        logicalPosition.y = nextY;
    }

    public void updateGraphicalPosition() {
        //System.out.println("Player Logic Position: " + this.logicalPosition);
        //System.out.println("Player Graphical Position before update: " + this.graphicalPosition);
        rectangle.translate(this.logicalPosition.x - this.graphicalPosition.x, this.logicalPosition.y - this.graphicalPosition.y);
        this.graphicalPosition.x = this.logicalPosition.x;
        this.graphicalPosition.y = this.logicalPosition.y;
        //System.out.println("Player Graphical Position after update: " + this.graphicalPosition);
        //System.out.println("******");
    }

    public boolean isOnGround(int CANVAS_HEIGHT) {
        return this.logicalPosition.y >= CANVAS_HEIGHT;
    }

    public boolean isMoving() {
        return this.movement.velocity.x != 0;
    }

    public void jump(int CANVAS_HEIGHT) {
        // Check if the player is on the ground before allowing a jump
        if (isOnGround(CANVAS_HEIGHT)) {
            movement.velocity.y = -10; // Adjust this value to control the jump strength
        }
    }

    public void move(int direction) {
        movement.velocity.x = 5 * direction; // Multiply by the direction (-1 for left, 1 for right)
    }

    public Position getCenter() {
        return logicalPosition;
    }

}
