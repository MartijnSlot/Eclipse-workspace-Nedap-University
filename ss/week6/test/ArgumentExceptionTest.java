package week6.test;

import org.junit.Before;
import org.junit.Test;

import week6.ArgumentLengthsDifferException;
import week6.TooFewArgumentsException;
import week6.WrongArgumentException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testprogram for ArgumentException.
 * Lab Exercise SoftwareSystems
 * @author Jip Spel
 * @version $Revision: 1.0 $
 */
public class ArgumentExceptionTest {

    /** Testvariabele for a <tt>WrongArgumentException</tt> object. */
    private WrongArgumentException wrongArgumentException;

    @Before
    public void setUp() {
        wrongArgumentException = new WrongArgumentException("C");
    }

    /**
     * Test <tt>TooFewArgumentsException</tt>
     */
    @Test
    public void testTooFewArgumentsException() {
        TooFewArgumentsException exception = new TooFewArgumentsException("B");
        assertTrue(exception instanceof WrongArgumentException);
        assertFalse(exception.getMessage().equals(wrongArgumentException.getMessage()));
    }

    /**
     * Test <tt>ArgumentLengthsDifferException</tt>
     */
    @Test
    public void testArgumentLengthsDifferException() {
        ArgumentLengthsDifferException exception = new ArgumentLengthsDifferException("a");
        assertTrue(exception instanceof WrongArgumentException);
        assertFalse(exception.getMessage().equals(wrongArgumentException.getMessage()));
    }
}
