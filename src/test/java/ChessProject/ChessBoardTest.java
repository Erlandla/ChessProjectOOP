package ChessProject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChessBoardTest {
	
	private ChessBoard board;
	private ChessPiece piece;
	
	@BeforeEach
	void setUp() {
		board = new ChessBoard();
		piece = new Pawn(PieceColor.white, board);
	}
	
	@Test
	void getPieceTest() {
		assertNull(board.getPiece(0, 0));
//		assertNull(board.getPiece("a1")); //Format no longer in use
		assertThrows(IllegalArgumentException.class, () -> {
			board.getPiece(-1, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			board.getPiece(8, 0);
		});
	}
	
	@Test
	void setPieceTest() {
		ChessPiece testPiece = new Pawn(PieceColor.white, board);
		board.setPiece(0, 0, testPiece);
		assertTrue(board.getPiece(0, 0) instanceof Pawn);
		assertEquals(0, testPiece.getFile());
		assertEquals(0, testPiece.getRank());
		
		board.setPiece(0,1, testPiece);
		assertTrue(board.getPiece(0,1) instanceof Pawn);
		assertEquals(0, testPiece.getFile());
		assertEquals(1, testPiece.getRank());
		
		board.setPiece(7,7, testPiece);
		assertTrue(board.getPiece(7,7) instanceof Pawn);
		assertEquals(7, testPiece.getFile());
		assertEquals(7, testPiece.getRank());
	}
	
	@Test
	void removePieceTest() {
		board.setPiece(0, 0, new Pawn(PieceColor.white, board));
		board.setPiece(0, 0, null);
		assertNull(board.getPiece(0,0));
	}
	
	@Test
	void addPieceOnAnotherPieceTest() {
		board.setPiece(0, 0, new Pawn(PieceColor.black, board));
		board.setPiece(0, 0, new Rook(PieceColor.black, board));
		assertTrue(board.getPiece(0, 0) instanceof Rook);
	}
	
	@Test
	void outOfBoundsTest() {
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			board.setPiece(10, 0, piece);
		});
//		assertThrows(IllegalArgumentException.class, () -> {
//			board.setPiece("a10", piece);
//		});
//		assertThrows(IllegalArgumentException.class, () -> {
//			board.setPiece(" ", piece);
//		});
//		assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
//			board.setPiece("i2", piece);
//		});
	}

}
