package io.codeforall.javatars;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Ellipse;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class PongGame implements KeyboardHandler {

    private Rectangle paddle1, paddle2;

    private Rectangle goal1, goal2;

    private Ellipse ball;
    private int paddleSpeed = 10;
    private double ballSpeedX = 1, ballSpeedY = 1;
    private Keyboard keyboard;

    private boolean upPressed = false;
    private boolean leftPressed = false;

    private boolean rightPressed = false;
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean pPressed = false;
    private boolean escPressed = false;
    private int canvasWidth = 800;
    private int canvasHeight = 600;

    private int counter = 0;
    private int maxCounter = 10;

    public void init() {

        Canvas canvas = Canvas.getInstance();
        Canvas.limitCanvasWidth(canvasWidth);
        Canvas.limitCanvasHeight(canvasHeight);

        paddle1 = new Rectangle(10, canvasHeight - 50, 150, 50);
        paddle2 = new Rectangle(650, 550, 150, 50);
        goal1 = new Rectangle(0, canvasHeight - 100, 50, 100);
        goal2 = new Rectangle(750, canvasHeight -100, 50, 100);
        ball = new Ellipse(390, 290, 20, 20);

        paddle1.setColor(Color.RED);
        paddle2.setColor(Color.BLUE);
        ball.setColor(Color.BLACK);
        goal1.setColor(Color.BLACK);
        goal2.setColor(Color.BLACK);

        paddle1.fill();
        paddle2.fill();
        ball.fill();
        goal1.draw();
        goal2.draw();
        setupKeyboard();

        gameStart();
    }

    private boolean isColliding(Ellipse ball, Rectangle paddle) {

        return ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() < paddle.getY() + paddle.getHeight() &&
                ball.getY() + ball.getHeight() > paddle.getY();
    }

    private void setupKeyboard() {
        keyboard = new Keyboard(this);

        registerKey(KeyboardEvent.KEY_W, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_A, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_D, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_W, KeyboardEventType.KEY_RELEASED);
        registerKey(KeyboardEvent.KEY_A, KeyboardEventType.KEY_RELEASED);
        registerKey(KeyboardEvent.KEY_D, KeyboardEventType.KEY_RELEASED);

        registerKey(KeyboardEvent.KEY_UP, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_LEFT, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_RIGHT, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_UP, KeyboardEventType.KEY_RELEASED);
        registerKey(KeyboardEvent.KEY_LEFT, KeyboardEventType.KEY_RELEASED);
        registerKey(KeyboardEvent.KEY_RIGHT , KeyboardEventType.KEY_RELEASED);

        registerKey(KeyboardEvent.KEY_P, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_ESC, KeyboardEventType.KEY_PRESSED);
    }

    private void registerKey(int key, KeyboardEventType type) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(type);
        keyboard.addEventListener(event);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_W:
                wPressed = true;
                break;
            case KeyboardEvent.KEY_A:
                aPressed = true;
                break;
            case KeyboardEvent.KEY_D:
                dPressed = true;
                break;
            case KeyboardEvent.KEY_UP:
                upPressed = true;
                break;
            case KeyboardEvent.KEY_LEFT:
                leftPressed = true;
                break;
            case KeyboardEvent.KEY_RIGHT:
                rightPressed = true;
                break;
            case KeyboardEvent.KEY_P:
                pPressed = !pPressed;
                break;
            case KeyboardEvent.KEY_ESC:
                escPressed = true;
                System.exit(0);
                break;
        }
        updatePaddles();
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_W:
                wPressed = false;
                break;
            case KeyboardEvent.KEY_A:
                aPressed = false;
                break;
            case KeyboardEvent.KEY_D:
                dPressed = false;
                break;
            case KeyboardEvent.KEY_UP:
                upPressed = false;
                break;
            case KeyboardEvent.KEY_LEFT:
                leftPressed = false;
                break;
            case KeyboardEvent.KEY_RIGHT:
                rightPressed = false;
        }
        updatePaddles();
    }

    private void updatePaddles() {
        if (wPressed && paddle1.getX() > 0) {
            paddle1.translate(0, -paddleSpeed);
        }
        if (aPressed && paddle1.getX() > 0) {
            paddle1.translate(-paddleSpeed, 0);
        }
        if (dPressed && paddle1.getX() + paddle1.getWidth() < canvasWidth) {
            paddle1.translate( paddleSpeed, 0);
        }
        if (upPressed && paddle2.getX() > 0) {
            paddle2.translate(0, -paddleSpeed);
        }
        if (leftPressed && paddle2.getX()> 0) {
            paddle2.translate(-paddleSpeed, 0);
        }
        if (rightPressed && paddle2.getX() + paddle2.getWidth()< canvasWidth) {
            paddle2.translate(paddleSpeed, 0);
        }
    }

    private void gameStart() {
        while (!escPressed) {
            if (!pPressed) {
                if (counter < maxCounter){
                    counter++;
                    increaseBallSpeed();
                }
                if (ballSpeedX > 0 || ballSpeedY > 0){
                    decreaseBallSpeed();
                }

                //ball.translate(ballSpeedX, ballSpeedY);

                if (ball.getY() + ball.getHeight() < canvasHeight ){
                    ball.translate(0,ballSpeedY);
                }

                if (ball.getX() < -ball.getWidth() || ball.getX() > canvasWidth + ball.getWidth()) {
                    ballSpeedX = -ballSpeedX;
                   // resetBall();
                }

                if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= canvasHeight) {
                    ballSpeedY = -ballSpeedY;
                   // increaseBallSpeed();
                }

                if (isColliding(ball, paddle1) || isColliding(ball, paddle2)) {
                    ballSpeedX = -ballSpeedX;
                    counter = 0;
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException exception) {
                System.out.println("Game loop interrupted");
            }
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

        ballSpeedX += ballSpeedX > 0 ? 0.1 : 0;
        ballSpeedY += ballSpeedY > 0 ? 0.1 : 0;
        //System.out.println(ballSpeedX);
        //System.out.println(ballSpeedY);
    }
    private void decreaseBallSpeed() {

        ballSpeedX -= ballSpeedX > 0 ? -0.1 : 0;
        ballSpeedY -= ballSpeedY > 0 ? -0.1 : 0;
        //System.out.println(ballSpeedX);
        //System.out.println(ballSpeedY);
    }

}
