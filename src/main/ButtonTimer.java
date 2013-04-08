package main;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*; 
import java.net.URL;
/**
    The goal of this class is to implement a square of the board as a button.
    The button should maintain the correct state, that is, the correct player, 
    and should change it according to the game's rules.
*/
    public  class ButtonTimer extends JButton { 
        //If square is free, it can be occupied by the other player.
        public static final int FREE = -1;
        //The square is occupied by player  #1
        public static final int PLAYER_1 = 0;
        //The square is occupied by player  #2
        public static final int PLAYER_2 = 1;
        //This array stores the buttons' images.
        public static Icon[] images;				
            // Static constructor
            static {
                images = new ImageIcon[] { 
                    //We will do so that so as to take images in the Jar 
                    new ImageIcon("src/img/x.jpg"),
                    new ImageIcon("src/img/o.jpg") 
                };
            }	
        //The state of the button, that is, the correct player.			 
        private int ownerPlayer;
        //Default constructor.
        public ButtonTimer() {
            // We always call to the base class constructor (always)
            super();
            // First, we´ll adjust the state to FREE
            setPlayer(FREE);
        }
        //This method will return true if it's free, or false if it's owned by a player.
        public boolean isFree() {
            return ownerPlayer == FREE;
        }
        //The new player that has "marked" the square, that is, that has clicked the botton,
        //updates the state of the button with the new player which is passes as parameter.
        public void setPlayer(int newPlayer) {
            //New player - updated
            ownerPlayer = newPlayer;
            //The button's icon will change according to the state.
                if (ownerPlayer == FREE) {
                    //The super class method is called to delete the button's image.
                    setIcon(null);
                }else{
                    //The super class method is called to update the button's image.
                    setIcon( images[ ownerPlayer ] );
                }
        }
        //Return The button's owner player.
        public int getPlayer() { 
            return ownerPlayer; 
        }
    }
