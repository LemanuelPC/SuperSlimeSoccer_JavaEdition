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
    public static final double GRAVITY = 0.98; // Gravity constant
    public static final double ATTRITION = 0.20; // Attrition constant
    public static final double BALL_ATTRITION = 0.02; // Attrition constant
    private static final double MAX_BOUNCE_ANGLE = Math.toRadians(75);

    private Game game;
    private Player player1, player2;
    private Rectangle goal1, goal2, field, back;
    private Ball ball;
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
        player1 = new Player(PADDING + 20, FIELD_HEIGHT + PADDING - 50, 105, 50);
        player2 = new Player(FIELD_WIDTH - 115, FIELD_HEIGHT + PADDING - 50, 105, 50);
        ball = new Ball((double) FIELD_WIDTH /2, (double) FIELD_HEIGHT /2, 20);
        goal1 = new Rectangle(10, FIELD_HEIGHT + PADDING - 115, 70, 115);
        goal2 = new Rectangle(FIELD_WIDTH -60, FIELD_HEIGHT + PADDING - 115, 70, 115);
        field = new Rectangle(10, 10, FIELD_WIDTH, FIELD_HEIGHT);

        back.setColor(Color.YELLOW);
        player1.rectangle.setColor(Color.RED);
        player2.rectangle.setColor(Color.BLUE);
        ball.ellipse.setColor(Color.BLACK);
        goal1.setColor(Color.BLACK);
        goal2.setColor(Color.BLACK);
        field.setColor(Color.BLACK);

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
        field.draw();

    }

    private void hideGame(){
        player1.rectangle.delete();
        player2.rectangle.delete();
        ball.ellipse.delete();
        goal1.delete();
        goal2.delete();
        field.delete();
    }

    void applyGravity() {
        if (!ball.isOnGround(FIELD_HEIGHT+PADDING)) {
            ball.velocity.y += GRAVITY; // gravity is a constant, e.g., 0.98
        }
        for (Player player : new Player[]{player1, player2}) {
            if (!player.isOnGround(FIELD_HEIGHT+PADDING)) {
                player.velocity.y += GRAVITY;
            }
        }
    }

    void applyAttrition() {
        if (ball.isMoving()) {
            if (ball.velocity.x > 0) {
                ball.velocity.x = Math.max(0, ball.velocity.x - BALL_ATTRITION);
            } else if (ball.velocity.x < 0) {
                ball.velocity.x = Math.min(0, ball.velocity.x + BALL_ATTRITION);
            }
        }
        for (Player player : new Player[]{player1, player2}) {
            if (player.isMoving()) {
                if (player.velocity.x > 0) {
                    player.velocity.x = Math.max(0, player.velocity.x - ATTRITION);
                } else if (player.velocity.x < 0) {
                    player.velocity.x = Math.min(0, player.velocity.x + ATTRITION);
                }
            }
        }
    }

    void checkCollisions() {
        // Ball and Slime Collision
        for (Player player : new Player[]{player1, player2}) {
            if (ball.intersects(player.rectangle)) {
                handleBallPlayerCollision(player);
            }
        }
        // Ball and Boundaries Collision
        // Top boundary
        if (ball.position.y <= 10) {
            ball.position.y = 10; // Correct position
            ball.velocity.y *= -0.9; // Reverse and reduce speed
        }
        // Bottom boundary
        if (ball.position.y + ball.ellipse.getHeight() >= FIELD_HEIGHT - 10) {
            ball.position.y = FIELD_HEIGHT - ball.ellipse.getHeight() - 10;
            ball.velocity.y *= -0.9; // Reverse and reduce speed
        }
        // Left boundary
        if (ball.position.x <= 10) {
            ball.position.x = 10; // Correct position
            ball.velocity.x *= -0.9; // Reverse and reduce speed
        }
        // Right boundary
        if (ball.position.x + ball.ellipse.getWidth() >= FIELD_WIDTH - 10) {
            ball.position.x = FIELD_WIDTH - ball.ellipse.getWidth() - 10;
            ball.velocity.x *= -0.9; // Reverse and reduce speed
        }
    }

    void handleBallPlayerCollision(Player player) {
        // Find the center point of the player
        Vector playerCenter = player.getCenter();

        // Calculate the collision point's relative position to the player's center
        double relativeIntersectY = (playerCenter.y - ball.position.y) / ((double) player.rectangle.getHeight() / 2);

        // Calculate bounce angle
        double bounceAngle = relativeIntersectY * MAX_BOUNCE_ANGLE;

        // Adjust ball's velocity based on bounce angle
        ball.velocity.x = (ball.velocity.x > 0 ? -1 : 1) * Math.cos(bounceAngle) * ball.velocity.getMagnitude();
        ball.velocity.y = -Math.sin(bounceAngle) * ball.velocity.getMagnitude();

        // Ensure the ball's velocity is not zero to avoid stuck situations
        //if (Math.abs(ball.velocity.x) < 1) {
        //}
    }

    public void play() {

        while (true) {
            long startTime = System.currentTimeMillis();

            if (!game.isPauseGame()) {
                // Game Loop Logic Start

                // Physics Update
                ball.update(FIELD_WIDTH, FIELD_HEIGHT);
                player1.update(FIELD_WIDTH+PADDING, FIELD_HEIGHT+PADDING);
                player2.update(FIELD_WIDTH+PADDING, FIELD_HEIGHT+PADDING);

                // Apply gravity if not grounded
                applyGravity();

                // Apply attrition if players moving
                applyAttrition();

                // Collision Detection and Response
                checkCollisions();

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
            player2.jump(FIELD_HEIGHT+PADDING);
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
            player1.jump(FIELD_HEIGHT+PADDING);
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
    }

}

