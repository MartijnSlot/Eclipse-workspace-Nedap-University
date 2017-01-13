package week3.hotel;

public class PricedRoom extends Room implements Bill.Item {

	private double safePrice;
	private double roomPrice;

	public PricedRoom(int number, double roomPrice, double safePrice) {
		super(number);
		this.roomPrice = roomPrice;
		this.safePrice = safePrice;
	}
	
	@Override
	public double getAmount() {
		return roomPrice;
	}
	
	@Override
	public String toString() {
		double pricePerNight = roomPrice + safePrice;
		return "Price per night: " + pricePerNight;
	}

}
