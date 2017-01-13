package week2;

public class Rectangle {
    private int length;
    private int width;

    //@ private invariant length > 0;
    //@ private invariant width > 0;
    /**
     * Create a new Rectangle with the specified length and width.
     */
    //@ requires theLength >= 0 && theWidth >= 0;
    //@ ensures this.length() == theLength && this.width() == theWidth;
    public Rectangle(int theLength, int theWidth) {
    	this.length = theLength;
    	this.width = theWidth;
    }
    
    /**
     * The length of this Rectangle.
     */
    //@ ensures \result >= 0;
    /*@ pure*/ public int length() {
    	return length;
    }

    /**
     * The width of this Rectangle.
     */
    //@ ensures \result >= 0;
    /* pure */ public int width() {
    	return width;
    }

    /**
     * The area of this Rectangle.
     */
    //@ ensures \result == length() * width();
    /*@ pure */ public int area() {
    	return width()*length();
    }

    /**
     * The perimeter of this Rectangle.
     */
    //@ensures \result == 2 * (length() + width());
    /*@ pure */ public int perimeter() {
    	return 2 * (length() + width());
    }
}
