package ss.week1.MoneyCounter;

public class DollarsAndCentsCounter {
	
	private int dollars;
	private int cents;
	
	//construct object
	public DollarsAndCentsCounter(int dollars, int cents){
		this.dollars = dollars;
		this.cents= cents;
	}
	
	public DollarsAndCentsCounter(){
		this.dollars = 0;
		this.cents= 0;
	}
	
	//queries
	public int dollars(){
			return this.dollars;
	}
	public int cents(){
		return this.cents;
	}
	
	//method add dollar
	public void add(int dollars, int cents) {
		this.dollars = this.dollars + dollars;
		this.cents = this.cents + cents;
		if (this.cents > 99) {
			this.dollars = this.dollars + 1;
			this.cents = this.cents -100;
			}
		if (this.dollars < 0)
			System.out.println("Dollars cannot be negative");
		if (this.cents < 0)
			System.out.println("Cents cannot be negative");
		System.out.println(
				"New amount: " + dollars() + " dollar, " + cents() + " cent");
	}

}
