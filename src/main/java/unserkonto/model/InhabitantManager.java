package unserkonto.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InhabitantManager implements Serializable {
	private static final long serialVersionUID = 925164777557540846L;
	
	private final List<Inhabitant> entities = new ArrayList<>();
	private final Map<String, Inhabitant> entitiesByName = new HashMap<>();
	
	public Inhabitant getEntity(int id) {
		if (id < 0 || id >= entities.size()) {
			throw new IndexOutOfBoundsException("No entity with id " + id);
		}
		
		return entities.get(id);
	}
	
	public boolean hasEntity(int id) {
		return id >= 0 && id < entities.size();
	}
	
	public Inhabitant getEntity(String name) {
		if (!hasEntity(name)) {
			throw new IllegalArgumentException("No entity with name '" + name + "'");
		}
		
		return entitiesByName.get(name);
	}
	
	public boolean hasEntity(String name) {
		return entitiesByName.containsKey(name);
	}
	
	public List<Inhabitant> getInhabitants() {
		return Collections.unmodifiableList(entities);
	}
	
	public Inhabitant createEntity(String name) {
		if (entitiesByName.containsKey(name)) {
			throw new IllegalArgumentException("An entity with name '" + name + "' already exists");
		}
		
		Inhabitant e = new Inhabitant(name, entities.size());
		entities.add(e);
		entitiesByName.put(name, e);
		return e;
	}
	
	public boolean hasName(String name) {
		return hasEntity(name);
	}
	
	public void clear() {
		entities.clear();
	}
	
	public void writeInhabitants(ObjectOutputStream out) throws IOException {
		out.writeObject(entities);
	}
	
	public void readInhabitants(ObjectInputStream in) throws IOException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		List<Inhabitant> loadedEntities = (List<Inhabitant>) in.readObject();
		entities.clear();
		entities.addAll(loadedEntities);
		
		for (Inhabitant entity: loadedEntities) {
			entitiesByName.put(entity.getName(), entity);
		}
	}
}
