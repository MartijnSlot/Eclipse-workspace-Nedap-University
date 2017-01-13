package week6.voteMachine;

import week6.voteMachine.gui.VoteGUIView;

public class VoteMachine {
	
	private PartyList parties;
	private VoteList votes;
	private VoteView ui;
	
	public VoteMachine() {
		this.votes = new VoteList();
		this.parties = new PartyList();
		this.ui = new VoteGUIView(this);
		parties.addObserver(ui);
		votes.addObserver(ui);
		
	}
	
	public static void main(String[] args){
		new VoteMachine().start();
	}
	
	public void start() {
		ui.start();
	}
	
	public void addParty(String party){
		parties.addParty(party);
	}
	
	public void vote(String party){
		votes.addVote(party);
	}
	
	public PartyList getParties(){
		return this.parties;
	}
	
	public VoteList getVote(){
		return this.votes;
	}
	

}
