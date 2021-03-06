package week7.threads;

public class TestConsole extends Thread {
	
	private String name;

	public static void main(String[] args){
		
		TestConsole alpha = new TestConsole("Thread A");
		alpha.start();
		
		TestConsole beta = new TestConsole("Thread B");
		beta.start();
		
	}
	
	public TestConsole(String name){
		this.name = name;
	}
	
	public void run(){
		sum();
	}
	
	private void sum(){
		int int1 = Console.readInt(name + ": Get number 1?");
		int int2 = Console.readInt(name + ": Get number 2?");
		Console.println(name + ": the sum of " + int1 + " and " + int2 + " is " + (int1 + int2));
	}
}
