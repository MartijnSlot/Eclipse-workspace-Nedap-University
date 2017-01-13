package ss.week1.Rectangle;

public class Rectangle {
	
	public int length;
	public int width;
	
	//constructor
	public Rectangle (int length, int width){
		this.length = length;
		this.width = width;
	}
	
	//queries
	public int getLength (){
		return length;
	}
	public int getWidth (){
		return width;
	}
	public int getArea () {
		return width * length;
	}
	public int getPerimeter (){
		return 2*length + 2*width;
	}
	
	//methods
	public void displayRectangle(){
		System.out.println(
				"length: " + getLength());
		System.out.println(
				"width: " + getWidth());
		System.out.println(
				"area: " + getArea());
		System.out.println(
				"perimeter: " + getPerimeter());
	}

}
