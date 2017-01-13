package week5;

public class ComputerPlayer extends Player {

	private Strategy SmartStrategy;
	private Strategy strategy;
	private Mark mark;

	public ComputerPlayer(Mark mark, Strategy strategy) {
		super("Computer", mark);
		if (strategy == SmartStrategy){
			this.strategy = new SmartStrategy();
		} else {
			this.strategy = new NaiveStrategy();
		}
		this.mark = mark;
	}
	
	public ComputerPlayer(Mark mark) {
		super("Computer", mark);
		this.strategy = new NaiveStrategy();
		this.mark = mark;
	}

	public int determineMove(Board board) {
		return strategy.determineMove(board, this.mark);
	}
	
	public String getName(){
		return strategy.getName() + "-" + mark.toString();
	}

}
