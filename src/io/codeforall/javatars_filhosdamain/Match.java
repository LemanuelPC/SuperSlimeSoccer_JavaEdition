package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;


public class Match implements Interactable {

    public static final int FIELD_HEIGHT = 400;
    public static final int FIELD_WIDTH = 560;
    public static final int PADDING = 10;
    private int frameRate = 60;
    private int frameTime = 1000 / frameRate;
    public static final double GRAVITY = 9.81; // Gravity constant
    public static final double ATTRITION = 2.0; // Attrition constant
    public static final double BALL_ATTRITION = 0.2; // Ball Attrition constant
    private static final double MAX_BOUNCE_ANGLE = Math.toRadians(75);

    private Game game;
    private Player2 player1, player2;
    private Rectangle goal1, goal2, back;
    private Field field;
    private Ball2 ball;
    private boolean upPressed = false;
    private boolean aPressed = false;
    private boolean wPressed = false;
    private boolean leftPressed = false;
    private boolean dPressed = false;
    private boolean rightPressed = false;
    private boolean pPressed = false;

    public Match(Game game){
        this.game = game;
    }

    public void init() {
        System.out.println("Initializing game");

        back = new Rectangle(-10, -10, 800, 800);

        field = new Field(PADDING, FIELD_WIDTH, FIELD_HEIGHT);

        player1 = new Player2(PADDING + 20, FIELD_HEIGHT + PADDING - 50, 100, 50);
        player2 = new Player2(FIELD_WIDTH - 115, FIELD_HEIGHT + PADDING - 50, 100, 50);

        ball = new Ball2((double) FIELD_WIDTH /2, (double) FIELD_HEIGHT /2, 20);

        goal1 = new Rectangle(10, FIELD_HEIGHT + PADDING - 115, 70, 115);
        goal2 = new Rectangle(FIELD_WIDTH -60, FIELD_HEIGHT + PADDING - 115, 70, 115);


        back.setColor(Color.YELLOW);
        player1.rectangle.setColor(Color.RED);
        player2.rectangle.setColor(Color.BLUE);
        ball.ellipse.setColor(Color.BLACK);
        goal1.setColor(Color.BLACK);
        goal2.setColor(Color.BLACK);
        field.field.setColor(Color.BLACK);

        showGame();

        play();

    }

    public void showGame(){
        pPressed = false;
        game.setKeyboardListenerEntity(this);

        back.fill();
        player1.rectangle.fill();
        player2.rectangle.fill();
        ball.ellipse.fill();
        goal1.draw();
        goal2.draw();
        field.field.draw();

    }

    private void hideGame(){
        player1.rectangle.delete();
        player2.rectangle.delete();
        ball.ellipse.delete();
        goal1.delete();
        goal2.delete();
        field.field.delete();
    }

    void applyGravity() {
        if (!ball.isCollidingWithFloor(field)) {
            ball.movement.velocity.updateVector(0, GRAVITY/60);// gravity is a constant, e.g., 9.81
            ball.movement.direction = Math.atan2(ball.movement.velocity.y, ball.movement.velocity.x);
        }
        for (Player2 player : new Player2[]{player1, player2}) {
            if (!player.isCollidingWithFloor(field)) {
                player.movement.velocity.updateVector(0, GRAVITY/60);
                player.movement.direction = Math.atan2(player.movement.velocity.y, player.movement.velocity.x);
            }
        }
    }

    void applyAttrition() {
        if (ball.isMoving()) {
            double frictionForceX = (ball.movement.velocity.x / ball.movement.velocity.magnitude) * (BALL_ATTRITION/60);
            double frictionForceY = (ball.movement.velocity.y / ball.movement.velocity.magnitude) * (BALL_ATTRITION/60);
            ball.movement.velocity.x -= frictionForceX;
            ball.movement.velocity.y -= frictionForceY;
            ball.movement.velocity.updateMagnitude();

            if (ball.movement.velocity.magnitude < 0.01){
                ball.movement.velocity.x = 0.0;
                ball.movement.velocity.y = 0.0;
                ball.movement.velocity.magnitude = 0.0;
            }
        }
        for (Player2 player : new Player2[]{player1, player2}) {
            if (player.isMoving()) {
                double frictionForceX = (player.movement.velocity.x / player.movement.velocity.magnitude) * (ATTRITION/60);
                double frictionForceY = (player.movement.velocity.y / player.movement.velocity.magnitude) * (ATTRITION/60);
                player.movement.velocity.x -= frictionForceX;
                player.movement.velocity.y -= frictionForceY;
                player.movement.velocity.updateMagnitude();

                if (player.movement.velocity.magnitude < 0.35){
                    player.movement.velocity.x = 0.0;
                    player.movement.velocity.y = 0.0;
                    player.movement.velocity.magnitude = 0.0;
                }
            }
        }
    }


    public void play() {

        while (true) {
            long startTime = System.currentTimeMillis();

            if (!game.isPauseGame()) {
                // Game Loop Logic Start
                /*System.out.println("Posição logica do player1: " + player1.logicalPosition);
                System.out.println("Posição grafica do player1: " + player1.graphicalPosition);
                System.out.println("Movimento do player1: " + player1.movement);
                System.out.println("******");
                System.out.println("******");*/
                // Apply gravity if not grounded
                applyGravity();

                // Apply attrition if players moving
                applyAttrition();

                // Update Movement
                ball.updateLogicalPosition();
                player1.updateLogicalPosition(field);
                player2.updateLogicalPosition(field);

                // Collision Detection and Response
                ball.checkCollisions(new Player2[]{player1, player2}, field);
                player1.checkCollisions(field);
                player2.checkCollisions(field);

                ball.updateGraphicalPosition();
                player1.updateGraphicalPosition();
                player2.updateGraphicalPosition();

                // Game Loop Logic End
            }

            if(game.isPauseGame() && !game.isMenuOpened()){
                hideGame();
                game.openMenu();
            }

            // Delay for next frame
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            long sleepTime = frameTime - elapsedTime;

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @Override
    public void setKey(int key, boolean state) {
        if (key == KeyboardEvent.KEY_UP){
            upPressed = true;
            player2.jump(field);
            upPressed = false;
        }
        if (key == KeyboardEvent.KEY_LEFT){
            leftPressed = true;
            player2.move(-1);
            leftPressed = false;
        }
        if (key == KeyboardEvent.KEY_RIGHT){
            rightPressed = true;
            player2.move(1);
            rightPressed = false;
        }
        if (key == KeyboardEvent.KEY_W) {
            wPressed = true;
            player1.jump(field);
            wPressed = false;
        }
        if (key == KeyboardEvent.KEY_A){
            aPressed = true;
            player1.move(-1);
            aPressed = false;
        }
        if (key == KeyboardEvent.KEY_D){
            dPressed = true;
            player1.move(1);
            dPressed = false;
        }
        if (key == KeyboardEvent.KEY_P) {
            game.setPauseGame(true);
        }
        if (key == KeyboardEvent.KEY_ESC) {
            System.exit(0);
        }
        if (key == KeyboardEvent.KEY_T) {
            System.out.println(field);
            System.out.println("Posição X do field na Canvas: " + field.field.getX());
            System.out.println("Posição Y do field na Canvas: " + field.field.getY());
            System.out.println("Posição Ymax do field na Canvas: " + field.field.getHeight());
            System.out.println("Posição Xmax do field na Canvas: " + field.field.getWidth());
            System.out.println("******");
            System.out.println("Posição logica da bola: " + ball.logicalPosition);
            System.out.println("Posição grafica da bola: " + ball.graphicalPosition);
            System.out.println("Movimento da bola: " + ball.movement);
            System.out.println("******");
            System.out.println("Posição logica do player1: " + player1.logicalPosition);
            System.out.println("Posição grafica do player1: " + player1.graphicalPosition);
            System.out.println("Movimento do player1: " + player1.movement);
            System.out.println(ball.distanceToPlayer(player1));
        }
    }

}

