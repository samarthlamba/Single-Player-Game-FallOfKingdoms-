/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 *
 * @author 18lambas
 */
public class Castle implements GameObject {

    private int currHP;
    private int nTurretSpots;
    private int money;
    private int exp;
    private Color side;
    private int x, y;
    private int width = 100, height = 200;
    private double statMultiplier;
    private ArrayList<Troop> troops;
    private ArrayList<Turret> turrets;
    private int maxHP;
    Image fairy;
    Image evil;

    public Castle(Color mySide, int myX, int myY) {

        currHP = 500;
        maxHP = 500;
        nTurretSpots = 1;
        money = 175;
        exp = 0;
        side = mySide;
        x = myX;
        y = myY;
        troops = new ArrayList<Troop>();
        turrets = new ArrayList<Turret>();
        statMultiplier = 1;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public long startTime() {
        return System.currentTimeMillis();
    }

    /*
     public void draw(Graphics g, ImageObserver i)
     {
     Toolkit toolkit = Toolkit.getDefaultToolkit();
     fairy = toolkit.getImage("fairy.gif");
     evil = toolkit.getImage("evil.gif");
     
     g.drawImage(fairy, 0, 350, i);
     g.drawImage(evil, 1164, 350,i);
     }
     */
    public void draw(Graphics g) {
        /*
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         fairy = toolkit.getImage("fairy.gif");
         evil = toolkit.getImage("evil.gif");
     
         g.drawImage(fairy, 0, 350, ArcadeDemo);
         g.drawImage(evil, 1164, 350,ArcadeDemo);
         */
        g.setColor(side);
        g.fillRect(x, y, width, height);

        /*
         for (Troop w : troops)
         {
         int elapsedTime = (int)Math.ceil((System.currentTimeMillis() -  w.getStartTime())/1000);  //timer per second
         int elapsedTimeSpeed = (int)Math.ceil((System.currentTimeMillis() -  w.getStartTime())/100); 
            
         if (w.getNum()==1)
         g.setColor(Color.GREEN);
         g.fillRect(x+100+ elapsedTimeSpeed, y+150, 5, 50);
         //System.out.println(elapsedTime);
         if (w.getNum()==2)
         g.setColor(Color.ORANGE);
         g.fillRect(x+100+ elapsedTimeSpeed, y+150, 5, 50);
         if (w.getNum()==3)
         g.setColor(Color.BLACK);
         g.fillRect(x+100+ elapsedTimeSpeed, y+150, 5, 50);
         }
         */
    }

    public String hasUpgraded() //just returns that opponent has upgraded
    {
        return ("Enemy has upgraded!");
    }

    //tonne of random useful returners

    public void setHP(int num) {
        currHP = num;
    }

    public int getHP() {
        return currHP;
    }

    public void increaseTurretSpots() {
        nTurretSpots++;
    }

    public void setMoney(int num) {
        money = num;
    }

    public int getMoney() {
        return money;
    }

    public void setEXP(int num) {
        exp = num;
    }

    public int getExp() {
        return exp;
    }

    public void upgrade() {
        statMultiplier *= 1.5;
    }

    ;
    public double getMultiplier() {
        return statMultiplier;
    }

    public void addTroop(Troop add) {
        troops.add(add);
    }

    public Troop getTroop(int x) {
        return troops.get(x);
    }

    public void removeTroop() {
        troops.remove(0);
    }

    public Troop firstTroop() {
        return troops.get(0);
    } //since the only troop that can fight closed distance is the first troop on the list

    public ArrayList<Troop> getAllTroops() {
        return troops;
    }

    public ArrayList<Troop> getOtherTroops() {
        ArrayList<Troop> temp = troops;
        temp.remove(0);
        return temp;
    }

    public int getNumTroops() {
        return troops.size();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return side;
    }

    public ArrayList<Turret> getTurrets() {
        return turrets;
    }

    public void addTurret(Turret add) {
        turrets.add(add);
    }

    public void generateRandomEnemies() //generates a random troop, with 70% chance of troop 1, 20% troop 2 and 10% troop 3
    {
        int num = (int) (Math.random() * 10);
        if (num < 7) {
            troops.add(new Troop(1, getColor(), getMultiplier(), -1, getX() - 5, getY() + 150, System.currentTimeMillis()));
        } else if (num < 9) {
            troops.add(new Troop(2, getColor(), getMultiplier(), -1, getX() - 5, getY() + 150, System.currentTimeMillis()));
        } else {
            troops.add(new Troop(3, getColor(), getMultiplier(), -1, getX() - 5, getY() + 150, System.currentTimeMillis()));
        }
    }

    public ArrayList<Troop> getAllRanged() //returns all ranged troops
    {
        ArrayList<Troop> ranged = new ArrayList<Troop>();
        for (Troop bob : troops) {
            if (bob.getNum() == 2) {
                ranged.add(bob);
            }
        }
        return ranged;
    }

    public ArrayList<Troop> getAllInfantry() //returns all infantry
    {
        ArrayList<Troop> ranged = new ArrayList<Troop>();
        for (Troop bob : troops) {
            if (bob.getNum() == 1) {
                ranged.add(bob);
            }
        }
        return ranged;
    }

    public ArrayList<Troop> getAllElite() //returns all elite
    {
        ArrayList<Troop> ranged = new ArrayList<Troop>();
        for (Troop bob : troops) {
            if (bob.getNum() == 3) {
                ranged.add(bob);
            }
        }
        return ranged;
    }

    public ArrayList<Troop> getTwoRanged() //returns first two ranged if they are there
    {
        ArrayList<Troop> ranged = new ArrayList<Troop>();
        for (Troop bob : troops) {
            if (bob.getNum() == 2) {
                ranged.add(bob);
            }
        }
        if (ranged.size() > 2) {
            for (int x = ranged.size() - 1; x > 1; x--) {
                ranged.remove(x);
            }
        }
        return ranged;
    }

    public boolean ifRangedTroops() //returns true if there are indeed ranged troops
    {
        for (Troop bob : troops) {
            if (bob.getNum() == 2) {
                return true;
            }
        }
        return false;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int newHP) {
        maxHP = newHP;
    }

    public void wipeBaseTroops() {
        troops.clear();
    }
}
