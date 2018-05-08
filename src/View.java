

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * View class for the SimpleCalendar
 * @author Praneet Singh
 *
 */
public class View implements ChangeListener{
	private JFrame mainFrame;
	private JButton prevButton, nextButton, create, quit;
	private JPanel monthView, buttonsPnl, dayView; 
	private JLabel sun, mon, tue, wed, thur, fri, sat, currentDate, currentMonth;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private JTextArea dayEvents;
	//create the view
	public View() {
		mainFrame = new JFrame("My Calender");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setSize(900,350);
		prevButton = new JButton("<");
		nextButton = new JButton(">");
		monthView = new JPanel( new GridLayout(7, 7));
		buttonsPnl = new JPanel(new GridLayout(1, 7));
		dayView = new JPanel(new BorderLayout());
		dayEvents = new JTextArea();
		create = new JButton("Create");
		quit = new JButton("Quit");
		sun = new JLabel("Sun");
		mon = new JLabel("Mon");
		tue = new JLabel("Tue");
		wed = new JLabel("Wed");
		thur = new JLabel("Thu");
		fri = new JLabel("Fri");
		sat = new JLabel("Sat");
		currentDate = new JLabel("Events:");
		currentDate.setFont(new Font("Ariel", Font.BOLD, 25));
		currentMonth = new JLabel("");
		currentMonth.setFont(new Font("Ariel", Font.BOLD, 20));
		currentMonth.setSize(100, 200);

		dayView.add(currentDate, BorderLayout.NORTH);
		dayView.add(dayEvents, BorderLayout.CENTER);
		buttonsPnl.add(currentMonth);
		buttonsPnl.add(create);
		buttonsPnl.add(prevButton);
		buttonsPnl.add(nextButton);
		buttonsPnl.add(quit);
		createDayButtons();
		monthView.add(sun);
		monthView.add(mon);
		monthView.add(tue);
		monthView.add(wed);
		monthView.add(thur);
		monthView.add(fri);
		monthView.add(sat);

		for(JButton b :	buttons) {
			monthView.add(b);
		}
		mainFrame.add(buttonsPnl, BorderLayout.NORTH);
		mainFrame.add(dayView, BorderLayout.CENTER);
		mainFrame.add(monthView, BorderLayout.WEST);
		
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dayEvents.setEditable(false);
		for(JButton b :	buttons) {
			b.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		}

	}

	/**
	 * Remove the border on all buttons
	 */
	public void removeAllBorder() {
		for(JButton b : buttons) {
				b.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		}
		monthView.repaint();
	}
	
	/**
	 * Mark a buttons as selected by putting a border around it
	 * @param date date number
	 */
	public void markSelected(String date) {
		for(JButton b : buttons) {
			if(b.getText().equals(date)) {
				b.setBorder(BorderFactory.createLineBorder(Color.blue));
			}
			monthView.repaint();
		}
	}
	
	/**
	 * Set the JLabel for the current month date
	 * @param s date in format
	 */
	public void setCurrentMonth(String s) {
		currentMonth.setText(s);
	}
	
	/**
	 * Create all the buttons for each day in month
	 */
	private void createDayButtons() {
		for(int i = 0; i < 42; i++) {
			buttons.add(new JButton(""));
		}
	}
	
	/**
	 * Set the JLabel current date to current date
	 * @param d formated date in string format
	 */
	public void setCurrentDate(String d) {
		currentDate.setText(d);
	}
	
	/**
	 * Set the day view to show events
	 * @param s 
	 */
	public void setDayView(String s) {
		dayEvents.setText(s);
	}
	
	/**
	 * Reset all buttons to blank
	 */
	public void resetDayButtons() {
		for(JButton bb : buttons) {
			bb.setText("");
		}
	}
	
	/**
	 * get all the day buttons
	 * @return ArrayList of buttons
	 */
	public ArrayList<JButton> getDayButtons(){
		return buttons;
	}
	
	/**
	 * Add a listener to all buttons
	 * @param l ActionListener
	 */
	public void addDayListener(ActionListener l) {
		for(JButton b : buttons) {
			b.addActionListener(l);
		}
	}
	
	/**
	 * Add listener to the create button
	 * @param l ActionListener
	 */
	public void addCreateListener(ActionListener l) {
		create.addActionListener(l);
	}
	
	/**
	 * Add listener to the previous button
	 * @param l ActionListener
	 */
	public void addPrevListener(ActionListener l) {
		prevButton.addActionListener(l);
	}
	
	/**
	 * Add listener to the next button
	 * @param l ActionListener
	 */
	public void addNextListener(ActionListener l) {
		nextButton.addActionListener(l);
	}

	/**
	 * Add listener to the quit button
	 * @param l ActionListener
	 */
	public void addQuitListener(ActionListener l) {
		quit.addActionListener(l);
	}

	/**
	 * Update the view when state changed
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		String s = (String) e.getSource();
		dayEvents.setText(s);
		dayView.repaint();
	}

}
