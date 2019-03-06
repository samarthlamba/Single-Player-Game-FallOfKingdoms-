/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameObjects;

import graphics.ArcadeDemo;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A store is an object which remembers which stats a player has upgraded Each
 * client has a store called myStore in the ArcadeDemo class Contains a store
 * display to upgrade stats
 *
 * @authors 18lambas and 18zhaoy
 */
public class Store {

    public int remainingSkillTokens;
    //Stores the number of skill points invested in each stat
    private ArrayList<Integer> stats = new ArrayList<Integer>();

    public ArrayList<Integer> getStats() {
        return stats;
    }
    //Health can be accessed as stats.get(HEALTH), speed as stats.get(SPEED), etc.
    final int INFANTRY = 0;
    final int RANGED = 1;
    final int NAVY = 2; //elite
    final int TURRETA = 3;
    final int TURRETB = 4;
    final int TURRETC = 5;
    final int UPGRADE = 6;
    private int firstColumn = 10 * 2;
    private int secondColumn = 190 * 2;
    private int thirdColumn = 410 * 2;
    private int firstRow = (int) (40 * 1.5);
    private int secondRow = (int) (60 * 1.5);
    private int thirdRow = (int) (80 * 1.5);
    private int moneyRow = (int) (20 * 1.5);
    private int cInfantry = 25;
    private int cRanged = 50;
    private int cElite = 100;
    private int cFix = 500;
    private int cFortify = 1000;
    private int cNuke = 1500;
    private int cUpgrade = 1000;
    private int xOffset = 20;
    private int yOffset = 0;
    private double statMultiplier;

    public Store() {
        stats = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            stats.add(0);
        }

        remainingSkillTokens = 500;
    }

    //Draws the store display. Store will have buttons to increase/decrease different stats.
    public void drawStore(Graphics g, int xOff, int yOff) {
        //Set the offsets
        xOffset = 20;
        yOffset = yOff;

        //Changing font to bold
        Font myFont = new Font("Arial", Font.BOLD, 16);
        g.setFont(myFont);

        //Displaying the number of remaining skill tokens
        g.drawString(remainingSkillTokens + " coins", firstColumn + xOffset - 12, moneyRow + yOffset);
        g.drawString("Infantry $" + cInfantry, firstColumn + xOffset - 12, firstRow + yOffset + 5);
        g.drawString("Ranged $" + cRanged, firstColumn + xOffset - 12, secondRow + yOffset + 5);

        g.drawString("Elite $" + cElite, firstColumn + xOffset - 12, thirdRow + yOffset + 5);
        g.drawString("Fix Base $" + cFix, secondColumn + xOffset - 40, firstRow + yOffset + 5);
        g.drawString("Fortify Base $" + cFortify, secondColumn + xOffset - 40, secondRow + yOffset + 5);
        g.drawString("WIPE FIELD $" + cNuke, secondColumn + xOffset - 40, thirdRow + yOffset + 5);
        g.drawString("Upgrade $" + cUpgrade, thirdColumn + xOffset - 40, firstRow + yOffset + 5);

        //Buttons to increase or decrease stats
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (i >= 0 && i < 3 && j != 2) {
                    g.drawRect(86 + firstColumn + xOffset + 400 * j + 10, 20 + firstRow - moneyRow + moneyRow * i, 20, 20);  //location of square 1st and second column
                }
                if (j == 2) {
                    g.drawRect(86 + firstColumn + xOffset + 400 * j + 10, 20 + firstRow - moneyRow + moneyRow * 0, 20, 20);  //location of square 1st and second column
                }
                if (j == 0 || j == 1) {
                    g.setColor(Color.RED);
                    g.fillRect(86 + firstColumn + xOffset + 400 * j + 10, 20 + firstRow - moneyRow + moneyRow * i, 20, 20);
                    g.setColor(Color.BLACK);

                }
                if (j == 2) {
                    g.setColor(Color.RED);
                    g.fillRect(86 + firstColumn + xOffset + 400 * j + 10, 20 + firstRow - moneyRow + moneyRow * 0, 20, 20);
                    g.setColor(Color.BLACK);
                }

            }
        }
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                if (j != 2) {
                    g.drawString("+", 93 + firstColumn + xOffset + 10 + 400 * j, 36 + firstRow - moneyRow + moneyRow * i);
                }
                if (j == 2) {
                    g.drawString("+", 93 + firstColumn + xOffset + 10 + 400 * j, 36 + firstRow - moneyRow + moneyRow * 0);
                }

            }

        }

        //Reset font
        g.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    public void addMoney(int mon) {
        remainingSkillTokens = remainingSkillTokens + mon;
    }

    //Called from arcadeDemo when mouse is clicked during store display mode. Detects button press, then changes stats
    public void buttonPressed(double mouseX, double mouseY, ArcadeDemo arcade, Castle base, Castle enemy) {

        for (int j = 0; j < 3; j++) {
            //System.out.println(base.getMultiplier());
            if (106 + firstColumn + xOffset + 400 * j - 10 < mouseX && mouseX < 86 + firstColumn + xOffset + 400 * j + 40) {  //94 + firstColumn + xOffset + 400*j, 35 + firstRow - moneyRow + moneyRow*i
                for (int i = 0; i < 3; i++) {
                    if (35 + firstRow - moneyRow + moneyRow * i - 20 < mouseY && 35 + firstRow - moneyRow + moneyRow * i + 10 > mouseY) {

                        stats.set(i, stats.get(i) + 1);
                        if (i == 0 && remainingSkillTokens >= cInfantry && j == 0) {
                            remainingSkillTokens -= cInfantry;
                            //System.out.println("25");
                            base.addTroop(new Troop(1, base.getColor(), base.getMultiplier(), 1, base.getX() + 100, base.getY() + 150, System.currentTimeMillis()));
                            //System.out.println(base.firstTroop());
                            //adds new troop to the end of the list of troops for the castle
                        }//Bullet stats cost 2 //cost of basic soldiers
                        if (i == 1 && remainingSkillTokens >= cRanged && j == 0) {
                            remainingSkillTokens -= this.cRanged; //cost of second basic soldiers
                            //System.out.println("50");
                            base.addTroop(new Troop(2, base.getColor(), base.getMultiplier(), 1, base.getX() + 100, base.getY() + 150, System.currentTimeMillis()));
                        }
                        if (i == 2 && remainingSkillTokens >= cElite && j == 0) {
                            remainingSkillTokens -= this.cElite; //cost of best soldiers
                            //System.out.println("100");
                            base.addTroop(new Troop(3, base.getColor(), base.getMultiplier(), 1, base.getX() + 100, base.getY() + 150, System.currentTimeMillis()));
                        }
                        if (i == 0 && remainingSkillTokens >= cFix && j == 1) {
                            remainingSkillTokens -= cFix; //fix base
                            base.setHP(base.getMaxHP());
                            //System.out.println("150");
                        }
                        if (i == 1 && remainingSkillTokens >= cFortify && j == 1) {
                            remainingSkillTokens -= cFortify; //fortify base
                            base.setMaxHP(base.getMaxHP() + 250);
                            base.setHP(base.getHP() + 250);
                            //System.out.println(base.getMaxHP()+" base max HP");
                            this.cFortify *= 1.3;
                        }
                        if (i == 2 && remainingSkillTokens >= cNuke && j == 1) {
                            remainingSkillTokens -= cNuke; //NUKE
                            //when nuking, returns all of the money gained by killing the people
                            for (Troop bob : enemy.getAllTroops()) {
                                if (enemy.firstTroop().getNum() == 1) {
                                    remainingSkillTokens += (cInfantry * 1.3);
                                } else if (enemy.firstTroop().getNum() == 2) {
                                    remainingSkillTokens += (cRanged * 1.3);
                                } else if (enemy.firstTroop().getNum() == 3) {
                                    remainingSkillTokens += (cElite * 1.3);
                                }
                            }
                            enemy.wipeBaseTroops();
                            /*
                             if (enemyCastle.firstTroop().getNum() == 1)
                             myStore.addMoney((int)(myStore.returnCInfantry()*1.5));
                             else if (enemyCastle.firstTroop().getNum() == 2)
                             myStore.addMoney((int)(myStore.returnCRanged()*1.5));
                             else if (enemyCastle.firstTroop().getNum() == 3)
                             myStore.addMoney((int)(myStore.returnCElite()*1.5));
                             enemyCastle.removeTroop();
                             */

                            //System.out.println("650");
                        }
                        if (i == 0 && remainingSkillTokens >= cUpgrade && j == 2) {
                            remainingSkillTokens -= cUpgrade; //cost of upgrade
                            //increases costs of soldiers and a future upgrade
                            cUpgrade *= 1.3;
                            cInfantry *= 1.3;
                            cRanged *= 1.3;
                            cElite *= 1.3;
                            //System.out.println("1000");
                            base.upgrade();
//                                statMultiplier*=1.5;
                        }

                    }  //Others cost 1
                }
            }
        }
    }

    public int returnCInfantry() {
        return this.cInfantry;
    }

    public int returnCRanged() {
        return this.cRanged;
    }

    public int returnCElite() {
        return this.cElite;
    }

    //Exit Button
}
