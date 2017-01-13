package week2;

public class Lamp {
	
	//Class variables
	public enum Brightness{
		LAMP_OFF, LOW, MED, HIGH, EXPLODED
	}
	public Brightness light = Brightness.LAMP_OFF;
	
	//@ public invariant light >= 0 && light <= 3;
	
	//constructor of instance light from class Lamp
	// ensures light() == brightness;
	public Lamp() {		
	}
	
	//query to get the state of the light
	//@ ensures \result >= 0 && \result <= 3;
	/*@ pure */ public Brightness light () {
		return light;
	}
	
	//method or command to switch light intensity
	//@	ensures light() == \old(light()) + 1 % 4;
	/*@ pure */ public void switchSetting(){
		switch (light) {
		case LAMP_OFF:
			light = Brightness.LOW;
			break;
		case LOW:
			light = Brightness.MED;
			break;
		case MED:
			light = Brightness.HIGH;
			break;
		case HIGH:
			light = Brightness.EXPLODED;
			break;
		default: 
			System.out.println("Lamp error.");
			light = Brightness.LAMP_OFF;
		
		}
		
	}
	
}

