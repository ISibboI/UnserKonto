package unserkonto;

import static org.junit.Assert.assertEquals;

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
import unserkonto.model.Flat;
import unserkonto.model.Inhabitant;
import unserkonto.model.Money;
import unserkonto.model.MoneyTransfer;

public class SerializationTest {
	@Test
	public void test() throws IOException, ClassNotFoundException {
		Flat flat = new Flat("");
		
		Inhabitant[] inhabitants = new Inhabitant[] { flat.getInhabitantManager().createEntity("A"),
				flat.getInhabitantManager().createEntity("B"), flat.getInhabitantManager().createEntity("C") };

		Account originalAccount = flat.getAccount();
		Random r = new Random(25632L);

		for (int i = 0; i < 1000; i++) {
			int money = 0;
			
			do {
				money = r.nextInt(1001) - 500;
			} while (money == 0);
			
			originalAccount.addTransfer(new MoneyTransfer(new Money(money),
					inhabitants[r.nextInt(inhabitants.length)], new Date(r.nextInt(400) * 400_000_000L)));
		}
		
		List<Inhabitant> originalInhabitants = new ArrayList<>(flat.getInhabitantManager().getInhabitants());
		
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		out.writeObject(flat);
		
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bout.toByteArray()));
		flat = (Flat) in.readObject();
		Account account = flat.getAccount();
		
		assertEquals(originalAccount.getTransfers(), account.getTransfers());
		assertEquals(originalAccount.getCurrentBalance(), account.getCurrentBalance());
		assertEquals(originalInhabitants, flat.getInhabitantManager().getInhabitants());
	}
}