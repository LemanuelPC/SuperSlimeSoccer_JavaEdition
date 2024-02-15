package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Canvas;

public class Engine {

    public static void main(String[] args) {

        Canvas.limitCanvasHeight(600);
        Canvas.limitCanvasWidth(800);

        Game game = new Game();

        game.init();

    }
}
