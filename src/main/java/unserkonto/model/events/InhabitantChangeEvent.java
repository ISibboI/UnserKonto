package unserkonto.model.events;

import java.util.Date;

import unserkonto.model.Inhabitant;
import unserkonto.model.events.EventManager.Phase;

public class InhabitantChangeEvent extends Event {
	public static final int MOVING_OUT = 1;
	public static final int MOVING_IN = 2;
	public static final int MOVING_SWITCH = 3;
	
	private final Inhabitant previousInhabitant;
	private final Inhabitant newInhabitant;
	private final int event;
	
	public InhabitantChangeEvent(Date date, Inhabitant previousInhabitant, Inhabitant newInhabitant) {
		super(date);
		
		this.previousInhabitant = previousInhabitant;
		this.newInhabitant = newInhabitant;
		
		int event = 0;
		event += previousInhabitant != null ? MOVING_OUT : 0;
		event += newInhabitant != null ? MOVING_IN : 0;
		this.event = event;
	}

	public Inhabitant getPreviousInhabitant() {
		return previousInhabitant;
	}

	public Inhabitant getNewInhabitant() {
		return newInhabitant;
	}

	public int getEvent() {
		return event;
	}

	@Override
	public Phase trackChange(Phase phase, Date end) {
		phase = phase.next(end);
		
		if ((event & MOVING_OUT) != 0) {
			if (!phase.getInhabitants().remove(previousInhabitant)) {
				throw new IllegalStateException(previousInhabitant.getName() + " moved out even though he/she wasn't living in the flat!");
			}
		}
		
		if ((event & MOVING_IN) != 0) {
			if (!phase.getInhabitants().add(newInhabitant)) {
				throw new IllegalStateException(newInhabitant.getName() + " moved in even though he/she is already living in the flat!");
			}
		}
		
		return phase;
	}
}