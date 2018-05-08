package tester;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *View interface
 *@author Praneet Singh, Dev Patel, Ira Sharma
 */
public interface ViewStrategy {

	/**
	 * after user makes selection for number of stones
	 * hide and remove the menu, label and start button
	 */
	void disposeSelection();

	/**
	 * Adds the player turn label onto the frame
	 */
	void addPlayerTurns();

	/**
	 * Displays player1 turn onto the board
	 * @param setter boolean to turn off/on the label
	 */
	void player1Turn(boolean setter);

	/**
	 * Displays player2 turn onto the board
	 * @param setter boolean to turn off/on the label
	 */
	void player2Turn(boolean setter);

	/**
	 * Create the pits
	 */
	void createPits();

	/**
	 * Create the lables for the pits
	 */
	void createPitLables();

	//listeners
	/**
	 * Adds an action listener to the start button
	 * @param l the action listener to be added
	 */
	void addStartListener(ActionListener l);

	/**
	 * Adds an action listener to the pits
	 * @param l the action listener to be added
	 */
	void addPitActionListener(ActionListener l);

	/**
	 * Adds an action listener to the mancala
	 * @param l the action listener to be added
	 */
	void addMancalaActionListener(ActionListener l);

	/**
	 * Adds an action listener to the quit button
	 * @param l the action listener to be added
	 */
	void addQuitActionListener(ActionListener l);

	/**
	 * Adds an action listener to the undo button
	 * @param l the action listener to be added
	 */
	void addUndoActionListener(ActionListener l);

	//accessors
	/**
	 * returns an arraylist that holds both playerA and playerB's mancala
	 * @return an arraylist that holds both playerA and playerB's mancala
	 */
	ArrayList<JButton> getMancala();

	/**
	 * gets the arraylist that holds playerA and playerB's pits
	 * @return the arraylist that holds playerA and playerB's pits
	 */
	ArrayList<JButton> getAllButtons();

	/**
	 * returns the frame
	 * @return the frame
	 */
	JFrame getFrame();

	/**
	 * get the stone selection from the user
	 * @return selected number of stones
	 */
	int getStoneNumber();

	//Mutators
	/**
	 * returns the number of stones as string
	 * @param number the number of stone string
	 * @return a number of asterisks
	 */
	String setStones(int number);
 

	/**
	 * create player mancalas
	 */
	void CreateMancala();

	/**
	 * creates lables for mancala
	 */
	void CreateLabels();
	
	/**
	 * After user chooses number of stones enable the buttons and mancala
	 */
	void enableButtons();

}