package unserkonto.model;

import java.io.Serializable;
import java.util.Objects;

public class Entity implements Serializable {
	private static final long serialVersionUID = -4123494171540693428L;
	final String name;
	final int id;
	
	@SuppressWarnings("unused")
	private Entity() {
		name = null;
		id = -1;
	}
	
	Entity(String name, int id) {
		Objects.requireNonNull(name, "name");
		
		this.name = name;
		this.id = id;
	}
	
	public String toString() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean equals(Object o) {
		if (o instanceof Entity) {
			Entity e = (Entity) o;
			return e.name.equals(name) && e.id == id;
		}
		
		return false;
	}
	
	public int hashCode() {
		return name.hashCode() ^ id;
	}
}
