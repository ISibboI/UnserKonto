package unserkonto;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import unserkonto.model.Account;
import unserkonto.model.Entity;
import unserkonto.model.EntityManager;
import unserkonto.model.Money;
import unserkonto.model.MoneyTransfer;

public class SerializationTest {
	@Test
	public void test() throws IOException, ClassNotFoundException {
		Entity[] entities = new Entity[] { EntityManager.getInstance().createEntity("A"),
				EntityManager.getInstance().createEntity("B"), EntityManager.getInstance().createEntity("C") };

		Account originalAccount = new Account();
		Random r = new Random(25632L);

		for (int i = 0; i < 1000; i++) {
			originalAccount.addTransfer(new MoneyTransfer(new Money(r.nextInt(1001) - 500),
					entities[r.nextInt(entities.length)], new Date(r.nextInt(400) * 400_000_000L)));
		}
		
		List<Entity> originalEntities = new ArrayList<>(EntityManager.getInstance().getEntities());
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		EntityManager.getInstance().writeEntities(out);
		out.writeObject(originalAccount);
		EntityManager.getInstance().clear();
		
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
		EntityManager.getInstance().readEntities(in);
		Account account = (Account) in.readObject();
		
		assertEquals(originalAccount.getTransfers(), account.getTransfers());
		assertEquals(originalAccount.getCurrentBalance(), account.getCurrentBalance());
		assertEquals(originalEntities, EntityManager.getInstance().getEntities());
	}
}