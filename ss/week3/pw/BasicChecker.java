package week3.pw;

public class BasicChecker implements Checker {
	
	private static final int MIN_SIZE = 6;
	private static final String SPACE = " ";
	public static final String INITPASS = "Drol42";
	
	public BasicChecker(){
		
	}

	@Override
	public boolean acceptable(String pw) {
		return (pw.length() >= MIN_SIZE && !pw.contains(SPACE));
	}

	public String generatePassword() {
		return "111111111a";
	}

}
