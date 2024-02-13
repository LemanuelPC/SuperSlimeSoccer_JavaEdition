package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import java.security.UnresolvedPermission;
import java.util.concurrent.Callable;

public class SlimeS_field implements KeyboardHandler{

    private Rectangle slime1, slime2, ball;
    private int slimespeed = 10;
    private double ballSpeedX = 1.0, ballSpeedY = 1.0;
    private Keyboard keyboard;

    private boolean upPressed = false;
    private boolean wPressed = false;
    private boolean pPressed = false;
    private boolean escPressed = false;

    private int canvasWidth = 800;
    private int canvasHeight = 600;
    public void init() {

        Canvas canvas = Canvas.getInstance();
      //  Canvas.limitCanvasWidth(canvasHeight);
        // Canvas.limitCanvasHeight(canvasWidth);



        slime1 = new Rectangle(30, 650, 40, 40);
        slime2 = new Rectangle(770, 650, 40, 40);
        ball = new Rectangle(390, 290, 20, 20);

        slime1.setColor(Color.GREEN);
        slime2.setColor(Color.BLUE);
        ball.setColor(Color.ORANGE);

        slime1.fill();
        slime2.fill();
        ball.fill();

        setupKeyboard();
        gameStart();

    }
    private void setupKeyboard() {
        keyboard = new Keyboard(this);

        registerKey(KeyboardEvent.KEY_W, KeyboardEventType.KEY_PRESSED);
        //registerKey(KeyboardEvent.KEY_S, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_W, KeyboardEventType.KEY_RELEASED);
        //registerKey(KeyboardEvent.KEY_S, KeyboardEventType.KEY_RELEASED);

        registerKey(KeyboardEvent.KEY_UP, KeyboardEventType.KEY_PRESSED);
        //registerKey(KeyboardEvent.KEY_DOWN, KeyboardEventType.KEY_PRESSED);
        registerKey(KeyboardEvent.KEY_UP, KeyboardEventType.KEY_RELEASED);
        //registerKey(KeyboardEvent.KEY_DOWN, KeyboardEventType.KEY_RELEASED);

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
            /*case KeyboardEvent.KEY_S:
                sPressed = true;
                break;*/
            case KeyboardEvent.KEY_UP:
                upPressed = true;
                break;
            /*case KeyboardEvent.KEY_DOWN:
                downPressed = true;
                break;*/
            case KeyboardEvent.KEY_P:
                pPressed = !pPressed;
                break;
            case KeyboardEvent.KEY_ESC:
                escPressed = true;
                System.exit(0);
                break;
        }
        updateSlimes();
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_W:
                wPressed = false;
                break;
            /*case KeyboardEvent.KEY_S:
                sPressed = false;
                break;*/
            case KeyboardEvent.KEY_UP:
                upPressed = false;
                break;
            /*case KeyboardEvent.KEY_DOWN:
                downPressed = false;
                break;*/
        }
        updateSlimes();
    }
    private void updateSlimes() {
        if (wPressed && slime1.getY() > 0) {
            slime1.translate(0, -slimespeed);
        }
        /*NOT NEEDED YET if (sPressed && slime1.getY() + slime1.getHeight() < canvasHeight) {
            slime1.translate(0, slimespeed);
        }*/
        if (upPressed && slime2.getY() > 0) {
            slime2.translate(0, -slimespeed);
        }
        /*NOT NEEDED YET if (downPressed && slime2.getY() + slime2.getHeight() < canvasHeight) {
            slime2.translate(0, slimespeed);
        }*/
    }
    private void resetBall() {

        if (ball != null) {
            ball.delete();
        }

        int centerX = canvasWidth / 2 - ball.getWidth() / 2;
        int centerY = canvasHeight / 2 - ball.getHeight() / 2;

        ball = new Rectangle(centerX, centerY, ball.getWidth(), ball.getHeight());
        ball.setColor(Color.ORANGE);
        ball.fill();

        ballSpeedX = 1.0;
        ballSpeedY = 1.0;
    }
    private boolean isColliding(Rectangle ball, Rectangle paddle) {
        return ball.getX() < paddle.getX() + paddle.getWidth() &&
                ball.getX() + ball.getWidth() > paddle.getX() &&
                ball.getY() < paddle.getY() + paddle.getHeight() &&
                ball.getY() + ball.getHeight() > paddle.getY();
    }
    public void gameStart() {
        System.out.println(ball);
        while (!escPressed) {
            if (!pPressed) {
                System.out.println(ball);
                ball.translate(ballSpeedX, ballSpeedY);

                if (ball.getX() < -ball.getWidth() || ball.getX() > canvasWidth + ball.getWidth()) {
                    resetBall();
                }

                if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= canvasHeight) {
                    ballSpeedY = -ballSpeedY;
                   //Not needed as we don't need to increase the speed increaseBallSpeed();
                }

                if (isColliding(ball, slime1) || isColliding(ball, slime2)) {
                    ballSpeedX = -ballSpeedX;
                    //Not needed as we don't need to increase the speed increaseBallSpeed();
                }
            }

            try {
                Thread.sleep(10);
            } catch (InterruptedException exception) {
                System.out.println("Game loop interrupted");
            }
        }
    }
}
