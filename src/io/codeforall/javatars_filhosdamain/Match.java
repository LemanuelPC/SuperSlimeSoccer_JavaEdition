package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;


public class Match implements Interactable {

    private Rectangle rectangle;
    public static final int canvasHeight = 600;
    public static final int canvasWidth = 800;
    //private Slime player = new Slime();
    private static final int DELAY = 10;
    Vector gravity = new Vector(0, 9.8); // Gravity vector
    double timeStep = 0.016; // Simulation time step, assuming 60 FPS

    // Ball attributes
    Vector ballPosition = new Vector(100, 100); // Initial position
    Vector ballVelocity = new Vector(0, 0); // Initial velocity
    Vector ballAcceleration = gravity; // Initial acceleration is just gravity

    Vector slimeVelocity = new Vector(0, 0); // Slime velocity
    boolean isJumping = false;

    //private CollisionDetector collisionDetector;

    //public Slime getPlayer() {
        //return player;
    //}
    private Game game;
    private Rectangle player1, player2, goal1, goal2;
    private Ellipse ball;
    private int paddleSpeed = 10, jumpPower = 20;
    private double ballSpeedX = 0.0, ballSpeedY = 0.0, jumpSpeed = 0.0;
    private boolean upPressed = false;
    private boolean aPressed = false;
    private boolean wPressed = false;
    private boolean leftPressed = false;
    private boolean dPressed = false;
    private boolean rightPressed = false;
    private boolean pPressed = false;
    private boolean escPressed = false;

    private int counter = 0;
    private int maxCounter = 10;

    public Match(Game game){
        this.game = game;
    }


    public void init() {
        System.out.println("Initializing game");

        player1 = new Rectangle(10, canvasHeight - 50, 150, 50);
        player2 = new Rectangle(650, 550, 150, 50);
        ball = new Ellipse(390, 290, 20, 20);
        goal1 = new Rectangle(0, canvasHeight - 100, 50, 100);
        goal2 = new Rectangle(750, canvasHeight -100, 50, 100);

        player1.setColor(Color.RED);
        player2.setColor(Color.BLUE);
        ball.setColor(Color.BLACK);
        goal1.setColor(Color.BLACK);
        goal2.setColor(Color.BLACK);

        showGame();

        play();

    }

    public void showGame(){
        pPressed = false;
        game.setKeyboardListenerEntity(this);

        player1.fill();
        player2.fill();
        ball.fill();
        goal1.draw();
        goal2.draw();

    }

    private void hideGame(){
        player1.delete();
        player2.delete();
        ball.delete();
    }

/*    private boolean isColliding(Ellipse ball, Rectangle paddle) {

        return ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() < paddle.getY() + paddle.getHeight() &&
                ball.getY() + ball.getHeight() > paddle.getY();
    }

    private boolean isCollidingLeft(Ellipse ball, Rectangle paddle){
        System.out.println("Colliding Left " + (ball.getX() + ball.getWidth() >= paddle.getX() && ball.getY() + ball.getHeight() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getHeight()));
        return ball.getX() + ball.getWidth() >= paddle.getX() && ball.getY() + ball.getHeight() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getHeight();
    }

    private boolean isCollidingTop(Ellipse ball, Rectangle paddle){
        System.out.println("Colliding Top " + (ball.getY() + ball.getHeight() >= paddle.getY() && ball.getX() + ball.getWidth() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth()));
        return ball.getY() + ball.getHeight() >= paddle.getY() && ball.getX() + ball.getWidth() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth();
    }

    private boolean isCollidingRight(Ellipse ball, Rectangle paddle){
        System.out.println("Colliding Right " + (ball.getX() <= paddle.getX() + paddle.getWidth() && ball.getY() + ball.getHeight() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getHeight()));
        return ball.getX() <= paddle.getX() + paddle.getWidth() && ball.getY() + ball.getHeight() >= paddle.getY() && ball.getY() <= paddle.getY() + paddle.getHeight();
    }

    private boolean isCollidingBottom(Ellipse ball, Rectangle paddle){
        System.out.println("Colliding Bottom " + (ball.getY() <= paddle.getY() + paddle.getHeight() && ball.getX() + ball.getWidth() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth()));
        return ball.getY() <= paddle.getY() + paddle.getHeight() && ball.getX() + ball.getWidth() >= paddle.getX() && ball.getX() <= paddle.getX() + paddle.getWidth();
    }*/

    public void updateBall() {
        // Apply gravity to velocity
        ballVelocity = ballVelocity.add(ballAcceleration.multiply(timeStep));

        // Update position based on velocity
        ballPosition = ballPosition.add(ballVelocity.multiply(timeStep));

        if (ballPosition.y >= groundY) {
            ballPosition.y = groundY; // Reset position to ground level
            ballVelocity.y *= -1; // Reflect velocity in the Y direction
            ballVelocity = ballVelocity.multiply(0.9); // Simulate energy loss
        }
    }

    public void jump() {
        if (!isJumping) {
            slimeVelocity.y = -10; // Initial jump speed
            isJumping = true;
        }
    }

    public void updateSlime() {
        if (isJumping) {
            // Apply gravity
            slimeVelocity = slimeVelocity.add(gravity.multiply(timeStep));
            // Update position
            slimePosition = slimePosition.add(slimeVelocity.multiply(timeStep));
            // Check for landing
            if (slimePosition.y >= groundY) {
                slimePosition.y = groundY;
                isJumping = false;
            }
        }
    }

    private void updatePaddles() {
        if (wPressed && player1.getX() > 0) {
            player1.translate(0, -jumpPower);
        }
        if (aPressed && player1.getX() > 0) {
            player1.translate(-paddleSpeed, 0);
        }
        if (dPressed && player1.getX() + player1.getWidth() < canvasWidth) {
            player1.translate( paddleSpeed, 0);
        }
        if (upPressed && player2.getX() > 0) {
            player2.translate(0, -jumpPower);
        }
        if (leftPressed && player2.getX()> 0) {
            player2.translate(-paddleSpeed, 0);
        }
        if (rightPressed && player2.getX() + player2.getWidth()< canvasWidth) {
            player2.translate(paddleSpeed, 0);
        }
    }

    private void resetBall() {

        if (ball != null) {
            ball.delete();
        }

        int centerX = canvasWidth / 2 - ball.getWidth() / 2;
        int centerY = canvasHeight / 2 - ball.getHeight() / 2;

        ball = new Ellipse(centerX, centerY, ball.getWidth(), ball.getHeight());
        ball.setColor(Color.BLACK);
        ball.fill();

        ballSpeedX = 1.0;
        ballSpeedY = 1.0;
    }

    private void increaseBallSpeed() {

        ballSpeedX += ballSpeedX >= 0 ? 0.1 : 0;
        ballSpeedY += ballSpeedY >= 0 ? 0.1 : 0;
        //System.out.println(ballSpeedX);
        //System.out.println(ballSpeedY);
    }

    private void decreaseBallSpeed() {

        ballSpeedX -= ballSpeedX > 0 ? -0.1 : 0;
        ballSpeedY -= ballSpeedY > 0 ? -0.1 : 0;
        //System.out.println(ballSpeedX);
        //System.out.println(ballSpeedY);
    }

    private void decreaseJumpSpeed() {

        jumpSpeed -= jumpSpeed > 0 ? -0.1 : 0;

    }



    public void play() {

        while (!escPressed) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!game.isPauseGame()) {
                if (counter < maxCounter){
                    counter++;
                    increaseBallSpeed();
                }
                if (ballSpeedX > 0 || ballSpeedY > 0){
                    decreaseBallSpeed();
                }
                if (jumpSpeed > 0){
                    decreaseJumpSpeed();
                }

                ball.translate(ballSpeedX, 0);

                if (ball.getY() + ball.getHeight() < canvasHeight){
                    ball.translate(0,Math.abs(ballSpeedY-gravity));
                }

                if(player1.getY()+player1.getHeight() < canvasHeight){
                    player1.translate(0,Math.abs(jumpSpeed-gravity));
                }

                if(player2.getY()+player2.getHeight() < canvasHeight){
                    player2.translate(0,Math.abs(jumpSpeed-gravity));
                }

                if (ball.getX() < -ball.getWidth() || ball.getX() > canvasWidth + ball.getWidth()) {
                    ballSpeedX = -ballSpeedX;
                    // resetBall();
                }

                if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= canvasHeight) {
                    ballSpeedY = -ballSpeedY;
                    // increaseBallSpeed();
                }

                /*if (isColliding(ball, player1) || isColliding(ball, player2)) {
                    ballSpeedX = -ballSpeedX;
                    counter = 0;
                }*/

                if (isCollidingLeft(ball, player2)) {
                    increaseBallSpeed();
                    ballSpeedX = -ballSpeedX;
                }

                if (isCollidingLeft(ball, player1)) {
                    increaseBallSpeed();
                    ballSpeedX = -ballSpeedX;
                }

                if (isCollidingRight(ball, player1)) {
                    increaseBallSpeed();
                    ballSpeedX = -ballSpeedX;
                }

                if (isCollidingRight(ball, player2)) {
                    increaseBallSpeed();
                    ballSpeedX = -ballSpeedX;
                }

                if (isCollidingTop(ball, player1)) {
                    increaseBallSpeed();
                    ballSpeedY = -ballSpeedY;
                }

                if (isCollidingTop(ball, player2)) {
                    increaseBallSpeed();
                    ballSpeedY = -ballSpeedY;
                }

                if (isCollidingBottom(ball, player1)) {
                    increaseBallSpeed();
                    ballSpeedY = -ballSpeedY;
                }

                if (isCollidingBottom(ball, player2)) {
                    increaseBallSpeed();
                    ballSpeedY = -ballSpeedY;
                }

            }
            if(game.isPauseGame() && !game.isMenuOpened()){
                hideGame();
                game.openMenu();
            }
        }
    }

    @Override
    public void setKey(int key, boolean state) {
        if (key == KeyboardEvent.KEY_UP){
            upPressed = true;
            updatePaddles();
            upPressed = false;
        }
        if (key == KeyboardEvent.KEY_LEFT){
            leftPressed = true;
            updatePaddles();
            leftPressed = false;
        }
        if (key == KeyboardEvent.KEY_RIGHT){
            rightPressed = true;
            updatePaddles();
            rightPressed = false;
        }
        if (key == KeyboardEvent.KEY_W) {
            wPressed = true;
            updatePaddles();
            wPressed = false;
        }
        if (key == KeyboardEvent.KEY_A){
            aPressed = true;
            updatePaddles();
            aPressed = false;
        }
        if (key == KeyboardEvent.KEY_D){
            dPressed = true;
            updatePaddles();
            dPressed = false;
        }
        if (key == KeyboardEvent.KEY_P) {
            game.setPauseGame(true);
        }
        if (key == KeyboardEvent.KEY_ESC) {
            escPressed = state;
            System.exit(0);
        }
    }

}

