package ss.week1.student;

public class Student {
	
	public static int SCORE = 69;
	public static boolean PASSED = false;
	
	public static void main(String[] args){
	if (SCORE >= 70)
		PASSED = true;
	System.out.println("Passed = " + PASSED);
	}

}
