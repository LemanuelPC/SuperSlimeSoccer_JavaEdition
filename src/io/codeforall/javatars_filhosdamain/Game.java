package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Canvas;

public class Game {
    Match match;
    Menu menu;
    Canvas canvas;

    public Game(Canvas canvas){
        this.canvas = canvas;
        this.menu = new Menu(canvas, this);
    }

    public void init(){
        //menu.initMenu();
        match = new Match(canvas, menu);
    }

    public void startGame(){
        //match = new Match(canvas, menu);
        match.init();
    }


}
