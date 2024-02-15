package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Canvas;

public class Engine {

/*    public Engine() {
        Canvas canvas = Canvas.getInstance();
        Canvas.limitCanvasWidth(800);
        Canvas.limitCanvasHeight(600);
        Menu menu = new Menu(canvas);
        Game game = new Game(canvas);
        //registerKeyboardEvents();
    }*/

    /*private void registerKeyboardEvents() {
        Keyboard keyboard = new Keyboard(this);
        int[] keys = {KeyboardEvent.KEY_UP, KeyboardEvent.KEY_DOWN, KeyboardEvent.KEY_ENTER, KeyboardEvent.KEY_ESC, KeyboardEvent.KEY_P};
        for (int key : keys) {
            keyboard.addEventListener(createKeyboardEvent(key, KeyboardEventType.KEY_PRESSED));
            keyboard.addEventListener(createKeyboardEvent(key, KeyboardEventType.KEY_RELEASED));
        }
    }*/

    /*private KeyboardEvent createKeyboardEvent(int key, KeyboardEventType type) {
        KeyboardEvent event = new KeyboardEvent();
        event.setKey(key);
        event.setKeyboardEventType(type);
        return event;
    }

    @Override
    public void keyPressed(KeyboardEvent e) {
        switch (e.getKey()) {
            case KeyboardEvent.KEY_UP:
                menu.currentOption = (currentOption - 1 + menuOptions.length) % menuOptions.length;
                updateMenuDisplay();
                break;
            case KeyboardEvent.KEY_DOWN:
                currentOption = (currentOption + 1) % menuOptions.length;
                updateMenuDisplay();
                break;
            case KeyboardEvent.KEY_ENTER:
                executeSelectedOption();
                break;
            case KeyboardEvent.KEY_ESC:
                escPressed = true;
                System.exit(0);
                break;
            case KeyboardEvent.KEY_P:
                pPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent e) {
        // Optional: Implement if needed for finer control over key events
    }*/


 /*   private void openConfig() {
        // Show configuration options for key bindings and volume settings
    }*/

    public static void main(String[] args) {
        //new Engine();
        Canvas canvas = Canvas.getInstance();
        Canvas.limitCanvasWidth(800);
        Canvas.limitCanvasHeight(600);
        Game game = new Game(canvas);
        //Menu menu = new Menu(canvas);
        //Match match = new Match(canvas);
        //menu.initMenu();
        game.init();

    }
}
