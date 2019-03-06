/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @authors 18lambas and 18zhaoy THIS IS NOT USED
 */
public class Turret implements GameObject {

    private double statMultiplier;
    private int attack;
    private int x, y;
    private int width, height;
    private Color side;

    /**
     *
     * @param num
     * @param multiplier
     * @param mySide
     * @param myX
     * @param myY
     * @param myWidth
     * @param myHeight
     */
    public Turret(int num, int multiplier, Color mySide, int myX, int myY, int myWidth, int myHeight) {
        statMultiplier = multiplier;
        if (num == 1) {
            attack = (int) statMultiplier * 15;
        } else if (num == 2) {
            attack = (int) statMultiplier * 25;
        } else {
            attack = (int) statMultiplier * 40;
        }
        side = mySide;
        x = myX;
        y = myY;
        width = myWidth;
        height = myHeight;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(side);
        g.fillRect(x, y, width, height);
    }

    public void setMultiplier(int multiplier) {
        statMultiplier = multiplier;
    }
}
