package main;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
/**
The goal of this class is to model the Nought and Crosses game. 
It has been developed with JFrame, building a board as a bidimentional 
matrix of ButtonTimer.The Event Management went achived in a 
centralized manner with a single Event Listener.
*/
public class NoughtAndCrosses extends JFrame {
    //It's a windows title.
    public static final String TITTLE = "Nought and Crosses"; 
    //The max size of the board will be 3
    public static final int BOARD_SIZE = 3;
    //The goal of this array is to know the number of figures placed by each player.
    public static int[] placedFigures = { 0, 0 };   
    //To know the actual turn's player.
    private int turnPlayer; 
    /** =========================
    GRAPHIC ATTRIBUTES DECLARATION 
     ========================= **/
    //This Label will show you the actual turn player.
    private JLabel actualTurn;
    //This Label will show you the image of the actual player.
    private JLabel labelFigure;
    //This is a bidimensional array of buttons that will represent the main board of the game.
    private ButtonTimer[][] board;
    //This panel will contain the square with the buttons.
    private JPanel panel_board;
    //This panel will contain the JLabels.
    private JPanel panel_info;	
    /** =========================
          WINDOW's DIMENSION 
     ========================= **/
    //Window's width
    public static final int WIDTH = 400;
    //Window's height
    static final int HEIGHT = 470;
    //Maximum time by each player.
    static final int MAX_TURN_TIME = 15000;
    //This label will hold the turn's remaining time.
    public JLabel labelTimer;	
    //Timer
    public Timer t;
    public String player1,player2;
    //Class Constructor.
    //@param title, player 1 and player 2.
    public NoughtAndCrosses( String title, String user1, String user2 ) {
        super(title);
        player1=user1;
        player2=user2;
        //This auxiliary method will build the graphics elements of the game.
        buildGraphicElements();
        //This auxiliary method will add the graphics elements to the containers.
        addElementsToContainers();
        //This auxiliary method will create the Event Listener.
        createEventListener();
        //Initialization process (the rest) 
        turnPlayer = ButtonTimer.PLAYER_1;
        //We should pack previously to show.
        pack();
      	//We will create the Timer Object.
        t = new Timer( 1000, new TimerListener(this) );
        t.start();
        setVisible(true);
    }
    //This method will create the Graphic elements such as: labels, buttons, panels..
    private void buildGraphicElements() {
        Random generator=new Random();
        int r=generator.nextInt(2);
        if (r==0){
            //This is a descriptive label so as to show the corresponding turn.
            actualTurn = new JLabel("Turn: "+ player1);
        }else if(r==1){
            actualTurn = new JLabel("Turn: "+ player2);
        }
        //This label will show you the remaining time.
        labelTimer = new JLabel("Remaining time : ");
        //It will start with Player 1
        labelFigure = new JLabel(ButtonTimer.images[ ButtonTimer.PLAYER_1]);
        //It will create the main buttons array so as to add to the panel.
        board = new ButtonTimer[BOARD_SIZE][BOARD_SIZE];
        for (int f=0; f<BOARD_SIZE; f++) {
            for (int c=0; c<BOARD_SIZE; c++) {
               board[f][c] = new ButtonTimer();
               board[f][c].setBackground(Color.white);
            }
         }
    }
    //This auxiliary method will add elements to its containers.
    private void addElementsToContainers(){
        //It will create the panels to add elements to the main board panel.
        panel_board = new JPanel();
        panel_board.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        //It will add buttons to the container.
        for (int f=0; f<BOARD_SIZE; f++) {
            for (int c=0; c<BOARD_SIZE; c++) {
                panel_board.add(board[f][c]);
            }
        }
        //The info's panel.
        panel_info = new JPanel();
        //It will add the actual turn player info with the associated image.
        panel_info.add(actualTurn);
        panel_info.add(labelFigure);
        //It will add the label with the remaining time.
        panel_info.add(labelTimer);
        //We will add the previous panels to the main panel container and then 
        //We will add the info's panel to the North and the board's panel to the South
        //First, we should obtain a reference to the Main's Windows panel container.
        Container content = getContentPane();
        content.add(panel_info,BorderLayout.NORTH);
        content.add(panel_board,BorderLayout.CENTER);
    }
    //This method will configure the Event Listener for graphics elements, that is, the buttons.
    private void createEventListener() {
        //First, we should create the Listener that will manage the events of the buttons.
        ButtonListener butListen  = new ButtonListener(this);
        //Then, we will register the Listener on each button
        for (int f=0; f<BOARD_SIZE; f++) 
            for (int c=0; c<BOARD_SIZE; c++) 
                board[f][c].addActionListener(butListen);
    }
    //This method will change the turn's player. 
    public void changeTurn() {
        //We will reset the second's counter.
        TimerListener.sec_count=0;
        if (turnPlayer ==  ButtonTimer.PLAYER_1) {
            turnPlayer =  ButtonTimer.PLAYER_2;
            actualTurn.setText("Turn: "+ player2);
            labelFigure.setIcon(ButtonTimer.images[ ButtonTimer.PLAYER_2]);
        }else{
            turnPlayer =  ButtonTimer.PLAYER_1;
            actualTurn.setText("Turn: "+ player1);
            labelFigure.setIcon(ButtonTimer.images[ ButtonTimer.PLAYER_1]);
        }
    }
    //This method will test who player has won the game.
    private boolean hasWon( int player ) {
        int countFigures = 0;	
        //Is there a line of figures of the player?
        //So, Horizontal lines
        for (int i=0; i<BOARD_SIZE; i++) {
            countFigures = 0;
            for (int j=0; j<BOARD_SIZE; j++) {
                if ( board[i][j].getPlayer() == player ) {
                    countFigures ++;
                    if ( countFigures == BOARD_SIZE ) {
                        return true;
                    }
                }
            }
        }
        //Or, Vertical lines
        for (int i=0; i<BOARD_SIZE; i++) {
            countFigures = 0;
            for (int j=0; j<BOARD_SIZE; j++) {
                if ( board[j][i].getPlayer() == player ) {
                    countFigures ++;
                    if ( countFigures == BOARD_SIZE ) {
                        return true;
                    }
                }
            }
        }    	
        //...First Diagonal 
        countFigures = 0;
        for (int i=0; i<BOARD_SIZE; i++) {
            if ( board[i][i].getPlayer() == player ) {
                countFigures ++;
                if ( countFigures == BOARD_SIZE ) {
                    return true;
                }
            }
        }  
        countFigures = 0;	    	
        //Other First Diagonal
        for (int i=0; i<BOARD_SIZE; i++) {
            if ( board[BOARD_SIZE-(i+1)][i].getPlayer() == player ) {
                countFigures ++;
                if ( countFigures == BOARD_SIZE ) {
                    return true;
                }
            }
        }
        return false;    
    }
    /**
     The goal of this class will be the Listener of Events that will be occur 
     *by the buttons on the main board. This Events will be occur as an 
     ActionEvents and, it should implement the corresponding method of the 
     interface ActionListener.
     */
    class ButtonListener implements ActionListener {
        //We will a reference's frame so as to access to the elements we want to change.
        private JFrame miFrame;
        //Class constructor.
        //@param frame: The frame that we need to store so we can access to the elements we want to modify later.
        public ButtonListener( JFrame frame ) {
            this.miFrame = frame;
        }
        //-- Method of the ActionListener interface that has to be overwritten.
        //@param event: The ActionEvent's event that is occur when the button is clicked.
        public void actionPerformed(ActionEvent event) {
            //First, we should obtain the reference of the button has been pressed.
            ButtonTimer clickedButton = (ButtonTimer) event.getSource();
            //Has the player figures left to put still? (size of the board)
            if (placedFigures[turnPlayer] < BOARD_SIZE) { 
                //We will check whether the square is free or not.
                if (clickedButton.isFree()) {
                    //We will update the button.
                    clickedButton.setPlayer(turnPlayer);
                    //We will increment the counter of placed figures by the player.
                    ((NoughtAndCrosses)miFrame).placedFigures[turnPlayer]++;
                    //Is there any winning?
                    if ( placedFigures[turnPlayer] == BOARD_SIZE) {
                        //We will check whether any player have won the game.
                        if ( hasWon(turnPlayer) ) {
                            if(turnPlayer==0){
                               javax.swing.JOptionPane.showMessageDialog(miFrame, "Congratulation!!!, "+player1+","+" you have won the game !!!" );
                               System.exit(0); 
                            }else if (turnPlayer==1){
                               javax.swing.JOptionPane.showMessageDialog(miFrame, "Congratulation!!!, "+player2+","+" you have won the game !!!" );
                               System.exit(0);
                            }
                        }
                    }
                    //Finally, we will change the player's turn. 
                    changeTurn();
                }else{  
                    //We will show a Message Dialog with an error message.
                    javax.swing.JOptionPane.showMessageDialog(miFrame, "You can't place a token there.");
                }
            }else{  
                //We should take out one token to move it since we don't have more figures.
                actualTurn.setText("¡You must take out one token and to move it!");
                //We should check if we are the correct player to take out it.
                if (clickedButton.getPlayer() == turnPlayer ) {
                    //The square will become free.
                    clickedButton.setPlayer( ButtonTimer.FREE);
                    //It will decrement the counter of placed figures.
                    ((NoughtAndCrosses)miFrame).placedFigures[turnPlayer]--;
                }else{
                    //Wrong, We aren't the correct player.
                    javax.swing.JOptionPane.showMessageDialog(miFrame, "It can't place a token there.");
                }
            }
        }
      }		
 }
class TimerListener implements ActionListener {
    //We will a reference's frame so as to access to the elements we want to change.
    private JFrame miFrame;
    //Class constructor.
    //@param frame: The frame that we need to store so we can access to the elements we want to modify later.
    public TimerListener( JFrame frame ) {
        this.miFrame = frame;
    }
    //Counter of seconds.
    public static int sec_count=0; 
    //This is the method of the ActionListener interface that has to be overwritten.
    //@param event: The ActionEvent's event that is occur when the time period has been reached.
    public void actionPerformed(ActionEvent event) {
        int temp = NoughtAndCrosses.MAX_TURN_TIME - sec_count*1000;
        if ( temp < 0 ) {
            sec_count=0;
            javax.swing.JOptionPane.showMessageDialog(miFrame, "Reached Time!!" );
            ((NoughtAndCrosses)miFrame).changeTurn();
        }else{
            ((NoughtAndCrosses)miFrame).labelTimer.setText("Remaining time : "+temp/1000);
            sec_count++;
        }
    }
}

