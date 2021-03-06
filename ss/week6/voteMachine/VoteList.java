package week6.voteMachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class VoteList extends Observable {
	
	public Map<String, Integer> votes;

	public VoteList(){
		votes = new HashMap<String, Integer>();
	}
	
	public Map<String, Integer> getVotes() {
		return votes;
	}

	public void addVote(String party1) {
		if (votes.containsKey(party1)){
			int currentVotes = votes.get(party1);
			currentVotes++;
			votes.remove(party1);
			votes.put(party1, currentVotes);
		} else {
			votes.put(party1, 1);
		}
		this.setChanged();
		this.notifyObservers("vote");
	}

}
