package io.codeforall.javatars_filhosdamain.players;

import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Slime {
    Picture slime = new Picture(56,28, "data/sprites/spr_slime_classic.png");

    public void draw(){
        slime.draw();
    }

    public Picture getSlime() {
        return slime;
    }
}
