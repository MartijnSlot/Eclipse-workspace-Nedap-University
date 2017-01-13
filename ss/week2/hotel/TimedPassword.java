package week2.hotel;

import week3.pw.*;

public class TimedPassword extends Password{
	
	private long validTime;
	private long beginTime;
	private boolean result = false;
	
	public TimedPassword(long validTime){
		super(new BasicChecker());
		this.validTime = validTime;
		this.beginTime = System.currentTimeMillis()/1000;
	}
	
	public TimedPassword(){
		super(new BasicChecker());
		this.validTime = 1;
		this.beginTime = System.currentTimeMillis()/1000;
	}
	
	public boolean setWord(String oldpass, String newpass) {
		if(super.setWord(oldpass, newpass)){
			beginTime = (System.currentTimeMillis() / 1000);
			return result;
		}
		return !result;
	}
	
	public boolean isExpired(){
		return (beginTime + validTime) < System.currentTimeMillis()/1000;
	}

}
