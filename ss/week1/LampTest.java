package ss.week1;

public class LampTest {
	
	private Lamp testLamp; // test object
	
	//create lamp test object
	public LampTest() {
		this.testLamp = new Lamp();
	}
	
	//Run test
	public void runTest() {
		testInitialState();	
		testChange();
	}
	
	//test initial state
	private void testInitialState(){
		System.out.println(
				"testInitialState:");
		System.out.println(
				"Initial light: " + testLamp.light());
	}
	
	//test changes
	private void testChange(){
		System.out.println(
				"testChange:");
		System.out.println(
				"Starting light: " + testLamp.light());
		testLamp.switchSetting();
		System.out.println(
				"After 1 change: " + testLamp.light());
		testLamp.switchSetting();
		testLamp.switchSetting();
		System.out.println(
				"After 3 changes: " + testLamp.light());
		testLamp.switchSetting();
		System.out.println(
				"After 4 changes: " + testLamp.light());
	}

}
