package test;

import week2.Rectangle;

/**
 * Testprogram for Rectangle.
 * Lab Exercise SoftwareSystems
 * @author Martijn Slot
 * @version $v1.0$
 */
public class RectangleTest {
    /** Testvariabele for a <tt>Rectangle</tt>-object. */
    private Rectangle rect1;
    /** Testvariabele for a <tt>Rectangle</tt>-object. */
    private Rectangle rect2;
    /** Testvariabele for a <tt>Rectangle</tt>-object. */
    private Rectangle rect3;


    /**
     * Sets initial variables to a well-defined initial value.
     * <p>
     * Assigns a <tt>Rectangle</tt> object to the <tt>rect</tt> instance variables
     * with specific sizes for length and width.
     */
    @Before
    public void setUp() {
        // initialisation of Rectangle-variables
        rect1 = new Rectangle(0,6);
        rect2 = new Rectangle(-5,6);
        rect3 = new Rectangle(5,-6);
    }

    /**
     * Test if the initial condition complies to the specification.
     */
    @Test
    public void testInitialCondition() {
        assertEquals("min", 0, rect1.length());
        assertEquals("min", -5, rect2.length());
        assertEquals("min", 5, rect3.length());
        assertEquals("min", 6, rect1.width());
        assertEquals("min", 6, rect2.width());
        assertEquals("min", -6, rect3.width());
    }
    
    /**
     * Test if the area complies to the specification.
     */
    @Test
    public void testArea() {
    	assertEquals(0, rect1.area());
    	assertEquals(-30, rect2.area());
    	assertEquals(-30, rect3.area());
    }
    
    /**
     * Test if the perimeter complies to the specification.
     */
    @Test
    public void testPerimeter() {
    	assertEquals(12, rect1.perimeter());
    	assertEquals(2, rect2.perimeter());
    	assertEquals(-2, rect3.perimeter());
    }
}
