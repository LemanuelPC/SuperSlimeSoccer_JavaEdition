package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Game implements KeyboardHandler {

    private int currentOption = 0;
    private final String[] menuOptions = {"Start Game", "Config", "Exit Game"};
    private final Text[] menuTexts = new Text[menuOptions.length];
    Canvas canvas;

    public Game() {
        canvas = Canvas.getInstance();
        Canvas.limitCanvasWidth(800);
        Canvas.limitCanvasHeight(600);
        initMenu();
        registerKeyboardEvents();
    }

    private void initMenu() {
        for (int i = 0; i < menuOptions.length; i++) {
            menuTexts[i] = new Text(350, 250 + (i * 50), menuOptions[i]);
            canvas.show(menuTexts[i]);
        }
        updateMenuDisplay();
    }

    private void updateMenuDisplay() {
        for (int i = 0; i < menuTexts.length; i++) {
            menuTexts[i].setColor(i == currentOption ? Color.RED : Color.BLACK);
        }
    }

    private void registerKeyboardEvents() {
        Keyboard keyboard = new Keyboard(this);
        int[] keys = {KeyboardEvent.KEY_UP, KeyboardEvent.KEY_DOWN, KeyboardEvent.KEY_ENTER};
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
                currentOption = (currentOption - 1 + menuOptions.length) % menuOptions.length;
                updateMenuDisplay();
                break;
            case KeyboardEvent.KEY_DOWN:
                currentOption = (currentOption + 1) % menuOptions.length;
                updateMenuDisplay();
                break;
            case KeyboardEvent.KEY_ENTER:
                executeSelectedOption();
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent e) {
        // Optional: Implement if needed for finer control over key events
    }

    private void executeSelectedOption() {
        switch (currentOption) {
            case 0:
                // Start the game
                startGame();
                break;
            case 1:
                // Open configuration settings
                openConfig();
                break;
            case 2:
                // Exit the game
                System.exit(0);
                break;
        }
    }

    private void startGame() {
        // Initialize game entities and start the game loop
    }

    private void openConfig() {
        // Show configuration options for key bindings and volume settings
    }

    public static void main(String[] args) {
        new Game();
    }
}
