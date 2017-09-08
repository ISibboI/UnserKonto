package unserkonto.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class EntityManager implements Serializable {
	private static final long serialVersionUID = 925164777557540846L;
	private static final EntityManager entityManager = new EntityManager();
	
	private EntityManager() {}
	
	public static EntityManager getInstance() {
		return entityManager;
	}
	
	private final List<Entity> entities = new ArrayList<>();
	private final Set<String> entityNames = new HashSet<>();
	
	public Entity getEntity(int id) {
		if (id < 0 || id >= entities.size()) {
			throw new IndexOutOfBoundsException("No entity with id " + id);
		}
		
		return entities.get(id);
	}
	
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}
	
	public Entity createEntity(String name) {
		entities.add(new Entity(name, entities.size()));
		entityNames.add(name);
		return entities.get(entities.size() - 1);
	}
	
	public boolean hasName(String name) {
		return entityNames.contains(name);
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
			entityNames.add(entity.getName());
		}
	}
}
