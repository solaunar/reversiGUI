import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class Window extends JPanel implements MouseListener {
	GamePlayer gamer = new GamePlayer();
	
	int [][] dataCopy;			// Used in method Available
	int turn = 1; 				// Variable turn initialized as 1, player goes first
	Move AI = new Move();
	
	Board board = new Board();
    int[][] data = board.getGameBoard();
    
    private int black = board.getXScore();
    private int white = board.getOScore();
    
    static int gameSizeInt = 8;

    JFrame frame = new JFrame();
    JPanel startingScreen = new JPanel(); 	// The startingScreenPanel contains the menu section of the game
    JPanel panel = new JPanel();			// The panel contains the interactive board section of the game
    JPanel endScreen = new JPanel();		// The endScreen contains the results and buttons for Restarting and Exiting the game


    static int cprint = 0;
    static int fontX = 10;
    static int fontY = 496;
    
    
public Window(){

	// Window Properties
    frame.setTitle("Reversi");
    frame.setLocationRelativeTo(null);
    frame.setLocation(450, 150);
    
    //Setting panel dimensions
    startingScreen.setPreferredSize(new Dimension(481, 505));
    panel.setPreferredSize(new Dimension(481, 505));
    endScreen.setPreferredSize(new Dimension(481, 505));
    
    frame.setResizable(false);									 // Window cannot be resized dynamically
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		 // Exit window when closed
    frame.getContentPane().setBackground( new Color(11,102,35)); // Window background color
    
    /* BUTTON FORMATING */
    
    //Button start of Starting Panel
    JButton Start = new JButton("PLAY");
    Start.setBackground(new Color(170,230,255));
    Start.setForeground(new Color(30,30,130));
    Start.setFont(new Font ("Courier", Font.BOLD, 24));
    Start.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
    
    //Button Group Difficulty of Starting Panel
    ButtonGroup Difficulty = new ButtonGroup();
    
    	//Button Easy of Starting Panel
        JRadioButtonMenuItem Easy = new JRadioButtonMenuItem("Easy");
        Easy.setBackground(new Color(170,230,255));
        Easy.setForeground(new Color(30,30,130));
        Easy.setFont(new Font ("Courier", Font.BOLD, 14));
        Easy.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
        
        //Button Normal of Starting Panel
        JRadioButtonMenuItem Normal = new JRadioButtonMenuItem("Normal");
        Normal.setBackground(new Color(170,230,255));
        Normal.setForeground(new Color(30,30,130));
        Normal.setFont(new Font ("Courier", Font.BOLD, 14));
        Normal.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
        
        //Button Hard of Starting Panel
        JRadioButtonMenuItem Hard = new JRadioButtonMenuItem("Hard");
        Hard.setBackground(new Color(170,230,255));
        Hard.setForeground(new Color(30,30,130));
        Hard.setFont(new Font ("Courier", Font.BOLD, 14));
        Hard.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
        
        Difficulty.add(Easy);
        Difficulty.add(Normal);
        Difficulty.add(Hard);
        
    //Button Group Turn of Starting Panel
    ButtonGroup Turn = new ButtonGroup();
    	
    	//Button First of Starting Panel
        JRadioButtonMenuItem First = new JRadioButtonMenuItem(" 1st");
        First.setBackground(new Color(170,230,255));
        First.setForeground(new Color(30,30,130));
        First.setFont(new Font ("Courier", Font.BOLD, 14));
        First.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
        
        //Button Second of Starting Panel
        JRadioButtonMenuItem Second = new JRadioButtonMenuItem(" 2nd");
        Second.setBackground(new Color(170,230,255));
        Second.setForeground(new Color(30,30,130));
        Second.setFont(new Font ("Courier", Font.BOLD, 14));
        Second.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
        
        Turn.add(First);
        Turn.add(Second);
        
    //Button exitGame of Ending Panel
    JButton exitGame = new JButton("Exit");
    exitGame.setBackground(new Color(170,230,255));
    exitGame.setForeground(new Color(30,30,130));
    exitGame.setFont(new Font ("Courier", Font.BOLD, 16));
    exitGame.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
    
    //Button newGame of Ending Panel
    JButton newGame = new JButton("New Game");
    newGame.setBackground(new Color(170,230,255));
    newGame.setForeground(new Color(30,30,130));
    newGame.setFont(new Font ("Courier", Font.BOLD, 16));
    newGame.setBorder(BorderFactory.createBevelBorder(1, new Color(68,152,238), Color.white));
    
    /* WINDOW ACTION LISTENERS */
    
    // newGame button restarts Window, returns to starting panel and re-initializes all data
    newGame.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	turn = 1; 		// Turn re-initialized as 1
        	board = new Board();
        	gamer = new GamePlayer();
        	data = board.getGameBoard();
        	black = board.getXScore();
        	white = board.getOScore();
        	frame.remove(endScreen);
        	frame.add(startingScreen);
        	frame.pack();
        	frame.repaint();
        }
    });
    
    // exitGame closes the window when pressed
    exitGame.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	System.exit(0);
        }
    });
    
    // Easy sets maxDepth to 3 when pressed, Easy is preselected since maxDepth is initialized with 3
    Easy.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	gamer.setMaxDepth(3);
        }
    });
    
    // Normal sets maxDepth to 5 when pressed
    Normal.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	gamer.setMaxDepth(5);
        }
    });
    
    // Hard sets maxDepth to 7 when pressed
    Hard.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	gamer.setMaxDepth(7);
        }
    });
    
    // First sets turn to 1 when pressed, First is preselected since turn is initialized with 1
    First.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	turn = 1;
        	gamer.setPlayer(Board.O); // AI gets White discs since it plays second
        }
    });
    
    // Second sets turn to 2 when pressed
    Second.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	turn = 2;
        	gamer.setPlayer(Board.X); // AI gets Black discs since it plays first
        }
    });
    
    // Start removes starting screen and adds panel when pressed, the user may now play
    Start.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	frame.remove(startingScreen);
        	frame.add(panel);
        	frame.pack();
        	frame.repaint();
        }
    });
    
    /* PANEL FORMATING AND ATTRIBUTES */
    
    // starting panel has 6 buttons, Start, Easy, Normal, Hard, First and Second
    startingScreen = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Setting window background color
            g.setColor(new Color(0,130,236));
            g.fillRect( 0,   0, 1000, 1000);
            
            // Adding starting panel's Image 
            try {                    
                g.drawImage(ImageIO.read(new File("images/panel1.png")),  0,  0,  null);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            // Button location and size
            Start.setBounds(190, 200, 100, 45);
            
            // Setting text color and font
            g.setFont(new Font ("Courier", Font.BOLD, 17));
            g.setColor(new Color(30,30,130));
            
            // Button group Title
            g.drawString("Difficulty" , 105, 285);
            // Button locations and size
            Easy.setBounds(114, 300, 80, 30);
            Normal.setBounds(114, 335, 80, 30);
            Hard.setBounds(114, 370, 80, 30);
            
            // Button group Title
            g.drawString("Turn" , 310, 285);
            // Button locations and size
            First.setBounds(290, 300, 80, 30);
            Second.setBounds(290, 335, 80, 30);
            
            Easy.setSelected(true);		// Preselected since maxDepth is initialized with value 4
            First.setSelected(true);	// Preselected since turn is initialized with value 1
            
            // adding buttons
            startingScreen.add(Start);
            startingScreen.add(Easy);
            startingScreen.add(Normal);
            startingScreen.add(Hard);
            startingScreen.add(First);
            startingScreen.add(Second);
        }
      
        @Override
        public Dimension getPreferredSize() {            	
        	  return new Dimension(481, 505);   
        }
    };
            
    // Adding action listener to starting screen
    startingScreen.addMouseListener(this);
    
    // panel has the game board, it displays white and black discs as well as available moves for the player highlighted with blue
    panel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Printing the empty board
            for (int i = 0; i < gameSizeInt; i++)
    			for (int j = 0; j < gameSizeInt; j++) {
    				g.setColor(new Color(11,102,35));
    				g.fillRect( i * 60,   j * 60, 60, 60);	// Draws green filled square
    				g.setColor(Color.black);
    				g.drawRect( i * 60,  j * 60, 60, 60);	// Draws black square border
    			}
            
            // By calling the Available method we add the value 2 to every available move cell
            data = Available (- (gamer.getPlayer()), board);
            
            // Printing discs and highlighting available moves
            for(int i = 0 ;i < 8;i++){
            	 for(int j = 0 ;j < 8;j++){
            		 switch (data[j][i]) {
                     case 0:   break;
                             
                     case 1: 
                    	// Printing white discs where the table has a value of 1
                    	 g.setColor(Color.BLACK);
                    	 g.fillOval(5+i * 60, 5+j * 60, 50, 50);	// Draws black filled circle
                    	 g.setColor(Color.GRAY);
                    	 g.drawOval(10+i * 60, 10+j * 60, 40, 40);	// Draws smaller gray circle for detail
                         if(AI.getRow()== j && AI.getCol() == i && gamer.getPlayer() == Board.X) {
                         	g.setColor(new Color(0,130,236));
                         	g.fillOval(20 + i * 60, 20 + j * 60, 20, 20); // Draws smaller blue filled circle on AI's last played disk
                         }
                             break;
                     case -1:
                    	 // Printing white discs where the table has a value of -1
                         g.setColor(Color.WHITE);
                         g.fillOval(5+i * 60, 5+j * 60, 50, 50);	// Draws white filled circle
                         g.setColor(Color.LIGHT_GRAY);
                         g.drawOval(10+i * 60, 10+j * 60, 40, 40);	// Draws smaller gray circle for detail
                         if(AI.getRow()== j && AI.getCol() == i && gamer.getPlayer() == Board.O) {
                          	g.setColor(new Color(0,130,236));
                          	g.fillOval(20 + i * 60, 20 + j * 60, 20, 20); // Draws smaller blue filled circle on AI's last played disk
                         }
                         	break;
                     case 2:
                    	 // Highlighting available moves where the table has a value of 2
                    	 if (gamer.getPlayer() == board.getlastColorPlayed()) {
                        	 g.setColor(new Color(68,152,238));
                             g.drawRect(i * 60,j * 60, 60, 60); // Draws blue square border
                    	 }
                         	break;
            		 }
                 }
            }
            
            //The RemoveTwos method erases the value of cells with 2 after the user has made their move
            data = RemoveTwos (data);
            
            // Updates score values for each color
            black = board.getXScore();
            white = board.getOScore();
            
            // Controls status messages on the bottom of the window
            if(!board.canPlay(Board.X)) {
           	 	cprint = 1;
            }
            if(!board.canPlay(Board.O)) {
            	cprint = 2;
            }
            
            // Setting text color and font
          	g.setColor(Color.BLACK);
          	g.setFont(new Font ("HelveticaNeue", Font.ITALIC, 15));
          	String pturn;
          	//Printing status messages on the bottom of the window
       	    if(cprint == 0){
       	    	pturn = (board.getlastColorPlayed() == -(gamer.getPlayer()) ? " |  AI Plays, click to continue." : " |  Player's turn, make a move.");
               	if(black > white){
               		g.drawString("Black is winning.   | Black : " + black + "  White : " + white + pturn, fontX, fontY);
                }else if(black == white){
                       g.drawString("No one is winning.   | Black : " + black + "  White : " + white + pturn, fontX, fontY);
               	}
                else{
               		g.drawString("White is winning.   | Black : " + black + "  White : " + white + pturn, fontX, fontY);
               	}
            }
       	    else if(cprint == 1){
	       	 	if(gamer.getPlayer() == Board.X){
	         	   g.drawString("AI (Black) can't move.  | Black : " + black + "  White : " + white + " | Click to continue.", fontX, fontY);
	    	 	}
	    	 	else{
	         	   g.drawString("Player (Black) can't move.  | Black : " + black + "  White : " + white + " | Click to continue.", fontX, fontY);
	    	 	}
	    	 	cprint = 0;	   
       	    }              	 
       	    else if(cprint == 2){
       	    	if(gamer.getPlayer() == Board.O){
		           	 	g.drawString("AI (White) can't move.  | Black : " + black + "  White : " + white + " | Click to continue.", fontX, fontY);
       	    	}
		      	else{
		           	  	g.drawString("Player (White) can't move.  | Black : " + black + "  White : " + white + " | Click to continue.", fontX, fontY);
		      	}
       	    	cprint = 0;	         	    		
       	    } 
        }
        @Override
        public Dimension getPreferredSize() {            	
        	  return new Dimension(481, 505);   
        }               
     };
     
     // Adding action listener to panel
     panel.addMouseListener(this);

     // end screen has the results and 2 buttons, newGame and exitGame
     endScreen = new JPanel() {
    	 @Override
         protected void paintComponent(Graphics g) {
             super.paintComponent(g);
             
             // Setting window background color
             g.setColor(new Color(0,130,236));
             g.fillRect(  0,   0, 1000, 1000);
             
             // Adding starting panel's Image 
             try {                    
                 g.drawImage(ImageIO.read(new File("images/panel.png")),  0,  0,  null);
             } catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
             
             // Updates score values for each color
             black = board.getXScore();
             white = board.getOScore();
             
             // Button locations and size
             newGame.setBounds(100, 150, 100, 45);
             exitGame.setBounds(300, 150, 100, 45);
             
             // Setting text color and font
             g.setColor(new Color(30,30,130));
             g.setFont(new Font ("Courier", Font.BOLD, 15));
             
             // Prints result messages
             if(black > white && gamer.getPlayer() == Board.X) 
             {
             	g.drawString("A.I. won, better luck next time hooman!" , 65, 30);
             	g.drawString(">w<" , 230, 45);
             	g.setFont(new Font ("Courier", Font.BOLD, 15));
             	g.setColor(Color.WHITE);
             	g.drawString("Score: ", 220, 65);
             	g.drawString("AI-kun - "+ black+ " disks", 175, 85);
             	g.drawString("Player - " + white + " disks", 175, 100);
             }
             else if(black < white && gamer.getPlayer() == Board.O) 
             {
             	g.drawString("A.I. won, better luck next time hooman!" , 65, 30);
             	g.drawString(">w<" , 230, 45);
             	g.setFont(new Font ("Courier", Font.BOLD, 15));
             	g.setColor(Color.WHITE);
             	g.drawString("Score: ", 220, 65);
             	g.drawString("AI-kun - "+ white+ " disks", 175, 85);
             	g.drawString("Player - " + black + " disks", 175, 100);
             }
             else if(black > white && gamer.getPlayer() == Board.O) 
             {
             	g.drawString("Damn hooman you were smart enough to beat our A.I.!" , 15, 30);
             	g.drawString("OwO" , 230, 45);
             	g.setFont(new Font ("Courier", Font.BOLD, 15));
             	g.setColor(Color.WHITE);
             	g.drawString("Score: ", 220, 65);
             	g.drawString("AI-kun - "+ white+ " disks", 175, 85);
             	g.drawString("Player - " + black + " disks", 175, 100);
             }
             else if(black < white && gamer.getPlayer() == Board.X)
             {
             	g.drawString("Damn hooman you were smart enough to beat our A.I.!" , 15, 30);
             	g.drawString("OwO" , 230, 45);
             	g.setFont(new Font ("Courier", Font.BOLD, 15));
             	g.setColor(Color.WHITE);
             	g.drawString("Score: ", 220, 65);
             	g.drawString("AI-kun - "+ black+ " disks", 175, 85);
             	g.drawString("Player - " + white + " disks", 175, 100);
             }
             else {
             	g.drawString("Uh oh seems like your forces are equal.. IT'S A TIE!" , 10, 30);
             	g.drawString("^w^" , 230, 45);
             	g.setFont(new Font ("Courier", Font.BOLD, 15));
             	g.setColor(Color.WHITE);
             	g.drawString("Score: ", 220, 65);
             	g.drawString("AI-kun - "+ white+ " disks", 175, 85);
             	g.drawString("Player - " + black + " disks", 175, 100);
             }
             endScreen.add(newGame);
             endScreen.add(exitGame);
         }
       
         @Override 										// No more dimensions needed
         public Dimension getPreferredSize() {            	
         	  return new Dimension(481, 505);   
         }         
     };        
     
        endScreen.addMouseListener(this);
        frame.add(startingScreen);
        frame.pack();
        frame.setVisible(true);
    }

	@Override
	public void mousePressed(MouseEvent arg0) {
		int x, y, i = 0, j = 0;
		// Get coordinates of user's next move by getting mouse position and dividing with cell dimensions
		x = arg0.getX();
		y = arg0.getY();
	    j = x/60;		
	    i = y/60;
	    
	    // Update frame
	    frame.repaint();
	    
	    // Transition to end screen if no-one can play
	    if(!(board.canPlay(gamer.getPlayer()) || board.canPlay(-gamer.getPlayer())))
	    {
        	frame.remove(panel);
        	frame.add(endScreen);
        	frame.pack();
        	frame.repaint();
        }
	    
	    // User plays first, parameters change accordingly
	    if(turn == 1)
	    {
			switch (board.getlastColorPlayed())
			{
                //If white played last, then black plays now
				case Board.O:
                    if(!board.canPlay(Board.X))
                    {
                    	board.setlastColorPlayed(Board.X);
                    	break;
                    }
                    if (board.isValidMove(i, j, Board.X))
                    {
						Move XMove = new Move(i, j);
						board.makeMove(XMove.getRow(), XMove.getCol(), Board.X);
                    }
                    panel.repaint();
					break;
					
                //If black played last, then white plays now
				case Board.X:
                    moves = board.possibleMoves(Board.O);
                    if(!board.canPlay(Board.O))
                    {
                    	board.setlastColorPlayed(Board.O);
                    	break;
                    }
                    AI_Plays(true); 
                    panel.repaint();
					break;
				default:
					break;
			}
		}
		else if (turn == 2){
			switch (board.getlastColorPlayed())
			{
                //If black played last, then white plays now
				case Board.X:
                    if(!board.canPlay(Board.O))
                    {
                    	board.setlastColorPlayed(Board.O);
                    	break;
                    }
                    if (board.isValidMove(i, j, Board.O))
                    {
						Move OMove = new Move(i, j);
						board.makeMove(OMove.getRow(), OMove.getCol(), Board.O);
                    }
                    panel.repaint();
					break;
					
				//If white played last, then black plays now
				case Board.O:
                    if(!board.canPlay(Board.X))
                    {
                    	board.setlastColorPlayed(Board.X);
                    	break;
                    }
                    AI_Plays(true); 
                    panel.repaint();
					break;
				default:
					break;
			}
		} 
	}
	
	// AI_Plays method finds the best move possible with MinMax and executes it via makeMove
	public void AI_Plays(boolean aiturn) {
		if(aiturn) {
			   AI = gamer.MiniMax(board);
			   board.makeMove(AI.getRow(), AI.getCol(), gamer.getPlayer()); 
		}
	}
	
	// Available method adds value 2 to all available move cells by traversing the ArrayList that the possibleMoves method returns
	ArrayList<int[]> moves;
	public int[][] Available (int currentPlayer, Board myboard) {

		int [][] dataCopy = myboard.getGameBoard();
		moves = myboard.possibleMoves(currentPlayer);		
		int[] temp;
		for(int x=0; x < moves.size(); x++)
		{
			temp = moves.get(x);
			dataCopy[temp[0]][temp[1]] = 2;		
		}
		return dataCopy;
	}
	
	// RemoveTwos method removes the 2 values form the table cells
	public int[][] RemoveTwos (int[][] table){
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){	
				if (table[i][j]==2){
					table[i][j]=0;
				}
			}
		}
		return table;
	}

    @Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}	   	   	
}
 