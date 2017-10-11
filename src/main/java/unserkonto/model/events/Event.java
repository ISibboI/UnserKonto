package unserkonto.model.events;

import java.util.Date;

import unserkonto.model.events.EventManager.Phase;

public abstract class Event implements Comparable<Event> {
	private Date date;

	public Event(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(Event o) {
		return date.compareTo(o.date);
	}

	public Date getDate() {
		return date;
	}

	public abstract Phase trackChange(Phase phase, Date end);
}
