

import java.io.Serializable;


public class Event implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String eventName, startingTime, endingTime, eventDate;

	public Event(String eventName, String startingTime, String endingTime, String date) {
		this.eventName = eventName;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		this.eventDate = date;
	}

	public String getEventDate() {
		return eventDate;
	}
	public String getEventName() {
		return eventName;
	}

	public String getStartingTime() {
		return startingTime;
	}

	public String getEndingTime() {
		return endingTime;
	}
}
