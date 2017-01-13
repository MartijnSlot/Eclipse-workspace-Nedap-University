package week2.hotel;

import week3.pw.*;

public class PasswordCopy {
	
	public static final String INITIAL = "Drol41";
	public String password;
	public static final int MIN_SIZE = 6;
	public static final String SPACE = " ";
	public Checker checker;
	private String factoryPassword = "Drol42";
	
	//constructors
	public PasswordCopy() {
		this.password = INITIAL;
	}
	
	public PasswordCopy(Checker checker) {
		this.checker = new BasicChecker();
		this.password = factoryPassword;
	}
	
	//methods - queries
	
	/**
	 * Test if a given string is an acceptable password. 
	 * Not acceptable: A word with less than 6 characters 
	 * or a word that contains a space.
	 * true If suggestion is acceptable
	 * @param suggestion
	 * @return boolean
	 */
	public boolean acceptable(String suggestion) {
		return (suggestion.length() >= MIN_SIZE && !suggestion.contains(SPACE));
	}
	
	/**
	 * Test if the given word is equal to the current password
	 * true If test is equal to the current password
	 * @param test
	 * @return boolean
	 */
	public boolean testWord(String test) {
		return test.equals(this.password);
	}
	
	/**
	 * Changes the password; true if old is equal to the current 
	 * password and that newpass is an acceptable password
	 * @param oldpass, newpass
	 * @return boolean
	 */
	public boolean setWord(String oldpass, String newpass){
		boolean result = false;
		if (testWord(oldpass) && acceptable(newpass)){
			this.password = newpass;
			result = !result;
		}
		return result;
	}
}

