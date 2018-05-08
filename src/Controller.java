

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Controller class for the SimpleCalendar
 * @author Praneet Singh
 *
 */
public class Controller {
	//has all the action listeners
	private MyCalendar cal;
	private View view;
	@SuppressWarnings("unused")
	private Date currentDate;

	public Controller(MyCalendar theCalender, View theView) {
		cal = theCalender;
		view = theView;
		loadEvents();
		DateFormat day = new SimpleDateFormat("d");
		String dayS = day.format(cal.getCalendar().getTime());
		view.markSelected(dayS);
		DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
		String today = df.format(cal.getCalendar().getTime());
		
		
		view.setCurrentMonth(today);
		view.addQuitListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveEvents();
			}
		});
		int firstDayOFMonth = cal.getGregCal().get(Calendar.DAY_OF_WEEK)-1;
		int counter = 1;

		for(int i = firstDayOFMonth; i < cal.getLastDayOfMonth()+firstDayOFMonth; i++) {
			view.getDayButtons().get(i).setText(""+(counter++));
		}
		view.addDayListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				view.removeAllBorder();
				JButton bu = (JButton) e.getSource();
				if(bu.getText() == "") {
					return;
				}
				int date = Integer.parseInt(bu.getText());
				cal.updateCalendar(date);
				DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
				
				DateFormat weekDay = new SimpleDateFormat("EEEE:");
				DateFormat day = new SimpleDateFormat("d");
				String dayS = day.format(cal.getCalendar().getTime());
				String today = df.format(cal.getCalendar().getTime());
				String week = weekDay.format(cal.getCalendar().getTime());
				Date d;
				try {
					d = df.parse(today);
					view.setCurrentMonth(today);
					view.setDayView(cal.getCurrentEvent(d));
					view.setCurrentDate(week);
					view.markSelected(dayS);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}


			}
		});
		view.addNextListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if(cal.getCalendar().get(Calendar.DAY_OF_MONTH) == cal.getLastDayOfMonth()) {
						view.removeAllBorder();
						cal.updateMonth();
						view.resetDayButtons();
						int firstDayOFMonth = cal.getGregCal().get(Calendar.DAY_OF_WEEK)-1;
						int counter = 1;

						for(int i = firstDayOFMonth; i < cal.getLastDayOfMonth()+firstDayOFMonth; i++) {
							view.getDayButtons().get(i).setText(""+(counter++));
						}
					}
					currentDate = cal.getNext(cal.getCalendar());
					view.removeAllBorder();
					DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					DateFormat weekDay = new SimpleDateFormat("EEEE:");
					DateFormat day = new SimpleDateFormat("d");
					String dayS = day.format(cal.getCalendar().getTime());
					String today = df.format(cal.getCalendar().getTime());
					String week = weekDay.format(cal.getCalendar().getTime());
					Date d = df.parse(today);
					view.setCurrentMonth(today);
					view.setDayView(cal.getCurrentEvent(d));
					view.setCurrentDate(week);
					view.markSelected(dayS);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		view.addPrevListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(cal.getCalendar().get(Calendar.DAY_OF_MONTH) == 1) {
						cal.minusMonth();
						view.resetDayButtons();
						int firstDayOFMonth = cal.getGregCal().get(Calendar.DAY_OF_WEEK)-1;
						System.out.println(firstDayOFMonth);
						int counter = 1;

						for(int i = firstDayOFMonth; i < cal.getLastDayOfMonth()+firstDayOFMonth; i++) {
							view.getDayButtons().get(i).setText(""+(counter++));
						}
					}
					view.removeAllBorder();
					currentDate = cal.getPrev(cal.getCalendar());
					DateFormat df = new SimpleDateFormat("MMMM d, yyyy");
					DateFormat weekDay = new SimpleDateFormat("EEEE:");
					DateFormat day = new SimpleDateFormat("d");
					String dayS = day.format(cal.getCalendar().getTime());
					String today = df.format(cal.getCalendar().getTime());
					String week = weekDay.format(cal.getCalendar().getTime());
					Date d = df.parse(today);
					view.setCurrentMonth(today);
					view.setDayView(cal.getCurrentEvent(d));
					view.setCurrentDate(week);
					view.markSelected(dayS);

				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		view.addCreateListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFrame inpu = new JFrame("Enter Event Details");
				JButton okay = new JButton("Save");
				inpu.setLayout(new GridLayout(5,2));
				JLabel enterName = new JLabel("Enter Event Name:");
				JTextField eN = new JTextField();
				JLabel enterStart = new JLabel("Enter Start Time(hh:mm):");
				JTextField sT = new JTextField();
				JLabel enterEnd = new JLabel("Enter Ending time(hh:mm):");
				JTextField eT = new JTextField();
				JLabel enterDate = new JLabel("Current Date:");
				JTextField dT = new JTextField();
				DateFormat day = new SimpleDateFormat("MM/dd/yyyy");
				String dayS = day.format(cal.getCalendar().getTime());
				dT.setText(dayS);
				JLabel conflict = new JLabel("There's a time conflict. Try again");
				inpu.setSize(1200, 150);
				inpu.add(enterName);
				inpu.add(eN);
				inpu.add(enterStart);
				inpu.add(sT);
				inpu.add(enterEnd);
				inpu.add(eT);
				inpu.add(enterDate);
				inpu.add(dT);
				inpu.add(okay);
				inpu.add(conflict);
				conflict.setVisible(false);
				inpu.setVisible(true);
				okay.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						Event input = new Event(eN.getText(), sT.getText(), eT.getText(), dT.getText());
						try {
							if(!cal.timeConflict(input)) {
								cal.add(input);
								inpu.dispose();
							}else {
								conflict.setVisible(true);
							}
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				});
			}
		});

	}
	private void saveEvents() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("Events.txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(cal.arrayOfAllEvents());

			objectOutputStream.close();
			fileOutputStream.close();
			System.exit(0);

		} catch (NotSerializableException exception) {
			// Output expected NotSerializeableExceptions.
			exception.printStackTrace();
		} catch (IOException exception) {
			// Output unexpected IOException.
			exception.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void loadEvents() {
		ArrayList<Event> inputEvents = new ArrayList<>();
		try {
			FileInputStream inputFile = new FileInputStream("Events.txt");
			ObjectInputStream inputObject = new ObjectInputStream(inputFile);
			inputEvents = (ArrayList<Event>) inputObject.readObject();
			for(Event inp : inputEvents) {	//add the events to the dataStructure
				cal.add(inp);
			}

			inputObject.close();
			inputFile.close();

		} catch (NotSerializableException e) {
			// Output expected NotSerializeableExceptions.
			e.printStackTrace();
		} catch (IOException | ClassNotFoundException e) {
			// Output unexpected IOExceptions and ClassNotFoundExceptions.
			System.out.println("Event.txt is not created yet.");
		}catch(ParseException e) {
			e.printStackTrace();
		}
	}
}
