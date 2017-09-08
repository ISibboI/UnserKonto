package unserkonto.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class EntityManager implements Serializable {
	private static final long serialVersionUID = 925164777557540846L;
	private static final EntityManager entityManager = new EntityManager();
	
	private EntityManager() {}
	
	public static EntityManager getInstance() {
		return entityManager;
	}
	
	private final List<Entity> entities = new ArrayList<>();
	private final Map<String, Entity> entitiesByName = new HashMap<>();
	
	public Entity getEntity(int id) {
		if (id < 0 || id >= entities.size()) {
			throw new IndexOutOfBoundsException("No entity with id " + id);
		}
		
		return entities.get(id);
	}
	
	public boolean hasEntity(int id) {
		return id >= 0 && id < entities.size();
	}
	
	public Entity getEntity(String name) {
		if (!hasEntity(name)) {
			throw new IllegalArgumentException("No entity with name '" + name + "'");
		}
		
		return entitiesByName.get(name);
	}
	
	public boolean hasEntity(String name) {
		return entitiesByName.containsKey(name);
	}
	
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}
	
	public Entity createEntity(String name) {
		if (entitiesByName.containsKey(name)) {
			throw new IllegalArgumentException("An entity with name '" + name + "' already exists");
		}
		
		Entity e = new Entity(name, entities.size());
		entities.add(e);
		entitiesByName.put(name, e);
		return e;
	}
	
	@Deprecated
	public boolean hasName(String name) {
		return hasEntity(name);
	}
	
	public void clear() {
		entities.clear();
	}
	
	public void writeEntities(ObjectOutputStream out) throws IOException {
		out.writeObject(entities);
	}
	
	public void readEntities(ObjectInputStream in) throws IOException, ClassNotFoundException {
		@SuppressWarnings("unchecked")
		List<Entity> loadedEntities = (List<Entity>) in.readObject();
		entities.clear();
		entities.addAll(loadedEntities);
		
		for (Entity entity: loadedEntities) {
			entitiesByName.put(entity.getName(), entity);
		}
	}
}
