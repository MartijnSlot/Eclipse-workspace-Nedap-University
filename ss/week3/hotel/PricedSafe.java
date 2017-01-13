package week3.hotel;

public class PricedSafe extends Safe implements Bill.Item {

	private double price;
	
	public PricedSafe(double price){
		this.price = price;
	}
	
	public String toString(){
		return "Price of Safe:  " + price;
	}
	
	@Override
	public double getAmount() {
		return price;
	}

}
