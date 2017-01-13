package week3.pw;

public class StrongChecker implements Checker {
	
	private static final int MIN_SIZE = 6;
	private static final String SPACE = " ";

	@Override
	public boolean acceptable(String pw) {
		boolean result = false;
		if (pw.length() >= MIN_SIZE && !pw.contains(SPACE)) {
			if (Character.isLetter(pw.charAt(1)) && Character.isDigit(pw.charAt(pw.length() - 1))) {
				result = !result;
				return result;
			}
		}
		return result;
	}

	public String generatePassword() {
		return "aaaaaaaaaaaa1";
	}


}
