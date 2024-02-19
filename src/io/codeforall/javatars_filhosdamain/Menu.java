package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Menu implements Interactable {

    private Picture background;
    boolean isVisible;
    private Text pressSpaceText;
    private int currentOption = 0;
    private final String[] menuOptions = {"Start Game", "Config", "Exit Game"};
    private final String[] menuOptions2 = {"Resume Game", "Config", "Exit Game"};
    private final Text[] menuTexts = new Text[menuOptions.length];
    Game game;

    public Menu(Game game){
        this.game = game;
    }

    public void display() {
        System.out.println("Menu opened");
        game.setKeyboardListenerEntity(this);
        isVisible = true;
        background = new Picture(10, 10, "data/mainscreen.jpeg");
        background.draw();

        if(game.isStartGame()) {
            for (int i = 0; i < menuOptions.length; i++) {
                menuTexts[i] = new Text(290, 120 + (i * 80), menuOptions2[i]);
                menuTexts[i].grow(50,25);
                menuTexts[i].draw();
            }
        }
        else{
            for (int i = 0; i < menuOptions.length; i++) {
                menuTexts[i] = new Text(290, 120 + (i * 80), menuOptions[i]);
                menuTexts[i].grow(50,25);
                menuTexts[i].draw();
            }
        }
        updateMenuDisplay();
        System.out.println("menu visible: " + isVisible);

    }

    public void clearDisplay(){
        for (int i = 0; i < menuOptions.length; i++) {
            menuTexts[i].delete();
        }
        background.delete();
        isVisible = false;
        System.out.println("menu visible: " + isVisible);
        game.setMenuOpen(false);
    }

    public void updateMenuDisplay() {
        for (int i = 0; i < menuTexts.length; i++) {
            menuTexts[i].setColor(i == currentOption ? Color.RED : Color.BLUE);
        }
    }

    @Override
    public void setKey(int key, boolean state) {
        if (key == KeyboardEvent.KEY_UP){
            currentOption = (currentOption - 1 + menuOptions.length) % menuOptions.length;
            updateMenuDisplay();
        }
        if (key == KeyboardEvent.KEY_DOWN){
            currentOption = (currentOption + 1) % menuOptions.length;
            updateMenuDisplay();
        }
        if (key == KeyboardEvent.KEY_SPACE) {
            executeSelectedOption();
        }
    }

    private void executeSelectedOption() {
        switch (currentOption) {
            case 0:
                // Start the game
                if(game.isPauseGame()) {
                    clearDisplay();
                    game.setPauseGame(false);
                    game.resumeGame();
                    break;
                }
                clearDisplay();
                game.setStartGame();
                break;
            case 1:
                // Open configuration settings
                clearDisplay();
                game.setConfigOpen(true);
                break;
            case 2:
                // Exit the game
                System.exit(0);
                break;
        }
    }

}
