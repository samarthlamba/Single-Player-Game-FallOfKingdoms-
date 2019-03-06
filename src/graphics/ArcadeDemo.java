package graphics;

/**
 * Class ArcadeDemo This class contains demos of many of the things you might
 * want to use to make an animated arcade game.
 *
 * Adapted from the AppletAE demo from years past.
 */
import gameObjects.Castle;

import gameObjects.Store;
import gameObjects.Troop;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author 18lambas
 */

public class ArcadeDemo extends AnimationPanel {

    private String mode = "store"; //This mode can be "game" or "store" to determine which screen is displayed
    private Store myStore = new Store(); //Each player has a store which stores their stats and allows them to chnage their stats
    public Castle myCastle = new Castle(Color.BLUE, 0, 350);
    public Castle enemyCastle = new Castle(Color.RED, 1164, 350);
    private int projectileCount = 0;
    private int maxY = 0;
    private int maxX = 0;
    private int minX = 0;
    private int minY = 0;
    private int locX = 220;
    private int locY = 120;
    private int recSizeX = 50;
    private int recSizeY = 50;
    private boolean cheatOn;
    private int count;
    private long reachMC;
    private long reachEC;
    private boolean gameOn;
    private boolean lost;
    private int enemyUpgrade = 1;

    //Constants
    //-------------------------------------------------------
    //Instance Variables
    //-------------------------------------------------------
    boolean zPressed;

    //Constructor
    //-------------------------------------------------------
    public ArcadeDemo() {   //Enter the name and width and height.  
        super("ArcadeDemo", 1280, 580);
        zPressed = false;
        count = 0;
        gameOn = true;

    }

    //The renderFrame method is the one which is called each time a frame is drawn.
    //-------------------------------------------------------
    protected Graphics renderFrame(Graphics g) {
        if (gameOn) {
            myStore.drawStore(g, 0, 0);
            g.drawLine(0, 150, 1280, 150);
            g.drawLine(0, 151, 1280, 151);
            g.drawLine(0, 152, 1280, 152);
            g.drawLine(0, 153, 1280, 153);
            //basic stats to know
            g.drawString("Number of Infantry alive: " + myCastle.getAllInfantry().size(), 25, 175);
            g.drawString("Number of Ranged alive: " + myCastle.getAllRanged().size(), 25, 200);
            g.drawString("Number of Elite soldiers alive: " + myCastle.getAllElite().size(), 25, 225);
            g.drawString("Number of Infantry alive: " + enemyCastle.getAllInfantry().size(), 1050, 175);
            g.drawString("Number of Ranged alive: " + enemyCastle.getAllRanged().size(), 1050, 200);
            g.drawString("Number of Elite soldiers alive: " + enemyCastle.getAllElite().size(), 1050, 225);

            myCastle.draw(g);
            enemyCastle.draw(g);
            //draws Castle HP
            g.drawString(myCastle.getHP() + "", myCastle.getX() + 30, 340);
            g.drawString(enemyCastle.getHP() + "", enemyCastle.getX() + 45, 340);

            //upgrades the enemy castle at the start so that the enemy starts out stronger
            if (count == 0) {
                enemyCastle.upgrade();
            }
            count++;
            /* CURRENTLY WORKING

             ArrayList<Integer> nList = new ArrayList<Integer>();
             if (nList.contains(count))
             enemyCastle.generateRandomEnemies();
             if (count == 5)
             enemyCastle.addTroop((new Troop (1, Color.RED, enemyCastle.getMultiplier(), -1, getX()-5, getY()+150, System.currentTimeMillis()))); 

             int ranMultiply = 
             */

        //upgrading the enemyCastle stats at the start
            //regularly upgrades the enemyCastle
            if (count % 5000 == 0) {
                enemyCastle.upgrade();
            }

            //making more enemies as time goes on
            int mult = 100 - count / 400;
            if (mult <= 80) {
                mult = 80;
                mult = 80 - count / 1000;
            }
            if (mult <= 50) {
                mult = 50;
            }
            //generate random enemy every random time
            int ranN = (int) (Math.random() * mult);
            if (ranN == 0) {
                enemyCastle.generateRandomEnemies();
            }

            //creates elite troops every so often
            if (mult <= 30 && count % 500 == 0) {
                enemyCastle.addTroop(new Troop(3, Color.RED, enemyCastle.getMultiplier(), -1, enemyCastle.getX() - 5, enemyCastle.getY() + 150, System.currentTimeMillis()));
            }

            if (enemyCastle.getNumTroops() > 0) //if there are troops
            {
                if (enemyCastle.firstTroop().getX() <= 120)// reached myCastle location and killing myCastle
                {
                    if (myCastle.getHP() >= myCastle.getMaxHP() && myCastle.getNumTroops() < 1) {
                        //System.out.println(myCastle.getHP() + "castle hp pre attack");
                        myCastle.setHP(myCastle.getHP() - enemyCastle.firstTroop().getAttack());
                        //System.out.println(myCastle.getHP() + "castle hp after attack");
                    }
                    if (count % 40 == 0 && myCastle.getNumTroops() < 1) {
                        myCastle.setHP(myCastle.getHP() - enemyCastle.firstTroop().getAttack());
                    }
                    //System.out.println("Reached");
                }
                if (enemyCastle.getNumTroops() >= 2) //if there are two or more troops
                {
                    for (int x = 1; x < enemyCastle.getAllTroops().size(); x++) //animate all except the first troop
                    {
                        if (enemyCastle.getTroop(x).canMove(enemyCastle.getTroop(x - 1))) //HERE TO MAKE SURE THEY CAN MOVE
                        {
                            enemyCastle.getTroop(x).animate(); //moves all the troops IF NO ONE IN FRONT
                        }
                    }
                }

                //moving the first troop and such
                if (myCastle.getNumTroops() < 1 && enemyCastle.getNumTroops() > 0 && enemyCastle.firstTroop().getX() > 120) {
                    enemyCastle.firstTroop().animate();
                } else if (myCastle.getNumTroops() > 0 && enemyCastle.getNumTroops() > 0 && !enemyCastle.firstTroop().byEnemy(myCastle.firstTroop()) && enemyCastle.firstTroop().getX() > 120) {
                    enemyCastle.firstTroop().animate();
                }
            }
            if (!enemyCastle.getAllTroops().isEmpty()) {
                g.drawString(enemyCastle.firstTroop().getHP() + "", enemyCastle.firstTroop().getX() - 1, 490);
            }
            if (!myCastle.getAllTroops().isEmpty()) //if there are troops by the myCastle
            {
                if (myCastle.getNumTroops() >= 2) //if there are at lest 2 troops
                {
                    for (int x = 1; x < myCastle.getAllTroops().size(); x++) //move the troops if they can move
                    {
                        if (myCastle.getTroop(x).canMove(myCastle.getTroop(x - 1))) //HERE TO MAKE SURE THEY CAN MOVE
                        {
                            myCastle.getTroop(x).animate(); //moves all the troops IF NO ONE IN FRONT
                        }
                    }
                }
                if (myCastle.getNumTroops() >= 1) //animates the first troop if there are no enemies, or no enemies in distance
                {
                    g.drawString(myCastle.firstTroop().getHP() + "", myCastle.firstTroop().getX() - 1, 490);
                    if ((enemyCastle.getNumTroops() < 1 || !myCastle.firstTroop().byEnemy(enemyCastle.firstTroop())) && myCastle.firstTroop().getX() < 1144) {
                        myCastle.firstTroop().animate();
                    }
                    if (myCastle.firstTroop().getX() >= 1139) //reached the enemyCastle and killing the enemyCastle
                    {
                        if (enemyCastle.getHP() >= enemyCastle.getMaxHP() && enemyCastle.getNumTroops() < 1) {
                            enemyCastle.setHP(enemyCastle.getHP() - myCastle.firstTroop().getAttack());
                            if (count % 160 == 0) //when myCastle troops have reached the enemyCastle, generate troops every 160 frames
                            {
                                enemyCastle.generateRandomEnemies();
                            }
                        } else if (count % 40 == 0 && enemyCastle.getNumTroops() < 1) {
                            enemyCastle.setHP(enemyCastle.getHP() - myCastle.firstTroop().getAttack());
                            if (count % 160 == 0) {
                                enemyCastle.generateRandomEnemies();
                            }
                        }
                    }
                }
            //killing each other
                //doing ranged troops
            /*
                 if (myCastle.getNumTroops() >= 1 && enemyCastle.getNumTroops() >= 1 && count % 5 == 0)
                 {    
                 if (myCastle.ifRangedTroops())
                 {
                 for (Troop bob : myCastle.getTwoRanged())
                 {
                 if (bob.byEnemy(enemyCastle.firstTroop()))
                 enemyCastle.firstTroop().setHP(enemyCastle.firstTroop().getHP()-bob.getAttack());
                 }
                 }
                 if (enemyCastle.ifRangedTroops())
                 {
                 for (Troop bob : enemyCastle.getTwoRanged())
                 {
                 if (bob.byEnemy(myCastle.firstTroop()))
                 myCastle.firstTroop().setHP(myCastle.firstTroop().getHP()-bob.getAttack());
                 }
                 }
                 }
                 */
                //attacking and stuffs
                if (myCastle.getNumTroops() >= 1 && enemyCastle.getNumTroops() >= 1 && count % 5 == 0) //only deduct HP every five frames so you can see change in HP
                {
                    if (/*enemyCastle.firstTroop().getNum()!=2 &&*/enemyCastle.firstTroop().byEnemy(myCastle.firstTroop())) {
                        myCastle.firstTroop().setHP(myCastle.firstTroop().getHP() - enemyCastle.firstTroop().getAttack());
                    }
                    if (/*myCastle.firstTroop().getNum()!=2 &&*/myCastle.firstTroop().byEnemy(enemyCastle.firstTroop())) {
                        enemyCastle.firstTroop().setHP(enemyCastle.firstTroop().getHP() - myCastle.firstTroop().getAttack());
                    }

                    //removes troops when they die
                    if (myCastle.firstTroop().getHP() <= 0) {
                        myCastle.removeTroop();
                        enemyCastle.generateRandomEnemies();
                    }
                    if (enemyCastle.firstTroop().getHP() <= 0) { //giving the monies back
                        if (enemyCastle.firstTroop().getNum() == 1) {
                            myStore.addMoney((int) (myStore.returnCInfantry() * 1.5));
                        } else if (enemyCastle.firstTroop().getNum() == 2) {
                            myStore.addMoney((int) (myStore.returnCRanged() * 1.5));
                        } else if (enemyCastle.firstTroop().getNum() == 3) {
                            myStore.addMoney((int) (myStore.returnCElite() * 1.5));
                        }
                        enemyCastle.removeTroop();
                    }
                }
            }
            //draw all troops
            for (Troop bob : myCastle.getAllTroops()) {
                bob.draw(g);
            }

            for (Troop bob : enemyCastle.getAllTroops()) {
                bob.draw(g);
            }

//number 15 not working currently
     /* frameNumber++;
             if (frameNumber%10==0)
             {
             Projectile temp = new Projectile();
             temp.setX(ball.getX());
             temp.setY(ball.getY());
             int dx=mouseX=temp.getX();
             int dy= mouseY -temp.getY();
             double distance = Math.sqrt(dx*dx +dy*dy);
             
             temp.setxVel((int)(dx*5/distance));
             temp.setyVel((int)(dy*5/distance));
             laserArray.add(temp);
            
             }*/
        //Draw a ball that bounces around the screen.
        //number 2
        //Draw any 'lasers' that have been fired (spacebar).
            /* for (int x=0; x<laserArray.size(); x++)  // number 5
             {
             g.setColor(Color.GREEN);
             if (laserArray.get(x).isFrozen())
             {
                        
             laserArray.remove(x);
             }
             }*/
        //end game checking
            //when one castle reaches 0 HP, end game
            if (enemyCastle.getHP() <= 0) {
                this.gameOn = false;
                lost = false;
            } else if (myCastle.getHP() <= 0) {
                gameOn = false;
                lost = true;
            }
        } else //when game is over, clear the set, and print result
        {
            //    x= 1280, y=580);
            g.setFont(new Font("Arial", Font.PLAIN, 70));
            if (lost) {
                g.setColor(Color.red);
                g.drawString("GAME OVER: YOU LOSE", 230, 300);
            } else {
                g.setColor(Color.green);
                g.drawString("GAME OVER: YOU WIN", 230, 300);
            }

        }
        return g;

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getX() > 1800) //temp way to close store
        {
            mode = "game";
        } else if (mode.equals("store")) {
            myStore.buttonPressed(mouseX, mouseY, this, myCastle, enemyCastle);
        }
    }

    //-------------------------------------------------------
    //Respond to Keyboard Events
    //-------------------------------------------------------
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == 'x' || c == 'F') {

        }
    }

    public void keyPressed(KeyEvent e) {
        int v = e.getKeyCode();

        if (v == KeyEvent.VK_BACK_SPACE) {
            if (v == KeyEvent.VK_Z) {
                zPressed = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int v = e.getKeyCode();

        if (v == KeyEvent.VK_Z) {
            zPressed = false;
        }
    }
    AudioClip themeMusic;
    AudioClip bellSound;

    
      public void initMusic() { //themeMusic = loadClip("under.wav"); themeMusic =
      loadClip("music1.wav"); bellSound = loadClip("music.wav");
      
      
      if (themeMusic != null) { themeMusic.loop(); //This would make it play!
      
      }
		/*
		 * if (ball.getX() == 0) //number 21 { if(bellSound != null) { bellSound.play();
		 * }}
		 */
      }
}

    


//--end of ArcadeDemo class--

