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
 * @authors 18lambas and 18zhaoy
 */
public class Troop implements GameObject {

    private int currHP;
    private int maxHP;
    private int attack;
    private double statMultiplier;
    private boolean ranged;
    private int x, y;
    private int originX;
    private int width, height;
    private Color side;
    private int speed;
    private Projectile shooter;
    private int gap;
    private int numT;
    private long start;
    private int count;
    private Color myColor;
    private Color originColor;

    public Troop(int num, Color mySide, double multiplier, int velocity, int myX, int myY, long startTime) {
        start = startTime;
        statMultiplier = multiplier; //determines the multiplier for the stats. It is from the Castle's multiplier
        //System.out.println(statMultiplier + "from Troop");
        gap = 15;
        numT = num;
        width = 5;
        height = 50;
        if (num == 1 || num == 2) {
            maxHP = (int) (statMultiplier * 50);
            //System.out.println(maxHP + "maxHP");
            currHP = maxHP;
            attack = (int) (statMultiplier * 20);
        } else //troop 3 stats
        {
            maxHP = (int) (statMultiplier * 100);
            currHP = maxHP;
            attack = (int) (statMultiplier * 40);
        }
        if (num == 2) //ranged stats
        {
            ranged = true;
            gap = gap * 2 + width + 1;
            attack = (int) (statMultiplier * 15);
            maxHP = (int) (statMultiplier * 35);
            currHP = maxHP;
        } else {
            ranged = false;
        }
        side = mySide;
        speed = velocity;
        x = myX;
        originX = myX;
        y = myY;
        if (side == Color.BLUE) //varying shades of blue for myCastle
        {
            if (num == 1) {
                myColor = new Color(135, 206, 240);
            } else if (num == 2) {
                myColor = new Color(0, 96, 255);
            } else {
                myColor = new Color(0, 0, 96);
            }
        } else //varying shades of red for enemyCastle
        {
            if (num == 1) {
                myColor = new Color(240, 182, 193);
            } else if (num == 2) {
                myColor = new Color(203, 32, 39);
            } else {
                myColor = new Color(124, 10, 2);
            }
        }
        originColor = myColor;
    }

    public void SetMultiplier(double w) {
        statMultiplier = (int) statMultiplier * w;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(myColor);
        g.fillRect(x, y, width, height);
    }

    public void animate() {
        //int elapsedTime = (int)Math.ceil((System.currentTimeMillis() -  start)/1000);  //timer per second
        //int elapsedTimeSpeed = (int)(Math.ceil((System.currentTimeMillis() - start)/1000));  //timer per second

        //originX += elapsedTimeSpeed;
        x += speed;

    }

    /**
     *
     * @param other is the troop in front (make a list of troops, troop at index
     * 0 is closest to enemy)
     * @return
     */
    public boolean canMove(Troop other) //checks if troop can move or if opponent is in front
    {
        int myB = x + width;
        if (side == Color.RED) {
            myB = x;
        }
        int oB = other.getX();
        if (other.getSide() == Color.RED) {
            oB = other.getX() + other.getWidth();
        }

        if (Math.abs(myB - oB) <= 10) //if the distance is greater than or equal to 10, return false
        {
            return false;
        }
        return true;
    }

    /**
     *
     * @param other
     * @return Returns true if the troop is by an enemy
     */
    public boolean byEnemy(Troop other) //checks if first troop is by the other castle's first troop
    {
        int myB = x + width;
        if (side == Color.RED) {
            myB = x;
        }
        int oB = other.getX();
        if (other.getSide() == Color.RED) {
            oB = other.getX() + other.getWidth();
        }

        if (Math.abs(myB - oB) <= gap) {
            return true;
        }
        return false;
    }

    //important things to return/variable variables

    public void setHP(int num) {
        currHP = num;
    }

    public int getHP() {
        return currHP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getAttack() {
        return attack;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public int getNum() {
        return numT;
    }

    public long getStartTime() {
        return start;
    }

    public void setMultiplier(int multiplier) {
        statMultiplier = multiplier;
    }

    public Color getSide() {
        return side;
    }

    public Color getTroopColor() {
        return myColor;
    }

    public void setTroopColor(Color nC) {
        myColor = nC;
    }

    public void resetTroopColor() {
        myColor = originColor;
    }
}
