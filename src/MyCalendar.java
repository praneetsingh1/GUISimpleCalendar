

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

enum DAYS
{
	Su, Mo, Tu, We, Th, Fr, Sa;
}
/**
 * Model for the SimpleCalendar
 * @author Praneet Singh
 *
 */
public class MyCalendar {
	Calendar c = GregorianCalendar.getInstance();
	private ArrayList<ChangeListener> listeners = new ArrayList<ChangeListener>();
	GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
	DAYS[] arrayOfDays = DAYS.values();
	private TreeMap<Date, ArrayList<Event>> events = new TreeMap<>();

	/**
	 * Get the last day of current month
	 * @return day number
	 */
	public int getLastDayOfMonth() {
		return temp.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * get the gregorian calendar with day 1 for each month
	 * @return calendar
	 */
	public GregorianCalendar getGregCal() {
		return temp;
	}
	
	/**
	 * Update calendar by the current date
	 * @param date 
	 */
	public void updateCalendar(int date) {
		c.set(Calendar.DAY_OF_MONTH, date);

	}
	
	/**
	 * Subtract month from calendar
	 */
	public void minusMonth() {
		temp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH)-1, 1);
	}
	
	/**
	 * Increment month of the calendar
	 */
	public void updateMonth() {
		temp.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, 1);
	}
	
	/**
	 * Get calendar
	 * @return calendar
	 */
	public Calendar getCalendar() {
		return c;
	}
	
	/**
	 * get the array of day names
	 * @return Days array
	 */
	public DAYS[] arrayOFDays() {
		return arrayOfDays;
	}
	
	/**
	 * Add to the change listener array
	 * @param listener
	 */
	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}
	/**
	 * gets the next day event
	 * @param c
	 * @throws ParseException 
	 */
	public Date getNext(Calendar c) throws ParseException { //return the next day
		c.add(Calendar.DATE, 1);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String today = df.format(c.getTime());
		Date d = df.parse(today);
		return d;
	}

	/**
	 * Gets the previous day event
	 * @param c
	 * @throws ParseException 
	 */
	public Date getPrev(Calendar c) throws ParseException { //return the next day
		c.add(Calendar.DATE, -1);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String today = df.format(c.getTime());
		Date d = df.parse(today);
		return d;
	}
	/**
	 * Adds an event to the treeMap
	 * @param e
	 * @throws ParseException 
	 */
	public void add(Event e) throws ParseException {
		DateFormat dap = new SimpleDateFormat("MM/dd/yyyy");
		Date dat = dap.parse(e.getEventDate());
		String updateEvent;
		ArrayList<Event> even = events.get(dat);
		if(events.containsKey(dat)) {
			even.add(e);
			updateEvent = getCurrentEvent(dat);
			notify(updateEvent);
		}else {
			even = new ArrayList<>();
			even.add(e);
			DateFormat da = new SimpleDateFormat("MM/dd/yyyy");
			Date d = da.parse(e.getEventDate());
			events.put(d, even);
			updateEvent = getCurrentEvent(dat);
			notify(updateEvent);
		}
		Collections.sort(even, sortTime());
	}

	/**
	 * Notify all the views in the changeListener array
	 * @param eventUpdate
	 */
	private void notify(String eventUpdate) {
		ChangeEvent event = new ChangeEvent(eventUpdate);
		for(ChangeListener listener: listeners) {
			listener.stateChanged(event);
		}
	}
	
	/**
	 * check if there is a time conflict	
	 * @param e
	 * @return
	 * @throws ParseException
	 */
	public boolean timeConflict(Event e) throws ParseException {
		DateFormat df = new SimpleDateFormat("K:mm");
		Date startTime = df.parse(e.getStartingTime());
		Date endTime = df.parse(e.getEndingTime());	
		DateFormat da = new SimpleDateFormat("MM/dd/yyyy");
		Date d = da.parse(e.getEventDate());
		if(events.get(d) != null) {
			for(Event p : events.get(d)) {
				Date tempStart = df.parse(p.getStartingTime());
				Date tempEnd = df.parse(p.getEndingTime());
				if( (startTime.after(tempStart) && startTime.before(tempEnd)) || 
						(endTime.after(tempStart) && endTime.before(tempEnd)) ||
						(startTime.equals(tempStart) && endTime.equals(tempEnd))) { //then there is conflict
					return true;
				}
			}	

		}
		return false;
	}

	/**
	 * Return a string that contains all the events for a specific date
	 * @param date date to get events from
	 * @return String of all events for a specific date
	 */
	public String printEvents(Date date){
		String all = "";
		for(Event e : events.get(date)) {
			all += "  " + e.getEventName() + " " + e.getStartingTime() + " - " + e.getEndingTime() + "\n";
		}
		return all;
	}

	/**
	 * Returns an array of all the events
	 * @return ArrayList of events
	 */
	public ArrayList<Event> arrayOfAllEvents() {
		ArrayList<Event> allEvents = new ArrayList<>();
		for(Date date : events.keySet()) {
			for(Event e : events.get(date)) {
				allEvents.add(e);
			}
		}
		return allEvents;
	}

	/**
	 * Gets the current event based on given date
	 * @param date Date
	 * @return event based on date and print it
	 */
	public String getCurrentEvent(Date date) {
		String all = "";
		if(events.containsKey(date)) {
			all = printEvents(date);
		}
		return all;
	}

	/**
	 * Creates a new comparator to sort by starting time using anonymous class.
	 * @return Comparison by time
	 */
	private Comparator<Event> sortTime() {
		return new Comparator<Event>() {

			@Override
			public int compare(Event o1, Event o2) {
				return o1.getStartingTime().compareTo(o2.getStartingTime());
			}
		};
	}	
}
