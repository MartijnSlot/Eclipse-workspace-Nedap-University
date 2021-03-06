package week2.hotel;

public class Hotel {

	// ---vars
	public Room room101;
	public Room room102;
	public String hotelName;
	

	// ---constructor
	public Hotel(String name) {
		this.hotelName = name;
		this.room101 = new Room(101);
		this.room102 = new Room(102);
	}
	
	// ---queries
	public Room getFreeRoom() {
		Room output = null;
		if (this.room101.getGuest() == null){
			output = this.room101;
			}
		else if (this.room102.getGuest() == null){
			output = this.room102;
			}
		
		return output;
	}
	
	
	public Room getRoom(String guestName) {
		
		Room output = null;
		
		if (this.room101.getGuest() != null){
			if (this.room101.getGuest().getName().equals(guestName)){
				output = this.room101;
			}
		}
		
		if (this.room102.getGuest() != null){
			if (this.room102.getGuest().getName().equals(guestName)){
				output = this.room102;
			}
		}
		return output;
	}

	public Password getPassword() {
		return new Password();
	}

	 @Override
	public String toString() { 
		  String output = room101.getGuest().getName();
		  return output; 
		  } 

//	 ---commands
	public Room checkIn(String pwAttempt, String guestName) {
		Room output = null;
		if (getPassword().testWord(pwAttempt)) {
			Room freeRoom = getFreeRoom();
			if (freeRoom != null){
				Guest guest = new Guest(guestName);
				guest.checkin(freeRoom);
				freeRoom.setGuest(guest);
				output = freeRoom;
			}
		}
		
		return output;
	}

	public void checkOut(String guestName) {
		Room room = getRoom(guestName);
		if (room != null) {
			if (room.getSafe().isOpen()) {
				room.getSafe().close();
			}
			room.getSafe().deactivate();
			room.getGuest().checkout();
			room.setGuest(null);
		} 
	}

}
