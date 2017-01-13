package week3.hotel;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;

public class BillTest {
	
	public Bill bill1;
	public Bill.Item item1;
	public Bill.Item item2;

	@Before
	public void setUp() throws Exception {
		bill1 = new Bill(System.out);
		item1 = new PricedSafe(5.0);
		item2 = new PricedRoom(101, 12.0, 7.0);
	}

	@Test
	public void testGetSum() {
		assertEquals("Expected: ", 0 , bill1.getSum(), 0);
		
	}
	
	@Test
	public void testNewItem(){
		bill1.newItem(item1);
		assertEquals("Expected: ", 5.0 , bill1.getSum(), 0);
		bill1.newItem(item2);
		assertEquals("Expected: ", 17.0, bill1.getSum(), 0);
				
	}
}
