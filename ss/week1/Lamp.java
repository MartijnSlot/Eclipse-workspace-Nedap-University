package week1;

public class Lamp {
	
	//Class variables
	public static final int LAMP_OFF = 0;
	public static final int LOW = 1;
	public static final int MED = 2;
	public static final int HIGH = 3;
	private int light;
	
	//constructor of instance light from class Lamp
	public Lamp() {
		this.light = LAMP_OFF;
		
	}
	
	//query to get the state of the light
	public int light () {
		return light;
	}
	
	//method or command to switch light intensity
	public void switchSetting(){
		light = (light + 1) % 4;
	}
	
}
