package io.codeforall.javatars_filhosdamain;

public class Game {
    private Match match;
    private static final int DELAY = 10;
    private Menu menu;
    private Config config;
    private MatchOptions matchOptions;
    private KeyboardListener keyboardListener;
    private boolean startGame;
    private boolean pauseGame;
    private boolean menuOpen;
    private boolean configOpen;
    private boolean matchOptionsOpen;

    public void init() {
        keyboardListener = new KeyboardListener();
        menu = new Menu(this);
        this.config = new Config(this);
        this.matchOptions = new MatchOptions(this);

        //keyboardListener.setEntity(menu);
        setMenuOpen(true);


        while (true) {
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (startGame) {
                match = new Match(this);
                //setKeyboardListenerEntity(match);
                start();
            }

            if (configOpen){
                if(!config.isVisible) {
                    config.display();
                }
            }

            if (menuOpen){
                if(!menu.isVisible) {
                    menu.display();
                }
            }
            if (matchOptionsOpen){
                if(!matchOptions.isVisible) {
                    matchOptions.display();
                }
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

    public boolean isMenuOpen() {
        return menuOpen;
    }

    public void setMenuOpen(boolean menuOpen) {
        this.menuOpen = menuOpen;
    }

    public boolean isConfigOpen() {
        return configOpen;
    }

    public void setConfigOpen(boolean configOpen) {
        this.configOpen = configOpen;
    }

    public void setMatchOptionsOpen(boolean matchOptionsOpen) {
        this.matchOptionsOpen = matchOptionsOpen;
    }
}
