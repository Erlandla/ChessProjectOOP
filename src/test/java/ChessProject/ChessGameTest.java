package ChessProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChessGameTest {
	
	private ChessGame game;
	
	@BeforeEach
	void setUp() {
		game = new ChessGame();
	}

	@Test
	void constructorTest() {
		for (int i=0; i<8; i++) {
			// Checks if all the black pawns are in place
			assertTrue(game.getChessBoard().getPiece(i, 1) instanceof Pawn 
					|| game.getChessBoard().getPiece(i, 1).getColor().equals(PieceColor.black));
			// Checks all the white pawns
			assertTrue(game.getChessBoard().getPiece(i, 6) instanceof Pawn 
					|| game.getChessBoard().getPiece(i, 6).getColor().equals(PieceColor.white));
		}
		// Checks if there are no pieces outside of the initial board state
		for (int y=0; y<4; y++) {
			for (int x=0; x<8; x++) {
				assertNull(game.getChessBoard().getPiece(x, 2+y));
			}
		}
		// Other black pieces 
		assertTrue(game.getChessBoard().getPiece(0, 0) instanceof Rook 
			|| game.getChessBoard().getPiece(0, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(7, 0) instanceof Rook 
			|| game.getChessBoard().getPiece(7, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(1, 0) instanceof Knight 
			|| game.getChessBoard().getPiece(1, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(6, 0) instanceof Knight
			|| game.getChessBoard().getPiece(6, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(2, 0) instanceof Bishop 
			|| game.getChessBoard().getPiece(2, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(5, 0) instanceof Bishop 
			|| game.getChessBoard().getPiece(5, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(3, 0) instanceof Queen 
			|| game.getChessBoard().getPiece(3, 0).getColor().equals(PieceColor.black));
		assertTrue(game.getChessBoard().getPiece(4, 0) instanceof King 
			|| game.getChessBoard().getPiece(4, 0).getColor().equals(PieceColor.black));
		// Other white pieces
		assertTrue(game.getChessBoard().getPiece(0, 7) instanceof Rook 
			|| game.getChessBoard().getPiece(0, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(7, 7) instanceof Rook 
			|| game.getChessBoard().getPiece(7, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(1, 7) instanceof Knight 
			|| game.getChessBoard().getPiece(1, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(6, 7) instanceof Knight
			|| game.getChessBoard().getPiece(6, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(2, 7) instanceof Bishop 
			|| game.getChessBoard().getPiece(2, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(5, 7) instanceof Bishop 
			|| game.getChessBoard().getPiece(5, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(3, 7) instanceof Queen 
			|| game.getChessBoard().getPiece(3, 7).getColor().equals(PieceColor.white));
		assertTrue(game.getChessBoard().getPiece(4, 7) instanceof King 
			|| game.getChessBoard().getPiece(4, 7).getColor().equals(PieceColor.white));
	}
	
	@Test
	void movePieceTest() {
		// Checks if a moved pawn is only at its end location
		game.movePiece(0, 6, 0, 4);
		assertTrue(game.getChessBoard().getPiece(0, 4) instanceof Pawn);
		assertFalse(game.getChessBoard().getPiece(0, 6) instanceof Pawn);
		
		// Checks if an illegal move throws an exception
		assertThrows(IllegalArgumentException.class, () -> {
			game.movePiece(1, 1, 3, 3);
		});
		
		// Checks if an exception is thrown if you try to move a piece that doesn't exist
		assertThrows(IllegalArgumentException.class, () -> {
			game.movePiece(1, 2, 1, 3);
		});
		
		// Checks if you try to move a piece of the enemy colour (i.e. move a white piece on black's turn)
		assertThrows(IllegalArgumentException.class, () -> {
			game.movePiece(1, 6, 1, 4);
		});
	}
	
	@Test
	void turnTimerTest() {
		assertEquals(1, game.getTurnTimer());
		game.movePiece(0, 6, 0, 4);
		assertEquals(2, game.getTurnTimer());
	}
	
	@Test
	void gameOverTest() {
		game.getChessBoard().setPiece(3, 3, new King(PieceColor.white, game.getChessBoard()));
		game.getChessBoard().setPiece(4, 3, new King(PieceColor.black, game.getChessBoard()));
		game.movePiece(3, 3, 4, 3);
		assertThrows(IllegalStateException.class, () -> {
			game.movePiece(0, 1, 0, 3);
		});
	}
	
	@Test
	void promoteTest() {
		Pawn pawn = new Pawn(PieceColor.white, game.getChessBoard());
		game.getChessBoard().setPiece(0, 1, pawn);
		game.getChessBoard().setPiece(0, 0, null);
		game.movePiece(0, 1, 0, 0);
		if (pawn.getPromotable()) {
			pawn.promote("queen");
		}
		assertTrue(game.getChessBoard().getPiece(0, 0) instanceof Queen);
	}
}
