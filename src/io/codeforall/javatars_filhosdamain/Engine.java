package io.codeforall.javatars_filhosdamain;

import io.codeforall.javatars_filhosdamain.players.Slime;
import org.academiadecodigo.simplegraphics.graphics.Canvas;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Engine {

    Canvas canvas;

    public Engine() {
        canvas = Canvas.getInstance();
        Canvas.limitCanvasWidth(800);
        Canvas.limitCanvasHeight(600);
        Menu menu = new Menu(canvas);
        menu.initMenu();
        //registerKeyboardEvents();
    }

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
        new Engine();
    }
}
