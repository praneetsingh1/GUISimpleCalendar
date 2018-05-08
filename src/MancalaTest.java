package tester;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The tester class gives the player option of two different views, sets up the model, view and controller.
 *@author Praneet Singh, Dev Patel, Ira Sharma

 */
public class MancalaTest
{
	static JFrame mainFrame;
	static JLabel welcomeMsg, chooseView;
	static JButton basic, colorful;
	
	public static void main(String[] args)
	{
		//create the model
		Model model = new Model();
		
		//Let the user decide the view class
		mainFrame = new JFrame();
		mainFrame.setSize(500, 275);
		welcomeMsg = new JLabel("  Welcome to Mancala!");
		chooseView = new JLabel("Choose your view:");
		basic = new JButton("Basic");
		colorful = new JButton("ColorFul");
		welcomeMsg.setFont(new Font("Ariel", Font.BOLD, 40));
		chooseView.setFont(new Font("Ariel", Font.BOLD, 20));
		mainFrame.add(welcomeMsg, BorderLayout.NORTH);
		mainFrame.add(chooseView);
		mainFrame.add(basic);
		mainFrame.add(colorful);
		mainFrame.setVisible(true);
		chooseView.setBounds(50, 25, 300, 100);
		basic.setBounds(150, 100, 100, 100);
		colorful.setBounds(250, 100, 100, 100);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);

		//based on user input start either view 1 or view 2 with a new controller.
		basic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				Controller controller = new Controller(model, new View());
				mainFrame.dispose();
			}
		});
		colorful.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unused")
				Controller controller = new Controller(model, new View2());
				mainFrame.dispose();
			}
		});
	}
}