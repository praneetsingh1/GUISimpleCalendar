package tester;


import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *Data Structures , Accessors and Mutators for the model data
 *@author Praneet Singh, Dev Patel, Ira Sharma

 */
public class Model
{
	private ArrayList<ChangeListener> listeners;
	private ArrayList<Player> players;
	private int[] pits, backupPit;
	private int backupMancala1, backupMancala2, counter, backupCounter;
	private boolean gameOver;
	

	/**
	 *	Set up the players, pits, counter and the backup variables
	 */
	public Model()
	{
		players = new ArrayList<Player>();
		listeners = new ArrayList<ChangeListener>();
		players.add(new Player());
		players.add(new Player());
		backupMancala1 = 0;
		backupMancala2 = 0;
		pits = new int[12];
		backupPit = new int[12];
		counter = 0;
		backupCounter= 0;
	}

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	/**
	 *	initialize the pits with given number
	 *	@param n number of stones per pit
	 */
	public void setThePits(int n)
	{
		for(int i = 0; i < pits.length; i++)
			pits[i] = n;
		ChangeEvent event = new ChangeEvent(n);
		for(ChangeListener listener: listeners) {
			listener.stateChanged(event);
		}
	}
	
	public int getP1Undo()
	{
		return players.get(0).getUndoNumber();
	}

	/**
	 *	Get number of stones in a specific index
	 *	@param index between 0 and 11
	 *	@return number of stones in that pit
	 */
	public int getPitStones(int index)
	{
		return pits[index];
	}
	
	/**
	 *	Get undo counter for player 2
	 *	@return undo number
	 */
	public int getP2Undo()
	{
		return players.get(1).getUndoNumber();
	}
	/**
	 *	Get the number of stones in Player 1's mancala
	 *	@return number of stones
	 */
	public int getStonesP1()
	{
		return players.get(0).getStones();
	}

	/**
	 *	Get the number of stones in Player 2's mancala
	 *	@return the number of stones
	 */
	public int getStonesP2()
	{
		return players.get(1).getStones();
	}

	/**
	 *	Sets the number of stones in a specific index
	 *	@param index between 0 and 11
	 *	@param numOfStones number to set
	 */
	public void setStones(int index, int numOfStones)
	{
		pits[index] = numOfStones;
	}
	/**
	 *	Save the current state of the board
	 */
	public void saveState()
	{
		System.arraycopy(pits, 0, backupPit, 0, backupPit.length);
		backupMancala1 = getStonesP1();
		backupMancala2 = getStonesP2();
		backupCounter = counter;
	}

	/**
	 *	Load the backup of the board 
	 */
	public void restoreState()
	{
		System.arraycopy(backupPit, 0, pits, 0, pits.length);
		players.get(0).setStones(backupMancala1);
		players.get(1).setStones(backupMancala2);
		counter = backupCounter;
	}

	/**
	 *	Add a stone to Player 1's mancala
	 */
	public void updateP1Mancala()
	{
		players.get(0).addStone();
	}

	/**
	 *	Add a stone to Player 2's mancala
	 */
	public void updateP2Mancala() 
	{
		players.get(1).addStone();
	}

	/**
	 *	Get undo counter for player 1
	 *	@return undo number
	 */
	/**
	 *	Take out all stones from a pit and put the stones
	 *	in the next pits. Add stones only to the current player's own mancala.
	 *	@param pit the pit label
	 */
	public void updateBoard(String pit)
	{
		saveState();

		int pitNumber = Integer.parseInt(pit.substring(1, 2)) - 1;
		if(pit.substring(0, 1).equals("B"))
			pitNumber = pitNumber + 6;

		// determines how many stones to distribute
		int stonesToPlace = pits[pitNumber]; 
		pits[pitNumber] = 0;

		for(int i = pitNumber + 1, j = 0; j < stonesToPlace; i++, j++)
		{
			if(i == 6 && counter % 2 == 0) // Update P1 Mancala
			{
				players.get(0).addStone();
				// if there are more stones to be added, add to next pit
				if(j < stonesToPlace - 1) 
				{
					pits[i % 12]++;
					j++; //skip next pit since stone was already added
				}
				else if(j == stonesToPlace - 1) // if there are no more stones to be added
					counter++;	// Player 1 gets another turn for ending in mancala
			}
			else if(i == 12 && counter % 2 == 1) // Update P2 mancala
			{
				players.get(1).addStone();
				// if there are more stones to be added
				if(j < stonesToPlace - 1)
				{
					pits[i % 12]++;
					j++; //skip next pit since stone was already added
				}
				else if(j == stonesToPlace - 1)
					counter++; // player 2 gets another turn for ending in mancala
			}
			else
				pits[i % 12]++;

			// Begin checking captures
			// if there are no more stones to place and last stone landed in p1's empty pit
			if(j == stonesToPlace - 1 && counter % 2 == 0 && pits[i % 12] == 1) 
			{
				if(i % 12 <= 5) // if index is on player 1's side
				{
					players.get(0).setStones(players.get(0).getStones() + pits[pits.length - (i % 12) - 1]);
					pits[pits.length - (i % 12) - 1] = 0;
				}
			}
			// if there are no more stones to place and last stone landed in p2's empty pit
			else if(j == stonesToPlace - 1 && counter % 2 == 1 && pits[i % 12] == 1) 
			{
				if(i % 12 >= 6) // if index is on player 2's side
				{
					players.get(1).setStones(players.get(1).getStones() + pits[pits.length - (i % 12) - 1]);
					pits[pits.length - (i % 12) - 1] = 0;
				}
			}
		}

		checkForWins();
		counter++; // next player's turn
	}

	/**
	 *	Checks the board state to determine if a winner was found
	 */
	public void checkForWins()
	{
		int zerosFound = 0;
		if(counter % 2 == 0)
		{
			
			for(int i = 0; i < pits.length / 2; i++)
				if(pits[i] == 0)
					zerosFound++;
		}
		else if(counter % 2 == 1)
		{
			for(int i = 6; i < pits.length; i++)
				if(pits[i] == 0)
					zerosFound++;
		}

		if(zerosFound == 6)
			gameOver = true;
	}

	/**
	 *	Get number of turns
	 *	@return the number of turns
	 */
	public int getCounter()
	{
		return counter;
	}

	/**
	 *	Check if someone already won
	 *	@return true only if a winner was found
	 */
	public boolean checkGameOver() 
	{
		return gameOver;
	}  

	/**
	 *	Check if the current player is clicking on right button
	 *	@param pit pit label
	 *	@return true only if move is valid
	 */
	public boolean checkValid(String pit)
	{
		boolean result = false;
		int index = 0;

		if(counter % 2 == 0 && pit.substring(0, 1).equals("A"))
		{
			result = true;
			index = Integer.parseInt(pit.substring(1, pit.length())) - 1;
		}
		else if(counter % 2 == 1 && pit.substring(0, 1).equals("B"))
		{
			result = true;
			index = Integer.parseInt(pit.substring(1, pit.length())) - 1 + 6;
		}

		if(pits[index] == 0)
			result = false;

		return result;
	}

	/**
	 *	Increase player 1 undos
	 */
	public void addP1Undo()
	{
		players.get(0).updateUndoNumber();
	}

	/**
	 *	Increase player 2 undos
	 */
	public void addP2Undo()
	{
		players.get(1).updateUndoNumber();
	}

	/**
	 *	Notify the data structure that a Undo occurred
	 */
	public void playerUndo()
	{
		counter++;
	}


}