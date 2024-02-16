package io.codeforall.javatars_filhosdamain;

public class Vector {
    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Vector addition
    public Vector add(Vector v) {
        return new Vector(this.x + v.x, this.y + v.y);
    }

    // Vector subtraction
    public Vector subtract(Vector v) {
        return new Vector(this.x - v.x, this.y - v.y);
    }

    // Scalar multiplication
    public Vector multiply(double value) {
        return new Vector(this.x * value, this.y * value);
    }

    // Normalize vector
    public Vector normalize() {
        double length = Math.sqrt(x * x + y * y);
        return length > 0 ? new Vector(x / length, y / length) : new Vector(0, 0);
    }

    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }
}
