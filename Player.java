package tester;

/**
 *The player class keeps track of the number of stones in the 
 * mancala and how many times the player used undo.
 *@author Praneet Singh, Dev Patel, Ira Sharma 

 */
public class Player
{
	private int stonesNumber;
	private int undoNumber;

	/**
	 *	Create a new Player
	 *	Start with 0 stones
	 * Start with 0 undo 
	 */
	public Player()
	{
		setStones(0);
		undoNumber = 0;
	}

	/**
	 *	Increment the number of stones in this player's mancala by 1	
	 */
	public void addStone()
	{
		stonesNumber++;
	}

	/**
	 *	Get the number of stones in this player's mancala	
	 *	@return The number of stones
	 */
	public int getStones()
	{
		return stonesNumber;
	}

	/**
	 *	Get how many times this player has used the Undo button
	 *	@return The number of Undos
	 */
	public int getUndoNumber()
	{
		return undoNumber;
	}

	/**
	 *	Increment the number of times this player has used the Undo 
	 *	button by 1	
	 */
	public void updateUndoNumber()
	{
		undoNumber++;
	}

	/**
	 *	Set the number of stones in this player's mancala
	 *	@param n The number of stones
	 */
	public void setStones(int n)
	{
		stonesNumber = n;
	}
}