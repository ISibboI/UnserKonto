package unserkonto;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Random;

import org.junit.Test;

import unserkonto.model.Money;

public class MoneyTest {
	private void divideThroughAll(Money m, int maxDivisor) {
		for (int divisor = 1; divisor < maxDivisor; divisor++) {
			Collection<Money> quotients = m.divide(divisor);
			Money sum = new Money(0);

			for (Money quotient : quotients) {
				sum = sum.add(quotient);
			}
			
			assertEquals(m, sum);
		}
	}
	
	private void add(int a, int b) throws NoSuchFieldException, SecurityException, IllegalAccessException {
		int sum = a + b;
		
		Money mSum = new Money(a).add(new Money(b));
		
		Field amount = Money.class.getDeclaredField("amount");
		amount.setAccessible(true);
		assertEquals(sum, amount.get(mSum));
		amount.setAccessible(false);
	}
	
	@Test
	public void testDivide() {
		final int MONEY_AMOUNT = 211;

		divideThroughAll(new Money(MONEY_AMOUNT), 2 * MONEY_AMOUNT);
		divideThroughAll(new Money(-MONEY_AMOUNT), 2 * MONEY_AMOUNT);
	}
	
	@Test
	public void testAdd() throws NoSuchFieldException, SecurityException, IllegalAccessException {
		Random r = new Random(547263L);
		
		add(1, 1);
		add(1, -1);
		add(-1, 1);
		add(-1, -1);
		
		for (int i = 0; i < 1000; i++) {
			int a = r.nextInt() / 2;
			int b = r.nextInt() / 2;
			add(a, b);
		}
	}
}
