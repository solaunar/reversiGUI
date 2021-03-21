
import java.util.ArrayList;
import java.util.Random;

public class GamePlayer
{
    //Variable that holds the maximum depth the MiniMax algorithm will reach for this game
	private int maxDepth;
    //Variable that holds which letter this player controls, initialized with O/white
	private int player = Board.O;
	
	private int a = Integer.MIN_VALUE; // alpha value used in a-b pruning
	private int b = Integer.MAX_VALUE; // beta value used in a-b pruning			
	
	// GamePlayer default constructor
	public GamePlayer()
	{
		maxDepth = 3; //maxDepth initialized at depth 3, "Easy"
	}
	
	// GamePlayer Constructor with depth and player a attributes
	public GamePlayer(int maxDepth, int player)  
	{
		this.maxDepth = maxDepth;
		this.player = player;
	}
	
	//Initiates the MiniMax algorithm
	public Move MiniMax(Board board)
	{
		//If Black plays then it wants to MAXimize the heuristic's value
		return max(new Board(board), 0, a, b, player);
	}

	// The max and min functions are called interchangingly, one after another until a max depth is reached
	public Move max(Board board, int depth, int a, int b, int player)
	{
	    Random r = new Random();

	    /* If MAX is called on a state that is terminal or after a maximum depth is reached or player can't make a move,
	     * then a heuristic is calculated on the state and the move gets returned.
	     */
	    if((board.isTerminal()) || (!board.canPlay(player))|| (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate(board.getlastColorPlayed()));
			return lastMove;
		}
	    //The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(player));
		Move maxMove = new Move(Integer.MIN_VALUE);
		for (Board child : children)
		{
		    //And for each child min is called, on a lower depth
			Move move = min(child, depth + 1, a, b, board.getlastColorPlayed());
		    //The child-move with the greatest value is selected and returned by max
			if(move.getValue() >= maxMove.getValue())
			{
				if ((move.getValue() == maxMove.getValue()))
		        {
					//If the heuristic has the same value then we randomly choose one of the two moves
					if (r.nextInt(2) == 0)
					{
						maxMove.setRow(child.getLastMove().getRow());
		                maxMove.setCol(child.getLastMove().getCol());
		                maxMove.setValue(move.getValue());
		            }
		        }
				else
		        {	
					// Otherwise if child-move value is greater than heuristic value, update maxMove
					// a-b pruning in case beta is lesser than or equal to alpha
		            if(move.getValue() > a) a = move.getValue();
		            if(b <= a) break; 
		            maxMove.setRow(child.getLastMove().getRow());
		            maxMove.setCol(child.getLastMove().getCol());
		            maxMove.setValue(move.getValue());
		        }
			}
		}
		return maxMove;
	}

		
	public Move min(Board board, int depth, int a, int b, int player)
	{
		Random r = new Random();
			
		/* If MIN is called on a state that is terminal or after a maximum depth is reached or player can't make a move,
		* then a heuristic is calculated on the state and the move gets returned.
		*/
		if((board.isTerminal()) || (!board.canPlay(player)) || (depth == maxDepth))
		{
			Move lastMove = new Move(board.getLastMove().getRow(), board.getLastMove().getCol(), board.evaluate(board.getlastColorPlayed()));
			return lastMove;
		}
		//The children-moves of the state are calculated
		ArrayList<Board> children = new ArrayList<Board>(board.getChildren(player));
		Move minMove = new Move(Integer.MAX_VALUE);
		for (Board child : children)
		{	
			//And for each child max is called, on a lower depth
			Move move = max(child, depth + 1, a, b, board.getlastColorPlayed());
			//The child-move with the smallest value is selected and returned by min
			if(move.getValue() <= minMove.getValue())
			{
				if ((move.getValue() == minMove.getValue()))
				{	
					//If the heuristic has the same value then we randomly choose one of the two moves
					if (r.nextInt(2) == 0)
	                {                    	
						minMove.setRow(child.getLastMove().getRow());
	                    minMove.setCol(child.getLastMove().getCol());
	                    minMove.setValue(move.getValue());
	                }
				}
	            else
	            {	
	            	// Otherwise if child-move value is lesser than heuristic value, update minMove
					// a-b pruning in case beta is lesser than or equal to alpha
	            	if(move.getValue() < b) b = move.getValue();
	            	if(b <= a) break;
	                minMove.setRow(child.getLastMove().getRow());
	                minMove.setCol(child.getLastMove().getCol());
	                minMove.setValue(move.getValue());
	            }
	        }
	    }
	    return minMove;
	}
	
	public int getPlayer()
	{
		return player;
	}
	
	public void setPlayer(int player)
	{
		this.player = player;
	}
	
	public int getMaxDepth()
	{
		return maxDepth;
	}
	
	public void setMaxDepth(int depth)
	{
		this.maxDepth = depth;
	}
}
