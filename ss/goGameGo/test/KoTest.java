package goGameGo.test;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import goGameGo.*;

public class KoTest {

	private Game game;		
	private Player player1 = new Player("jan", Stone.BLACK);
	private Player player2 = new Player("piet", Stone.WHITE);
	Position b1 = new Position(1,2);
	Position b2 = new Position(2,1);
	Position w1 = new Position(1,1);
	

	@Before
	public void setUp() {
		game = new Game(player1, player2, 5);
		player2.makeMove(game.getBoard(), w1);
		player1.makeMove(game.getBoard(), b1);
		player1.makeMove(game.getBoard(), b2);
		game.updateTUI();

	}
	
	@Test
	public void testIsPoint() {
		assertTrue(game.getBoard().isPoint(w1));
	}
	
	@Test
	public void testEmpty() {
		game.reset();
		assertTrue(game.getBoard().isEmptyPoint(w1));
		assertTrue(game.getBoard().isEmptyPoint(b1));
		assertTrue(game.getBoard().isEmptyPoint(b2));

	}

	@Test
	public void testRemoval() {
		assertTrue(game.getBoard().isEmptyPoint(w1));
		assertTrue(game.getBoard().getPoint(b1).getStone() == Stone.BLACK);
		assertTrue(game.getBoard().getPoint(b2).getStone() == Stone.BLACK);

	}
}

