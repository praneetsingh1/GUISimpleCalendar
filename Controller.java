package tester;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;

/**
 * Take the Model and View as parameters and sets up all the listeners
 *@author Praneet Singh, Dev Patel, Ira Sharma

 */
public class Controller
{
	@SuppressWarnings("unused")
	private int counter;
	private Model model;
	private ViewStrategy view;

	/**
	 *	Create the controller with model and view
	 *	@param model model to manipulate
	 *	@param view view to display the data on
	 */
	public Controller(Model theModel, ViewStrategy theView)
	{
		model = theModel;
		view = theView;
		counter = 0;
		

		model.addChangeListener((ChangeListener) view);
		view.addStartListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setThePits(view.getStoneNumber());
				view.disposeSelection();
				view.enableButtons();
				
			}
		});
		
		view.addMancalaActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String mancalaName = ((JButton) e.getSource()).getName();
				if (mancalaName.equals("playerA")) {
					view.getMancala().get(0).setText(model.getStonesP2() + ""); 
				} else {
					view.getMancala().get(1).setText(model.getStonesP1() + "");
				}
			}

		});

		view.addPitActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String pitName = ((JButton) e.getSource()).getName(); //pit label


				if (model.checkGameOver()) {
					if (model.getStonesP1() > model.getStonesP2())
						JOptionPane.showMessageDialog(view.getFrame(), "Player A Wins.");
					else if (model.getStonesP2() > model.getStonesP1())
						JOptionPane.showMessageDialog(view.getFrame(), "Player B Wins.");
					else
						JOptionPane.showMessageDialog(view.getFrame(), "Draw Game.");
				} else if (!model.checkValid(pitName)) {
					JOptionPane.showMessageDialog(view.getFrame(), "Invalid Move.");
				} else {
					model.updateBoard(pitName);
					model.checkForWins();

					for (int i = 0; i < view.getAllButtons().size(); i++) {
						view.getAllButtons().get(i).setText(setStones(model.getPitStones(i)));
					}

					view.getMancala().get(0).setText(setStones(model.getStonesP2()));
					view.getMancala().get(1).setText(setStones(model.getStonesP1()));
					if (model.getCounter() % 2 == 0) {
						view.player2Turn(false);
						view.player1Turn(true);
					} else {
						view.player1Turn(false);
						view.player2Turn(true);
					}
					if (model.checkGameOver()) {
						if (model.getStonesP1() > model.getStonesP2())
							JOptionPane.showMessageDialog(view.getFrame(), "Player 1 Wins.");
						else if (model.getStonesP2() > model.getStonesP1())
							JOptionPane.showMessageDialog(view.getFrame(), "Player 2 Wins.");
						else
							JOptionPane.showMessageDialog(view.getFrame(), "Draw Game.");
					}
				}
			}
		});
		view.addUndoActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(model.getCounter() % 2 == 1 && model.getP1Undo() >= 3)
				{
					JOptionPane.showMessageDialog(view.getFrame(), "Maximum Undo limit reached for Player A.");
					return;
				}
				else if(model.getCounter() % 2 == 0 && model.getP2Undo() >= 3)
				{
					JOptionPane.showMessageDialog(view.getFrame(), "Maximum Undo limit reached for Player B.");
					return;
				}
				
				model.restoreState();
				for (int i = 0; i < view.getAllButtons().size(); i++) {
					view.getAllButtons().get(i).setText(setStones(model.getPitStones(i)));
				}

				view.getMancala().get(0).setText(setStones(model.getStonesP2()));
				view.getMancala().get(1).setText(setStones(model.getStonesP1()));
				if (model.getCounter() % 2 == 0) {
					view.player2Turn(false);
					view.player1Turn(true);
				} else {
					view.player1Turn(false);
					view.player2Turn(true);
				}
				if (model.checkGameOver()) {
					if (model.getStonesP1() > model.getStonesP2())
						JOptionPane.showMessageDialog(view.getFrame(), "Player 1 Wins.");
					else if (model.getStonesP2() > model.getStonesP1())
						JOptionPane.showMessageDialog(view.getFrame(), "Player 2 Wins.");
					else
						JOptionPane.showMessageDialog(view.getFrame(), "Draw Game.");
				}

				if(model.getCounter() % 2 == 0)
					model.addP1Undo();
				else
					model.addP2Undo();
			}

		});

		view.addQuitActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	/**
	 *  Converts the number of stones to 'o' representation)
	 *	@param number of stones
	 *	@return string representing the stones
	 */
	private String setStones(int number)
	{
		String stones = "";
		for(int i =0; i < number; i++){
			stones += "o";
		}
		return stones;
	}
}