package unserkonto.model.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import unserkonto.model.Inhabitant;

public class EventManager implements Iterable<Event> {
	public static class Phase {
		private final Date start;
		private Date end;
		private final Set<Inhabitant> inhabitants;

		public Phase(Date start, Date end, Set<Inhabitant> inhabitants) {
			this.start = start;
			this.end = end;
			this.inhabitants = inhabitants;
		}

		public Date getStart() {
			return start;
		}

		public Date getEnd() {
			return end;
		}
		
		public void setEnd(Date end) {
			this.end = end;
		}

		public Set<Inhabitant> getInhabitants() {
			return inhabitants;
		}

		public Phase next(Date date) {
			return new Phase(end, date, new HashSet<Inhabitant>(inhabitants));
		}
	}

	private final List<Event> events = new ArrayList<>();

	public void addEvent(Event e) {
		events.add(e);
		Collections.sort(events);
	}

	@Override
	public Iterator<Event> iterator() {
		return events.iterator();
	}

	public List<Phase> getPhasesBetween(Date start, Date end) {
		if (start.compareTo(end) >= 0) {
			throw new IllegalArgumentException("Start date must be before end date!");
		}
		
		List<Phase> result = new ArrayList<>();
		
		if (events.size() == 0) {
			return result;
		}
		
		// TODO 
		
		throw new UnsupportedOperationException();
	}
}
