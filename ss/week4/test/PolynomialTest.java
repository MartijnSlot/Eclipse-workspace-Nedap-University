package week4.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import week4.math.*;

public class PolynomialTest {

    private static final double DELTA = 1e-15;
    private Polynomial polynomial;
	private LinearProduct[] functionlist = {
			new LinearProduct(new Constant(3), new Constant(3)),
			new LinearProduct(new Constant(2), new Constant(2)),
			new LinearProduct(new Constant(1), new Constant(1)),
			new LinearProduct(new Constant(0), new Constant(0))
			};

    @Before
    public void setUp() {
        polynomial = new Polynomial(functionlist);
        System.out.println(polynomial);
    }

    @Test
    public void testApply() {
        assertEquals(0, polynomial.apply(0), DELTA);
        assertEquals(0, polynomial.apply(-1), DELTA);
        assertEquals(0, polynomial.apply(1), DELTA);
    }

    @Test
    public void testDerivative() {
    	assertTrue(polynomial.derivative() instanceof Polynomial);
        assertEquals(0, polynomial.derivative().apply(2), DELTA);

    }
    
    @Test
    public void testIntegrand() {
    	assertTrue(polynomial.integrand() instanceof Polynomial);
        assertEquals(0, polynomial.integrand().apply(2), DELTA);

    }

}
