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
 * @authors 18lambas and 18zhaoy ALSO NOT USED
 */
public class Projectile implements GameObject {

    private int x, y;
    private int targetX, targetY;
    private int attack;
    private Color side;
    private int xVel, yVel;
    private int speed = 5;
    private int radius = 5; //size of the projectile

    public Projectile(Color mySide, int initX, int initY, int findX, int findY) {
        x = initX;
        y = initY;
        side = mySide;
        targetX = findX;
        targetY = findY;
        int dx = targetX - x;
        int dy = targetY - y;
        double r = speed / Math.sqrt(dy * dy + dx * dx);
        xVel = (int) (dx * r);
        yVel = (int) (dy * r);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, radius, radius);
    }

    public void draw(Graphics g) {
        g.setColor(side);
        g.fillOval(x, y, radius, radius);
    }

    public void animate() {
        int dx = targetX - x;
        int dy = targetY - y;
        double r = speed / Math.sqrt(dy * dy + dx * dx);
        xVel = (int) (dx * r);
        yVel = (int) (dy * r);
        x += xVel;
        y += yVel;
    }
}
