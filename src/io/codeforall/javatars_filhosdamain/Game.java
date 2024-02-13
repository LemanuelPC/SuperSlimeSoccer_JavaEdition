package io.codeforall.javatars_filhosdamain;

import io.codeforall.javatars_filhosdamain.players.Slime;
import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Game implements GameKeyboardHandler {

//    Slime player1 = new Slime();
    private Canvas canvas;

    public Game(Canvas canvas){
        this.canvas = canvas;
        //registerKeyboardEvents();
        init();
    }
/*
    private void registerKeyboardEvents() {
        Keyboard keyboard = new Keyboard(this);
        int[] keys = {KeyboardEvent.KEY_UP, KeyboardEvent.KEY_ENTER, KeyboardEvent.KEY_ESC, KeyboardEvent.KEY_P};
        for (int key : keys) {
            keyboard.addEventListener(createKeyboardEvent(key, KeyboardEventType.KEY_PRESSED));
            keyboard.addEventListener(createKeyboardEvent(key, KeyboardEventType.KEY_RELEASED));
        }
    }

    private KeyboardEvent createKeyboardEvent(int key, KeyboardEventType type) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(type);
        return event;
    }

    @Override
    public void keyPressed(KeyboardEvent e) {
        switch (e.getKey()) {
            case KeyboardEvent.KEY_UP:
                // saltar
                break;
            case KeyboardEvent.KEY_ENTER:
                // ignorar
                break;
            case KeyboardEvent.KEY_ESC:
                //escPressed = true;
                System.exit(0);
                break;
            case KeyboardEvent.KEY_P:
                //pPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent e) {
        // Optional: Implement if needed for finer control over key events
    }

    private void startGame() {
        System.out.println("Initial game");
        while (true) {
            canvas.show(player1.getSlime());
            try {
                Thread.sleep(10);
            } catch (InterruptedException exception) {
                System.out.println("Game loop interrupted");
            }
        }
    }*/

    private Rectangle paddle1, paddle2, ball;
    private int paddleSpeed = 10;
    private double ballSpeedX = 1.0, ballSpeedY = 1.0;
    private Keyboard keyboard;

    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean wPressed = false;
    private boolean sPressed = false;

    private boolean pPressed = false;
    private boolean escPressed = false;

    private int canvasWidth = 800;
    private int canvasHeight = 600;

    public void init() {
        System.out.println("Initializing game");
        paddle1 = new Rectangle(10, 250, 20, 100);
        paddle2 = new Rectangle(770, 250, 20, 100);
        ball = new Rectangle(390, 290, 20, 20);

        paddle1.setColor(Color.RED);
        paddle2.setColor(Color.BLUE);
        ball.setColor(Color.BLACK);

        paddle1.fill();
        paddle2.fill();
        ball.fill();

        setupKeyboard();

        gameStart();
    }

    private boolean isColliding(Rectangle ball, Rectangle paddle) {
        return ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() < paddle.getY() + paddle.getHeight() &&
                ball.getY() + ball.getHeight() > paddle.getY();
    }

    private void setupKeyboard() {
        keyboard = new Keyboard(this);

        registerKey(KeyboardEvent.KEY_W, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_S, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_W, KeyboardEventType.KEY_RELEASED);
        registerKey(KeyboardEvent.KEY_S, KeyboardEventType.KEY_RELEASED);

        registerKey(KeyboardEvent.KEY_UP, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_DOWN, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_UP, KeyboardEventType.KEY_RELEASED);
        registerKey(KeyboardEvent.KEY_DOWN, KeyboardEventType.KEY_RELEASED);

        registerKey(KeyboardEvent.KEY_P, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_ESC, KeyboardEventType.KEY_PRESSED);
        System.out.println(keyboard);
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
            case KeyboardEvent.KEY_S:
                sPressed = true;
                break;
            case KeyboardEvent.KEY_UP:
                upPressed = true;
                break;
            case KeyboardEvent.KEY_DOWN:
                downPressed = true;
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
            case KeyboardEvent.KEY_S:
                sPressed = false;
                break;
            case KeyboardEvent.KEY_UP:
                upPressed = false;
                break;
            case KeyboardEvent.KEY_DOWN:
                downPressed = false;
                break;
        }
        updatePaddles();
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

    private void gameStart() {
        System.out.println(ball);
        while (!escPressed) {
            if (!pPressed) {
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
}

