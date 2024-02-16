package io.codeforall.javatars_filhosdamain;

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

        player1 = new Player2(PADDING + 20, FIELD_HEIGHT + PADDING - 50, 105, 50);
        player2 = new Player2(FIELD_WIDTH - 115, FIELD_HEIGHT + PADDING - 50, 105, 50);

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
        if (!ball.isOnGround(field) && !ball.intersects(player1) && !ball.intersects(player2)) {
            ball.velocity.y += GRAVITY; // gravity is a constant, e.g., 0.98
        }
        if (ball.isOnGround(field)) {
            System.out.println("******* isOnGround == True *******");
            System.out.println("Ball middle logic Y position: " + ball.position.y);
            //System.out.println("Ball middle graphic position: " + (double) (ball.ellipse.getY() + ball.ellipse.getHeight()));
            System.out.println(ball.velocity);
            System.out.println("\n");
        }
        for (Player2 player : new Player2[]{player1, player2}) {
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
        for (Player2 player : new Player2[]{player1, player2}) {
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
        for (Player2 player : new Player2[]{player1, player2}) {
            if (ball.intersects(player)) {
                System.out.println("Ball hit " + player);
                handleBallPlayerCollision(player);
            }
        }
    }

    void handleBallPlayerCollision(Player2 player) {
        // Add the player's velocity to the ball's velocity
        ball.velocity.x = Math.abs(ball.velocity.x) + player.velocity.x;
        ball.velocity.y = Math.abs(ball.velocity.y) + player.velocity.y;

        // Correct the ball's position to prevent it from passing through the player
        if (ball.velocity.y < 0) { // If the ball is moving upwards after the collision
            //ball.position.y = player.position.y + player.rectangle.getHeight() + 1; // Adjust ball above the player
        } else { // If the ball is moving downwards
            //ball.position.y = player.position.y - ball.ellipse.getHeight() - 1; // Place the ball below the player
        }

        // Further refine position adjustment based on the side of collision if necessary
        // This is a simplified example; you may need to adjust logic based on your game's specifics
    }

    public void play() {

        while (true) {
            long startTime = System.currentTimeMillis();

            if (!game.isPauseGame()) {
                // Game Loop Logic Start
                //ball.ellipse.translate(0,0.98);
                System.out.println("******* Loop start *******");
                //System.out.println("Ball middle logic position: " + ball.position);
                System.out.println("Ball relative graphic position: " + field.getObjectPosition(ball.position));
                System.out.println("Player2 relative graphic position: " + field.getObjectPosition(player2.position));
                System.out.println("Ball velocity vector: " + ball.velocity);
                System.out.println("\n");

                // Physics Update
                ball.update(field, new Player2[]{player1, player2});
                player1.update(FIELD_WIDTH+PADDING, FIELD_HEIGHT+PADDING);
                player2.update(FIELD_WIDTH+PADDING, FIELD_HEIGHT+PADDING);

                // Apply gravity if not grounded
                applyGravity();

                // Apply attrition if players moving
                applyAttrition();

                // Collision Detection and Response
                checkCollisions();
                System.out.println("******* Loop end *******");
                //System.out.println("Ball middle logic position: " + ball.position);
                System.out.println("Ball relative graphic position: " + field.getObjectPosition(ball.position));
                System.out.println("Player2 relative graphic position: " + field.getObjectPosition(player2.position));
                System.out.println("Ball velocity vector: " + ball.velocity);
                System.out.println("\n");


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

