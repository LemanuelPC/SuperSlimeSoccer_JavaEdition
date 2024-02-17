package io.codeforall.javatars_filhosdamain;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Field {
    Rectangle field;
    Vector position;
    double width;
    double height;

    public Field(int padding, int width, int height){
        this.field = new Rectangle(padding, padding, width, height);
        this.position = new Vector((double) width / 2, (double) height / 2);
        this.width = width;
        this.height = height;
    }

    public Vector getObjectPosition(Position absolutePosition) {
        // Subtract the padding to translate the absolute position into field-relative position
        double relativeX = absolutePosition.x - field.getX();
        double relativeY = absolutePosition.y - field.getY();
        return new Vector(relativeX, relativeY);
    }

    @Override
    public String toString() {
        return "Field{" +
                "field=" + field +
                ", position=" + position +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
