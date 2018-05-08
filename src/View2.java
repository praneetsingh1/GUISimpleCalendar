package tester;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



/**
 *Second view of the board
 *@author Praneet Singh, Dev Patel, Ira Sharma
 */
public class View2 implements ViewStrategy, ChangeListener
{
	protected final JFrame frame;
	protected final JPanel mainPanel;
	private ArrayList<JButton> mancala = new ArrayList<JButton>();
	private ArrayList<JLabel> pitLabel = new ArrayList<JLabel>();
	private ArrayList<JButton> allButtons = new ArrayList<JButton>();
	private JButton undoButton, quitButton, start;
	private JLabel player1Turn, player2Turn, mancalaPlayerA, mancalaPlayerB, chooseLabel;
	private JComboBox<Integer> menu;

	/**
	 *Creates an instance of the board
	 */
	public View2()
	{
		//prepare frame
		frame = new JFrame("Mancala");
		frame.setSize(900, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel(null);
		CreateMancala();
		CreateLabels();
		createPits();
		createPitLables();
		menu = new JComboBox<>();
		chooseLabel = new JLabel("Choose the number of stones:");
		chooseLabel.setFont(new Font("Ariel", Font.BOLD, 22));
		menu.addItem(3);
		menu.addItem(4);
		undoButton = new JButton("UNDO");
		quitButton = new JButton("QUIT");
		start = new JButton("Lets Start!");
		start.setBounds(400, 60, 75, 75);
		undoButton.setBounds(375, 400, 75, 75);
		quitButton.setBounds(455, 400, 75, 75);
		chooseLabel.setBounds(100, 0 , 350, 50);
		chooseLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		menu.setBounds(500, 0, 150, 50);
		mainPanel.add(undoButton);
		mainPanel.add(quitButton);
		mainPanel.add(start);
		mainPanel.add(menu);
		mainPanel.add(chooseLabel);
		mainPanel.setBackground(new Color(153, 102, 0));
		player1Turn = new JLabel("Player A's Turn");
		player2Turn = new JLabel("Player B's Turn");
		addPlayerTurns();
		disableButtons();
		mainPanel.setBounds(0, 0, 900, 500);
		frame.add(mainPanel);
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	/**
	 * After user chooses number of stones enable the buttons and mancala
	 */
	public void enableButtons() {
		for(JButton b: allButtons) {
			b.setEnabled(true);
		}
		for(JButton c : mancala) {
			c.setEnabled(true);
		}
	}
	/**
	 * Removes the menu, chooselabel and start button from the view
	 */
	@Override
	public void disposeSelection() {
		menu.setVisible(false);
		chooseLabel.setVisible(false);
		start.setVisible(false);
		mainPanel.remove(menu);
		mainPanel.remove(chooseLabel);
		mainPanel.remove(start);
	}

	/**
	 * Add lables for the players
	 */
	@Override
	public void addPlayerTurns()
	{
		player1Turn.setForeground(Color.ORANGE);
		player2Turn.setForeground(Color.RED);
		player1Turn.setBounds(400, 300, 100, 100);
		player2Turn.setBounds(400, 100, 100, 100);
		mainPanel.add(player1Turn);
		mainPanel.add(player2Turn);
		player2Turn.setVisible(false);
	}

	/**
	 * Sets the player 1 turn label to visible
	 */
	@Override
	public void player1Turn(boolean setter){

		player1Turn.setVisible(setter);
	}

	/**
	 * Sets the player 2 turn label to visible
	 */
	@Override
	public void player2Turn(boolean setter){
		player2Turn.setVisible(setter);
	}

	/**
	 * Creates the pit buttons
	 */
	@Override
	public void createPits() {
		int x = 150;
		for (int i = 0; i < 6; i++) {
			allButtons.add(new JButton());
			allButtons.get(i).setBounds(x, 260, 100, 40);
			x += 100;
			mainPanel.add(allButtons.get(i));

			allButtons.get(i).setName("A" + (i + 1));
		}
		x = 650;
		for (int i = 6; i < 12; i++) {
			allButtons.add(new JButton());
			allButtons.get(i).setBounds(x, 220, 100, 40);
			x -= 100;
			String buttonNumber = "B" + (i + 1 - 6);
			allButtons.get(i).setName(buttonNumber);
			//BJButtons.get(i).setText(buttonNumber);
			mainPanel.add(allButtons.get(i));
		}
	}



	/**
	 * Creates the lables for the pits
	 */
	@Override
	public void createPitLables()
	{
		// Creates labels for Player A's pits
		for (int i = 0; i < 6; i++) {
			final JLabel aLabel = new JLabel("A" + (i + 1));
			aLabel.setForeground(Color.ORANGE);
			aLabel.setFont(new Font("Ariel", Font.ITALIC, 12));
			pitLabel.add(aLabel);

			int xCoord = allButtons.get(i).getX() + allButtons.get(i).getWidth() / 2;
			int yCoord = allButtons.get(i).getY() + allButtons.get(i).getHeight();
			aLabel.setBounds(xCoord, yCoord, allButtons.get(i).getWidth(), allButtons.get(i).getHeight() / 2);
		}

		// Creates labels for Player B's pits
		for (int i = 6; i < 12; i++) {
			final JLabel bLabel = new JLabel("B" + (i + 1 - 6));
			pitLabel.add(bLabel);
			bLabel.setForeground(Color.RED);
			bLabel.setFont(new Font("Ariel", Font.ITALIC, 12));
			int xCoord = allButtons.get(i).getX() + allButtons.get(i).getWidth() / 2;
			int yCoord = allButtons.get(i).getY() - allButtons.get(i).getHeight() / 2;
			bLabel.setBounds(xCoord, yCoord, allButtons.get(i).getWidth(), allButtons.get(i).getHeight() / 2);
		}
		for (JLabel label : pitLabel) {
			mainPanel.add(label);
		}
	}


	//listeners
	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#addStartListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addStartListener(ActionListener l) {
		start.addActionListener(l);
	}

	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#addPitActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addPitActionListener(ActionListener l)
	{
		for(JButton button: allButtons){
			button.addActionListener(l);
		}
	}

	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#addMancalaActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addMancalaActionListener(ActionListener l){
		for(JButton button: mancala){
			button.addActionListener(l);
		}
	}

	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#addQuitActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addQuitActionListener(ActionListener l)
	{
		quitButton.addActionListener(l);
	}

	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#addUndoActionListener(java.awt.event.ActionListener)
	 */
	@Override
	public void addUndoActionListener(ActionListener l)
	{
		undoButton.addActionListener(l);
	}

	//accessors
	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#getMancala()
	 */
	@Override
	public ArrayList<JButton> getMancala()
	{
		return mancala;
	}


	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#getAllButtons()
	 */
	@Override
	public ArrayList<JButton> getAllButtons(){
		return allButtons;
	}

	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#getFrame()
	 */
	@Override
	public JFrame getFrame(){
		return frame;
	}

	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#getStoneNumber()
	 */
	@Override
	public int getStoneNumber() {
		return (int)menu.getSelectedItem();
	}


	//Mutators
	/* (non-Javadoc)
	 * @see mancala.ViewStrategy#setStones(int)
	 */
	@Override
	public String setStones(int number){
		String stones = "";
		for(int i =0; i < number; i++){
			stones += "o";
		}
		return stones;
	}

	/* 
	 * create the mancalas for the players
	 */
	@Override
	public void CreateMancala()
	{

		String player = "A";
		int x = 90;
		for (int i = 0; i < 2; i++) {
			getMancala().add(new JButton());
			getMancala().get(i).setBounds(x, 210, 60, 100);
			x += 660;
			getMancala().get(i).setName("player" + player);
			mainPanel.add(getMancala().get(i));
			player = "B";
		}
	}

	/* 
	 * Creates the labels for Player 1 and Player 2.
	 */
	@Override
	public void CreateLabels()
	{
		mancalaPlayerA = new JLabel("Player A");
		mancalaPlayerB = new JLabel("Player B");
		mancalaPlayerA.setForeground(Color.ORANGE);
		mancalaPlayerB.setForeground(Color.RED);
		mainPanel.add(mancalaPlayerB);
		mainPanel.add(mancalaPlayerA);
		mancalaPlayerA.setBounds(750, 280, 100, 100);
		mancalaPlayerB.setBounds(90, 280, 100, 100);
	}
	
	/**
	 * Disable the buttons upon start
	 */
	private void disableButtons() {
		for(JButton b: allButtons) {
			b.setEnabled(false);
		}
		for(JButton c : mancala) {
			c.setEnabled(false);
		}
	}

	/**
	 * Updates the view with the correct number of stones
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		
		for (int i = 0; i < 12; i++)
		{
			int numOfStones = (int)e.getSource();
			String buttonNumber = setStones(numOfStones);
			allButtons.get(i).setText(buttonNumber);
		}
	}
}