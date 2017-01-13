package week2.hotel;

public class Safe {
	
	//---vars
	public boolean activeState;
	public boolean openState;
	
	//@ public invariant Password password;
	//@ public invariant activeState;
	//@ public invariant activeState;
	
	
	//---constructor
	public Safe(){
		activeState = false;
		openState = false;
	}
	
	//---queries
	
	//@ ensures /result = true || /result = false;
	/*@ pure */ public boolean isActive(){
		return this.activeState;
	}
	
	//@ ensures /result = true || /result = false;
	/*@ pure */ public boolean isOpen(){
		return this.openState;
	}
	
	//@ ensures /result = password;
	/*@ pure */ public Password getPassword(){
		return new Password();
	}
	
	//---commands
	
	//@ requires password.length() >= 6 && !password.contains(" ");
	public void activate(String attempt){
		if (getPassword().testWord(attempt)) {
			this.activeState = true;
		} else {
			System.out.println("Wrong activation password");
			activeState = false;
		}
	}
	
	//@ requires password.length() >= 6 && !password.contains(" ");
	public void deactivate(){
		if (!this.isOpen()) {
			this.activeState = false;
		}
	}
	
	//@ requires password.length() >= 6 && !password.contains(" ");
	public void open(String attempt){
		if(isActive()) {
			if (getPassword().testWord(attempt)) {
				openState = true;
			}
		} else {
			System.out.println("Wrong open password");
			openState = false;
		}
	}
	
	//@ requires password.length() >= 6 && !password.contains(" ");
	public void close(){
		this.openState = false;
	}
}
