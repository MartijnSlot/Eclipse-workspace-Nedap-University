package week6.voteMachine;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class VoteTUIView implements Observer, VoteView {

	public VoteMachine vm;

	public VoteTUIView(VoteMachine vm){
		this.vm = vm;
	}

	public void start(){
		String input = null;
		Scanner in = new Scanner(System.in);

		System.out.println("Type one:");
		System.out.println("VOTE [party], ADD PARTY [party], VOTES, PARTIES, EXIT or HELP");
		input = in.hasNextLine() ? in.nextLine() : null;

		if (input.equals("VOTES")) {
			vm.getVote();
		} else if (input.contains("VOTE")) {
			vm.vote(input.split(" ")[1]);
		} else if (input.contains("ADD PARTY")) {
			vm.addParty(input.split(" ")[2]);
		} else if (input.equals("PARTIES")) {
			vm.getParties();
		} else if (input.equals("EXIT")) {
			System.out.println("Toedeledokie");
		} else if (input.equals("HELP")) {
		}

		in.close();
	}

	public void showVotes(Map<String, Integer> map){
		for (String ki : map.keySet()){
			System.out.println(ki + map.get(ki));
		}
	}

	public void showParties(List<String> list){
		for (String party : list){
			System.out.println(party);
		}
	}

	public void showError(String error){
		System.out.println(error);
	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("added: " + arg);
		
	}

}
