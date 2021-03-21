
import java.util.ArrayList;

public class Board
{
    // Variables for the Boards values
	public static final int X = 1; 	 	// Black disks
	public static final int O = -1; 	// White disks
	public static final int EMPTY = 0;	// Empty cells
	public int X_disks = 2;				// Black score counter, initialized as 2 because starting board has 2 black disks
	public int O_disks = 2;				// White score counter, initialized as 2 because starting board has 2 white disks
	
    // Immediate move that lead to this board
    private Move lastMove;

    /* Variable containing who played last; whose turn resulted in this board state,
     * even a new Board has lastColorPlayed value; which dictates who will play first
     */
	private int lastColorPlayed;
	
	/* Table that holds values for each board cell, 1 for Black disks, -1 for White disks, 
	 * 0 for EMPTY cells (later on 2 for player available moves)
	 */
	private int [][] gameBoard;
	
	public Board()
	{
		// Initializing values
		lastMove = new Move();
		lastColorPlayed = O; 		// Initialized with O because Black always goes first, it's the law
		gameBoard = new int[8][8];  // 8x8 table for an 8x8 grid board
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{	
				// Creating an EMPTY board
				gameBoard[i][j] = EMPTY;
			}
			// Placing starting board disks
			gameBoard[3][3] = O;
			gameBoard[3][4] = X;
			gameBoard[4][3] = X;
			gameBoard[4][4] = O;
		}
	}
	
	// Copy constructor
	public Board(Board board)
	{
		lastMove = board.lastMove;
		lastColorPlayed = board.lastColorPlayed;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{	
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
		O_disks = board.O_disks;
		X_disks = board.X_disks;
	}

    // Make a move, places 1 or -1 for black and white respectively
	public void makeMove(int i, int j, int player)
	{
		gameBoard[i][j] = player;
		lastMove = new Move(i, j);
		lastColorPlayed = player;
		
		if (player == X) {
			X_disks++; 		// Increases number of black disks by one when black makes a move
		}else {
			O_disks++;		// Increases number of white disks by one when white makes a move
		}
		
		/* Reverses flanked disks to opposite color 
		 * by traversing to each direction starting from placed disk
		 */
		
	    int mi , mj;
	    
	    // Opponent of player currently making a move
        int opponent = ((player == 1) ? -1 : 1);
        
        // reverse up
        mi = i - 1;
        mj = j;

        while(mi>0 && gameBoard[mi][mj] == opponent)
        {
            
            if(gameBoard[mi-1][mj] == player)
            {
            	while(mi>0 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mi++;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }
            
            mi--;
        }

        // reverse down
        mi = i + 1;
        mj = j;

        while(mi<7 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi+1][mj] == player)
        	{
            	while(mi<7 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mi--;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }
            
            mi++;
        }

        // reverse left
        mi = i;
        mj = j - 1;

        while(mj>0 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi][mj-1] == player)
        	{
            	while(mj>0 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mj++;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }

            mj--;
        }

        // reverse right
        mi = i;
        mj = j + 1;
        
        while(mj<7 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi][mj+1] == player)
        	{
            	while(mj<7 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mj--;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }
           
            mj++;
        }

        // reverse up left
        mi = i - 1;
        mj = j - 1;
        
        while(mi>0 && mj>0 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi-1][mj-1] == player)
        	{
            	while(mi>0 && mj>0 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mi++;
            		mj++;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }
            
            mi--;
            mj--;
        }
        
        // reverse up right
        mi = i - 1;
        mj = j + 1;
        
        while(mi>0 && mj<7 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi-1][mj+1] == player)
        	{
            	while(mi>0 && mj<7 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mi++;
            		mj--;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }
            
            mi--;
            mj++;
        }
        
        // reverse down left
        mi = i + 1;
        mj = j - 1;
        
        while(mi<7 && mj>0 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi+1][mj-1] == player)
        	{
            	while(mi<7 && mj>0 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mi--;
            		mj++;
            		if (player == X)
            		{
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }

            mi++;
            mj--;
        }
        
        // reverse down right
        mi = i + 1;
        mj = j + 1;
        
        while(mi<7 && mj<7 && gameBoard[mi][mj] == opponent)
        {

        	if(gameBoard[mi+1][mj+1] == player)
        	{
            	while(mi<7 && mj<7 && gameBoard[mi][mj] == opponent)
            	{
            		gameBoard[mi][mj] = player;
            		mi--;
            		mj--;
            		if (player == X) {
            			X_disks++;
            			O_disks--;
            		}else {
            			O_disks++;
            			X_disks--;
            		}
            	}
            }

            mi++;
            mj++;
        }
        
	}

    //Checks whether a move is valid
	public boolean isValidMove(int i, int j, int player)
	{
		if ((i == -1) || (j == -1) || (i > 7) || (j > 7)) return false; // If move is out of bounds
		if(gameBoard[i][j] != EMPTY) return false; 						// If board cell is not empty
		
		/* Searches for "ally" disk that will trap opponent disks
		 * by traversing to each direction starting from placed disk
		 */
        int row , col , flanked;
        int opponent = ((player == 1) ? -1 : 1);
        // flanked variable counts trapped opponent disks between allied ones for each direction
        // move up
        row = i - 1;
        col = j;
        flanked = 0;
        while(row>0 && gameBoard[row][col] == opponent)
        {
            row--;
            flanked++;
        }
        if(row>=0 && gameBoard[row][col] == player && flanked>0) return true;


        // move down
        row = i + 1;
        col = j;
        flanked = 0;
        while(row<7 && gameBoard[row][col] == opponent)
        {
            row++;
            flanked++;
        }
        if(row<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // move left
        row = i;
        col = j - 1;
        flanked = 0;
        while(col>0 && gameBoard[row][col] == opponent)
        {
            col--;
            flanked++;
        }
        if(col>=0 && gameBoard[row][col] == player && flanked>0) return true;

        // move right
        row = i;
        col = j + 1;
        flanked = 0;
        while(col<7 && gameBoard[row][col] == opponent)
        {
            col++;
            flanked++;
        }
        if(col<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // move up left
        row = i - 1;
        col = j - 1;
        flanked = 0;
        while(row>0 && col>0 && gameBoard[row][col] == opponent)
        {
            row--;
            col--;
            flanked++;
        }
        if(row>=0 && col>=0 && gameBoard[row][col] == player && flanked>0) return true;

        // move up right
        row = i - 1;
        col = j + 1;
        flanked = 0;
        while(row>0 && col<7 && gameBoard[row][col] == opponent)
        {
            row--;
            col++;
            flanked++;
        }
        if(row>=0 && col<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // move down left
        row = i + 1;
        col = j - 1;
        flanked = 0;
        while(row<7 && col>0 && gameBoard[row][col] == opponent)
        {
            row++;
            col--;
            flanked++;
        }
        if(row<=7 && col>=0 && gameBoard[row][col] == player && flanked>0) return true;

        // move down right
        row = i + 1;
        col = j + 1;
        flanked = 0;
        while(row<7 && col<7 && gameBoard[row][col] == opponent)
        {
            row++;
            col++;
            flanked++;
        }
        if(row<=7 && col<=7 && gameBoard[row][col] == player && flanked>0) return true;

        // when all hopes fade away
        return false;
	}


    /* Generates the children of the state,
     * any available move that can be potentially played results to a child
     */
	public ArrayList<Board> getChildren(int color)
	{
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(isValidMove(row, col, color))
				{	
					Board child = new Board(this);
					//child.print();
					child.makeMove(row, col, color);
					//System.out.println("O: " + child.O_disks + " @: " + child.X_disks);
					//System.out.println("(" + row + ", "+ col + ") evaluate: " + child.evaluate(color) + " color: " + color);
					children.add(child);
				}
			}
		}
		return children;
	}

	/*
     * The heuristic we use in MinMax to evaluate the weight of
     * our current state for each color depending on the tree level
     * For weight calculation please refer to the appropriate report chapter
     */
	
	public int evaluate(int color)
    {
        int sum = 0;
        int opp = color == X ? O : X;
        // "Killer move" cases
        if(color == X && O_disks == 0) {
            sum += 1000;
        }
        if(color == O && X_disks == 0) {
            sum += 1000;
        }
                
        for(int row=0; row<8; row++)
        {
            for(int col=0; col<8; col++)
            {            
                if(this.gameBoard[row][col] == color)
                {
                    if((row == 0 || row==7) && (col == 0 || col == 7))
                    {
                        sum += 16;    // State is appointed an additional weight of 16 due to corner location
                    }
                    else if ((row == 0 || row==7) || (col == 0 || col == 7))
                    {
                        sum += 4;    // State is appointed an additional weight of 4 due to edge location
                    }
                    else
                    {
                        sum +=1;    // State is appointed an additional weight of 1 due to insignificant location
                    }
                }
                else if (this.gameBoard[row][col] == opp) {
                    if((row == 0 || row==7) && (col == 0 || col == 7))
                    {
                        sum -= 16;    // State is decreased by a weight of 16 due to corner location of opponent
                    }
                    else if ((row == 0 || row==7) || (col == 0 || col == 7))
                    {
                        sum -= 4;    // State is decreased by a weight of 4 due to edge location of opponent
                    }
                    else
                    {
                        sum -=1;    // State is decreased by a weight of 1 due to insignificant location of opponent
                    }
                }
            }
        }
        return sum;
}

    /*
     * Checks if there are any available moves,
     * if not, then the player can't play and misses their turn
     */
    public boolean canPlay(int player)
    {
    	if (possibleMoves(player).isEmpty())
    	{  
    		return false;
    	}
    	return true;
	}

    /*
     * Finds all available moves via isValidMove, and returns an ArrayList
     * containing the board coordinates of each available move
     */
    public ArrayList<int[]> possibleMoves(int color)
    {
    	ArrayList<int[]> moves = new ArrayList<int[]>();
    	int[] temp;
    	for(int i=0; i<8; i++)
    	{
        	for(int j=0; j<8; j++)
        	{
        		if (isValidMove(i, j, color))
        		{
        			temp = new int[2];
        			temp[0] = i;
        			temp[1] = j;
        			moves.add(temp);
        		}        		
        	}
        }
    	return moves;
    }
    
    /*
     * Method isTerminal merely checks whether if the board is full or not,  
     * other terminating conditions are accounted for elsewhere
     */
    public boolean isTerminal()
    {
        for(int i=0; i<8; i++)
        {
        	for(int j=0; j<8; j++)
        	{
        		if (gameBoard[i][j] == EMPTY)
        		{
        			return false;
        		}
        	}
        }
        return true;
    }
    
	public Move getLastMove()
	{
		return lastMove;
	}
	
	public int getlastColorPlayed()
	{
		return lastColorPlayed;
	}
	
	public int[][] getGameBoard()
	{
		return gameBoard;
	}
	
	public int getOScore()
	{
		return O_disks;
	}
	
	public int getXScore()
	{
		return X_disks;
	}
	
	public void setlastColorPlayed(int lastColorPlayed)
	{
		this.lastColorPlayed = lastColorPlayed;
	}
	
	public void setLastMove(Move lastMove)
	{
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setGameBoard(int[][] gameBoard)
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}
	
}

