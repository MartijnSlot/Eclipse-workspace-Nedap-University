package week2.test;

import week2.hotel.*;
import static org.junit.Assert.*;
import org.junit.*;

public class SafeTest {
    /** Test variable for a <tt>Safe</tt> object. */
    private Safe safe;
    private Password pw;
    private String badPassword = "Apekop";
    private String goodPassword = "Drol41";

	@Before
	public void setUp() {
		pw = new Password();
		pw.setWord(Password.FACTORYPASSWORD, "Drol41");
		safe = new Safe();	
	}

	@Test
	public void testInitialState() {
		assertTrue(pw.testWord(safe.getPassword()));
	    assertFalse(safe.isActive());
	    assertFalse(safe.isOpen());
	}
	
	@Test
	public void testSafeOpen() {
		safe.activate(goodPassword);
		safe.open(goodPassword);
		assertTrue(safe.isOpen());
		safe.deactivate();
		safe.activate(badPassword);
		safe.open(badPassword);
		assertFalse(safe.isOpen());
	}
	
	@Test
	public void testSafeClosed() {
		safe.close();
		assertFalse(safe.isOpen());;
	}
	
	@Test
	public void testSafeAcivated() {
		safe.activate(goodPassword);
		assertTrue(safe.isActive());
		safe.deactivate();
		safe.activate(badPassword);
		assertFalse(safe.isActive());
	}
	
	@Test
	public void testSafeDeactivated() {
		safe.deactivate();
		assertFalse(safe.isActive());
	}

}
