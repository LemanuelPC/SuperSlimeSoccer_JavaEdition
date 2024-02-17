package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Ellipse;

public class Ball2 {
    Ellipse ellipse;
    Position logicalPosition;
    Position graphicalPosition;
    Movement movement;
    double diameter;
    double radius;

    public Ball2(double xTopLeftCorner, double yTopLeftCorner, double diameter) {
        this.diameter = diameter;
        this.radius = diameter/2;
        this.ellipse = new Ellipse(xTopLeftCorner, yTopLeftCorner, diameter, diameter);
        this.logicalPosition = new Position(xTopLeftCorner + radius, yTopLeftCorner + radius);
        this.graphicalPosition = new Position(xTopLeftCorner + radius, yTopLeftCorner + radius);
        this.movement = new Movement();
    }

    // Method to update the ball's position based on its velocity
    public void updateLogicalPosition() {
        //System.out.println("**** Ball before logic update *****");
        //System.out.println("Logical Position: " + logicalPosition);
        //System.out.println("Movement: " + movement);
        //System.out.println("\n");
        this.logicalPosition.x = this.logicalPosition.x + Math.cos(this.movement.direction) * this.movement.velocity.magnitude;
        this.logicalPosition.y = this.logicalPosition.y + Math.sin(this.movement.direction) * this.movement.velocity.magnitude;
        //System.out.println("**** Ball after logic update *****");
        //System.out.println("Logical Position: " + logicalPosition);
        //System.out.println("Movement: " + movement);
        //System.out.println("\n");
    }

    public boolean intersectsPlayer(Player2 player) {
        // If the distance is less or equal to the sum of their radius, they intersect
        return distanceToPlayer(player) <= this.radius + player.height;
    }

    public boolean isCollidingWithFloor(Field field) {
        return logicalPosition.y + radius >= field.field.getHeight()+10;
    }

    public boolean isCollidingWithCeiling(Field field) {
        return logicalPosition.y - radius <= field.field.getY();
    }

    public boolean isCollidingWithLeftWall(Field field) {
        return logicalPosition.x - radius <= field.field.getX();
    }

    public boolean isCollidingWithRightWall(Field field) {
        return logicalPosition.x + radius >= field.field.getWidth()+10;
    }

    public double distanceToPlayer(Player2 player){
        //Distance between player and ball
        return Math.sqrt(Math.pow(this.logicalPosition.x - player.logicalPosition.x, 2) + Math.pow(this.logicalPosition.y - player.logicalPosition.y, 2));
    }

    public boolean isMoving() {
        return this.movement.velocity.magnitude != 0;
    }

    void checkCollisions(Player2[] players, Field field) {
        for (Player2 player : players) {
            if (intersectsPlayer(player)) {
                movement.direction = Math.atan2(logicalPosition.y - player.logicalPosition.y, logicalPosition.x - player.logicalPosition.x) ;
                movement.velocity.magnitude += player.movement.velocity.magnitude * 0.8;
                movement.velocity.x = Math.cos(movement.direction) * movement.velocity.magnitude;
                movement.velocity.y = Math.sin(movement.direction) * movement.velocity.magnitude;

                if (distanceToPlayer(player) < this.radius + player.height) {
                    double correctX = this.logicalPosition.x + Math.cos(this.movement.direction) * ((this.radius + player.height) - distanceToPlayer(player));
                    double correctY = this.logicalPosition.y + Math.sin(this.movement.direction) * ((this.radius + player.height) - distanceToPlayer(player));

                    this.logicalPosition.x = correctX;
                    this.logicalPosition.y = correctY;
                }
            }
        }

        if(isCollidingWithFloor(field)){
            if (logicalPosition.y + radius > field.field.getHeight()+10){
                double deltaY = Math.abs(logicalPosition.y - (field.field.getHeight()+10 - radius));
                double deltaX = deltaY / Math.tan(movement.direction);
                double correctX;

                if (movement.velocity.x > 0) {
                    // Moving right
                    correctX = logicalPosition.x + deltaX;
                } else {
                    // Moving left
                    correctX = logicalPosition.x - deltaX;
                }

                double correctY = field.field.getHeight()+10 - radius; // Position ball just above the bottom

                this.logicalPosition.x = correctX;
                this.logicalPosition.y = correctY;
            }

            movement.velocity.y = -movement.velocity.y * 0.8;
            movement.velocity.updateMagnitude();
            movement.direction = Math.atan2(movement.velocity.y, movement.velocity.x);

            if (movement.velocity.magnitude < 0.35){
                movement.velocity.x = 0.0;
                movement.velocity.y = 0.0;
                movement.velocity.magnitude = 0.0;
            }
        }

        if(isCollidingWithCeiling(field)){
            if (logicalPosition.y < radius) {
                double deltaY = Math.abs(logicalPosition.y - radius);
                double deltaX = deltaY / Math.tan(movement.direction);
                double correctX;

                if (movement.velocity.x > 0) {
                    // Moving right
                    correctX = logicalPosition.x + deltaX;
                } else {
                    // Moving left
                    correctX = logicalPosition.x - deltaX;
                }

                double correctY = radius; // Position ball just below the ceiling

                this.logicalPosition.x = correctX;
                this.logicalPosition.y = correctY;
            }

            movement.velocity.y = -movement.velocity.y * 0.8;
            movement.velocity.updateMagnitude();
            movement.direction = Math.atan2(movement.velocity.y, movement.velocity.x);
        }

        if(isCollidingWithLeftWall(field)){
            if (logicalPosition.x - radius < field.field.getX()){
                double correctX = radius;
                double deltaX = Math.abs(logicalPosition.x - correctX);
                double deltaY = deltaX * Math.tan(movement.direction);
                double correctY;

                if (movement.velocity.y > 0) {
                    // If moving downwards, add deltaY to correctY
                    correctY = logicalPosition.y + deltaY;
                } else {
                    // If moving upwards, subtract deltaY from correctY
                    correctY = logicalPosition.y - deltaY;
                }

                this.logicalPosition.x = correctX;
                this.logicalPosition.y = correctY;
            }

            movement.velocity.x = -movement.velocity.x * 0.8;
            movement.velocity.updateMagnitude();
            movement.direction = Math.atan2(movement.velocity.y, movement.velocity.x);
        }

        if(isCollidingWithRightWall(field)){
            if (logicalPosition.x > field.field.getWidth()+10 - radius) {
                double deltaX = Math.abs(logicalPosition.x - (field.field.getWidth()+10 - radius));
                double deltaY = deltaX * Math.tan(movement.direction);
                double correctY;

                if (movement.velocity.y > 0) {
                    // Moving down
                    correctY = logicalPosition.y + deltaY;
                } else {
                    // Moving up
                    correctY = logicalPosition.y - deltaY;
                }

                this.logicalPosition.x = field.field.getHeight() - radius;
                this.logicalPosition.y = correctY;
            }

            movement.velocity.x = -movement.velocity.x * 0.8;
            movement.velocity.updateMagnitude();
            movement.direction = Math.atan2(movement.velocity.y, movement.velocity.x);
        }
    }

    public void updateGraphicalPosition(){
        //System.out.println("Logical Position: " + logicalPosition);
        //System.out.println("Graphical Position: " + graphicalPosition);
        //System.out.println("\n");
        this.ellipse.translate(this.logicalPosition.x - this.graphicalPosition.x, this.logicalPosition.y - this.graphicalPosition.y);
        this.graphicalPosition.x = this.logicalPosition.x;
        this.graphicalPosition.y = this.logicalPosition.y;
    }
}


