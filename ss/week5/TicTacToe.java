package week5;

/**
 * Executable class for the game Tic Tac Toe. The game can be played against the
 * computer. Lab assignment Module 2
 * 
 * @author Theo Ruys
 * @version $Revision: 1.4 $
 */

public class TicTacToe {

	public static Strategy SmartStrategy;
	public static Player player1;
	public static Player player2;
	public static Player[] playerList;
	public static Mark[] markList;

	public static void main(String[] args) {
		playerList = new Player[2];
		markList = new Mark[2];
		markList[0] = Mark.XX; markList[1] = Mark.OO;
		
		if (args.length != 2) {
			System.out.println("Error: Number of players can only be 2.");
		}

		for (int i = 0; i < args.length; i++){
			if (args[i].equals("-N")) {
				playerList[i] = new ComputerPlayer(markList[0]);
			} else if (args[i].equals("-S")){
				playerList[i] = new ComputerPlayer(markList[0], SmartStrategy);
			} else {
				playerList[i] = new HumanPlayer(args[i],markList[1]);
			}
		}
		
		Game newGame = new Game(playerList[0], playerList[1]);
		newGame.start();
	}
}
