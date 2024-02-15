package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;


public class Match implements Interactable {

    private Rectangle rectangle;
    public static final int canvasHeight = 600;
    public static final int canvasWidth = 800;
    //private Slime player = new Slime();
    private static final int DELAY = 10;
    //private CollisionDetector collisionDetector;

    //public Slime getPlayer() {
        //return player;
    //}
    private Game game;
    private Rectangle paddle1, paddle2, ball;
    private int paddleSpeed = 10;
    private double ballSpeedX = 1.0, ballSpeedY = 1.0;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private boolean pPressed = false;
    private boolean escPressed = false;

    public Match(Game game){
        this.game = game;
    }


    public void init() {
        System.out.println("Initializing game");

        paddle1 = new Rectangle(10, 250, 20, 100);
        paddle2 = new Rectangle(770, 250, 20, 100);
        ball = new Rectangle(390, 290, 20, 20);

        paddle1.setColor(Color.RED);
        paddle2.setColor(Color.BLUE);
        ball.setColor(Color.BLACK);

        showGame();

        play();

    }

    public void showGame(){
        pPressed = false;
        game.setKeyboardListenerEntity(this);

        paddle1.fill();
        paddle2.fill();
        ball.fill();

    }

    private void hideGame(){
        paddle1.delete();
        paddle2.delete();
        ball.delete();
    }

    private boolean isColliding(Rectangle ball, Rectangle paddle) {
        return ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() < paddle.getY() + paddle.getHeight() &&
                ball.getY() + ball.getHeight() > paddle.getY();
    }

    private void updatePaddles() {
        if (wPressed && paddle1.getY() > 0) {
            paddle1.translate(0, -paddleSpeed);
        }
        if (sPressed && paddle1.getY() + paddle1.getHeight() < canvasHeight) {
            paddle1.translate(0, paddleSpeed);
        }
        if (upPressed && paddle2.getY() > 0) {
            paddle2.translate(0, -paddleSpeed);
        }
        if (downPressed && paddle2.getY() + paddle2.getHeight() < canvasHeight) {
            paddle2.translate(0, paddleSpeed);
        }
    }

    private void resetBall() {

        if (ball != null) {
            ball.delete();
        }

        int centerX = canvasWidth / 2 - ball.getWidth() / 2;
        int centerY = canvasHeight / 2 - ball.getHeight() / 2;

        ball = new Rectangle(centerX, centerY, ball.getWidth(), ball.getHeight());
        ball.setColor(Color.BLACK);
        ball.fill();

        ballSpeedX = 1.0;
        ballSpeedY = 1.0;
    }

    private void increaseBallSpeed() {

        ballSpeedX += ballSpeedX > 0 ? 0.1 : -0.1;
        ballSpeedY += ballSpeedY > 0 ? 0.1 : -0.1;
        //System.out.println(ballSpeedX);
        //System.out.println(ballSpeedY);
    }



    public void play() {

        while (!escPressed) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!game.isPauseGame()) {
                ball.translate(ballSpeedX, ballSpeedY);

                if (ball.getX() < -ball.getWidth() || ball.getX() > canvasWidth + ball.getWidth()) {
                    resetBall();
                }

                if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= canvasHeight) {
                    ballSpeedY = -ballSpeedY;
                    increaseBallSpeed();
                }

                if (isColliding(ball, paddle1) || isColliding(ball, paddle2)) {
                    ballSpeedX = -ballSpeedX;
                    increaseBallSpeed();
                }
            }
        }
    }

    @Override
    public void setKey(int key, boolean state) {
        if (key == KeyboardEvent.KEY_UP){
            upPressed = state;
            updatePaddles();
        }
        if (key == KeyboardEvent.KEY_DOWN){
            downPressed = state;
            updatePaddles();
        }
        if (key == KeyboardEvent.KEY_W) {
            wPressed = state;
            updatePaddles();
        }
        if (key == KeyboardEvent.KEY_S) {
            sPressed = state;
            updatePaddles();
        }
        if (key == KeyboardEvent.KEY_P) {
            game.setPauseGame(true);
            hideGame();
            game.openMenu();
            if(!game.isPauseGame()) {
                showGame();
            }
        }
        if (key == KeyboardEvent.KEY_ESC) {
            escPressed = state;
            System.exit(0);
        }
    }

}

