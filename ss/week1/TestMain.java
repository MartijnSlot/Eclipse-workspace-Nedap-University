package ss.week1;

public class TestMain {
		
	//Run Lamp

	public static void main (String[] args) {
		/** 
		 * make object lamp1 from class Lamp
		 * call method switchlight on lamp1
		 * print state of query light on lamp1
		 */
		Lamp lamp1 = new Lamp();
		System.out.println(lamp1.light());
		lamp1.switchSetting();
		System.out.println(lamp1.light());
		/** 
		 * make object lamp2 from class LampTest
		 * call method runTest on lamp2
		 */
		LampTest test = new LampTest();
		test.runTest();
		
	}

}
