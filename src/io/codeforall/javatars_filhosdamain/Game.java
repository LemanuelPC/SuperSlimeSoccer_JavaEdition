package io.codeforall.javatars_filhosdamain;

public class Game {
    private Match match;
    private static final int DELAY = 10;
    private Menu menu;
    private KeyboardListener keyboardListener;
    private boolean startGame;
    private boolean pauseGame;
    private boolean menuOpened;

    public void init() {
        keyboardListener = new KeyboardListener();
        menu = new Menu(this);

        //keyboardListener.setEntity(menu);
        menu.display();


        while (true) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (startGame) {
                menu.clearDisplay();
                match = new Match(this);
                //setKeyboardListenerEntity(match);
                start();
            }
        }
    }

    public void start() {

        match.init();

    }

    public void setKeyboardListenerEntity(Interactable event){
        keyboardListener.setEntity(event);
    }

    public void openMenu(){
        menu.display();
    }

    public void resumeGame(){
        menu.clearDisplay();
        match.showGame();
    }

    public boolean isStartGame() {
        return startGame;
    }

    public void setStartGame() {
        this.startGame = true;
    }

    public boolean isPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(boolean state) {
        this.pauseGame = state;
    }

    public boolean isMenuOpened() {
        return menuOpened;
    }

    public void setMenuOpened(boolean menuOpened) {
        this.menuOpened = menuOpened;
    }
}
