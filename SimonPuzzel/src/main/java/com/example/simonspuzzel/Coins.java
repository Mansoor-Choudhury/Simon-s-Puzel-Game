package com.example.simonspuzzel;


import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Coins class to store coins details
 *
 * CreatedBy: Mansoor
 */
public class Coins implements Serializable {

    private double x;
    private double y;
    private double radius;
    private Circle circle;
    private Text text;
    private boolean isPressed;

    /**
     * Coins constructor
     *
     * @param x - x coordinate
     * @param y - y coordinate
     * @param radius - radius of coins
     * @param circle - circle object
     * @param text - text in the circles or coins
     */
    public Coins(double x, double y, double radius, Circle circle, Text text) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.circle = circle;
        this.text = text;
    }

    /**
     * Setter for y coordinate
     *
     * @param y - y coordinate
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Setter for x coordinate
     *
     * @param x - x coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * getter for y coordinate
     *
     * @return - y coordinate value
     */
    public double getY() {
        return y;
    }

    /**
     * getter for x coordinate
     *
     * @return - x coordinate value
     */
    public double getX() {
        return x;
    }

    /**
     * Get circle getter
     *
     * @return - circle object
     */
    public Circle getCircle(){
        return circle;
    }

    /**
     * setter for color
     *
     * @param color  - color
     */
    public void setColor(Color color) {
        circle.setFill(color);
    }

    /**
     * changes the coordinates of the circle and text as it gets dragged
     *
     * @param event - mouse event
     */
    public void draw(MouseEvent event) {
        circle.setRadius(radius);
        circle.setTranslateX(x);
        circle.setTranslateY(y);
        text.setTranslateX(x-10);
        text.setTranslateY(y+10);
    }

    /**
     * Is pressed to check if coin is already placed and not movable
     *
     * @return - true or false
     */
    public boolean isPressed() {
        return isPressed;
    }

    /**
     * Setter for is pressed
     *
     * @param pressed - true or false
     */
    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    /**
     * Setter method to send text value
     *
     * @param text - text object
     */
    public void setText(Text text) {
        this.text = text;
    }

    /**
     * Getter for text object
     *
     * @return - text object
     */
    public Text getText() {
        return text;
    }
}
