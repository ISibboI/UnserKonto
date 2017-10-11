package unserkonto.model;

import java.io.Serializable;
import java.util.Objects;

public class Inhabitant implements Serializable {
	private static final long serialVersionUID = -4123494171540693428L;
	final String name;
	final int id;
	
	@SuppressWarnings("unused")
	private Inhabitant() {
		name = null;
		id = -1;
	}
	
	Inhabitant(String name, int id) {
		Objects.requireNonNull(name, "name");
		
		this.name = name;
		this.id = id;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Inhabitant) {
			Inhabitant e = (Inhabitant) o;
			return e.name.equals(name) && e.id == id;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() ^ id;
	}
}
