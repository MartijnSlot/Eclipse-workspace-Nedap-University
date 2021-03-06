package ss.week1.hotel;

public class Guest {
	
	private Room room;
	private String name;

	//constructor
    /**
     * Creates a <code>Guest</code> with the given name, without a room.
     * @param n name of the new <code>Guest</code>
     */
	public Guest(String n){
		this.name = n;
	}

	//methods -- queries

	 /**
     * Returns the current Room with <code>Guest</code> living in it.
     * @return the Room of this <code>Guest</code>;
     *         <code>null</code> if this <code>Guest</code> 
     *         is not currently renting
     */
	public Room getRoom(){
			return room;
	}
    /**
     * @return the name of this <code>Guest</code>.
     */
	public String getName(){
		return name;
	}
    /**
     * Rents a Room to this Guest. 
     * This is only possible if this Guest does not already have a Room 
     * and the Room to be assigned is not already rented. 
     * Also adapts the Guest-reference of the Room.
     */
	public boolean checkin(Room room){
		if (room.getGuest() == null && this.getRoom() == null) {
			room.setGuest(this);
			this.room = room;
			return true;
		} else {
			return false;
		}
				
	}
    /**
     * Sets the Room of this <code>Guest</code> to null. Also resets the Guest-reference of the (current) <code>Room</code>.
     */
	public boolean checkout(){
		if (this.getRoom() != null) {
			// Room room = this.getRoom();
			room.setGuest(null);
			room = null;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString(){
		return "Name of the guest:" + this.name + ", " + "Room of customer:" + this.getRoom().getNumber();
	}
	
}