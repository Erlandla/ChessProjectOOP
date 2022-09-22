package ChessProject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class ChessPieceTest {
	
	private ChessPiece piece;
	private ChessBoard board;
	
	@BeforeEach
	void setUp() {
		piece = null;
		board = new ChessBoard();
	}

	@Test
	@DisplayName("Pawn is created correctly")
	void pawnConstructTest() {
		piece = new Pawn(PieceColor.white, board);
		assertTrue(piece instanceof Pawn && piece.getColor().equals(PieceColor.white));
		assertFalse(piece instanceof Pawn && piece.getColor().equals(PieceColor.black));
		
		piece = new Pawn(PieceColor.black, board);
		assertTrue(piece instanceof Pawn && piece.getColor().equals(PieceColor.black));
		assertFalse(piece instanceof Pawn && piece.getColor().equals(PieceColor.white));
		
		assertTrue(piece instanceof ChessPiece);
		
		// Requires more functionality like moving and en passant to be fully tested
		// Probably gonna handle movement on the board
	}
	@Test
	@DisplayName("Pawn's legal moves and position are correct")
	void pawnPositionTest() {
		piece = new Pawn(PieceColor.white, board);
		board.setPiece(0, 6, piece);
		assertEquals(0, piece.getFile());
		assertEquals(6, piece.getRank());
	}
	@Test
	@DisplayName("Pawn's legal moves are correct") 
	void pawnLegalMovesTest() {
		piece = new Pawn(PieceColor.white, board);
		board.setPiece(0, 6, piece);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertTrue(legalMoves.contains("04"));
		assertTrue(legalMoves.contains("05"));
	}
	@Test
	@DisplayName("Pawn is promotable")
	void isPromotableTest() {
		// Checks if white pawns are promotable in black's base
		piece = new Pawn(PieceColor.white, board);
		board.setPiece(0, 0, piece);
		assertTrue(piece.getPromotable());
		// Checks if black pawns are not promotable in their base
		piece = new Pawn(PieceColor.black, board);
		board.setPiece(0, 0, piece);
		assertFalse(piece.getPromotable());
		// Not promotable elsewhere
		piece = new Pawn(PieceColor.white, board);
		board.setPiece(0, 1, piece);
		assertFalse(piece.getPromotable());
		// Black in white base
		piece = new Pawn(PieceColor.black, board);
		board.setPiece(0, 7, piece);
		assertTrue(piece.getPromotable());
		// White in white base
		piece = new Pawn(PieceColor.white, board);
		board.setPiece(0, 7, piece);
		assertFalse(piece.getPromotable());
		// Black outside of white base
		piece = new Pawn(PieceColor.black, board);
		board.setPiece(0, 6, piece);
		assertFalse(piece.getPromotable());
	}
	
	@Test
	@DisplayName("Rook is created correctly")
	void rookTest() {
		piece = new Rook(PieceColor.white, board);
		assertTrue(piece instanceof Rook && piece.getColor().equals(PieceColor.white));
		assertFalse(piece instanceof Rook && piece.getColor().equals(PieceColor.black));
		
		piece = new Rook(PieceColor.black, board);
		assertTrue(piece instanceof Rook && piece.getColor().equals(PieceColor.black));
		assertFalse(piece instanceof Rook && piece.getColor().equals(PieceColor.white));
		
		assertTrue(piece instanceof ChessPiece);
	}
	@Test
	@DisplayName("Rook's legal moves and position are correct")
	void rookPositionTest() {
		piece = new Rook(PieceColor.white, board);
		board.setPiece(0, 6, piece);
		assertEquals(0, piece.getFile());
		assertEquals(6, piece.getRank());
	}
	@Test
	@DisplayName("Rook's legal moves are correct") 
	void rookLegalMovesTest() {
		piece = new Rook(PieceColor.black, board);
		board.setPiece(0, 0, piece);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertTrue(legalMoves.contains("10"));
		assertTrue(legalMoves.contains("20"));
		assertTrue(legalMoves.contains("30"));
		assertTrue(legalMoves.contains("40"));
		assertTrue(legalMoves.contains("50"));
		assertTrue(legalMoves.contains("60"));
		assertTrue(legalMoves.contains("70"));
		assertTrue(legalMoves.contains("01"));
		assertTrue(legalMoves.contains("02"));
		assertTrue(legalMoves.contains("03"));
		assertTrue(legalMoves.contains("04"));
		assertTrue(legalMoves.contains("05"));
		assertTrue(legalMoves.contains("06"));
		assertTrue(legalMoves.contains("07"));
		assertEquals(14, legalMoves.size());
	}
	
	@Test
	@DisplayName("Knight is created correctly")
	void knightTest() {
		piece = new Knight(PieceColor.white, board);
		assertTrue(piece instanceof Knight && piece.getColor().equals(PieceColor.white));
		assertFalse(piece instanceof Knight && piece.getColor().equals(PieceColor.black));
		
		piece = new Knight(PieceColor.black, board);
		assertTrue(piece instanceof Knight && piece.getColor().equals(PieceColor.black));
		assertFalse(piece instanceof Knight && piece.getColor().equals(PieceColor.white));
		
		assertTrue(piece instanceof ChessPiece);
	}
	@Test
	@DisplayName("Knight's legal moves and position are correct")
	void knightPositionTest() {
		piece = new Knight(PieceColor.white, board);
		board.setPiece(0, 6, piece);
		assertEquals(0, piece.getFile());
		assertEquals(6, piece.getRank());
	}
	@Test
	@DisplayName("Knight's legal moves are correct") 
	void knightLegalMovesTest() {
		piece = new Knight(PieceColor.white, board);
		board.setPiece(4, 4, piece);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertEquals(8, legalMoves.size());
		assertTrue(legalMoves.contains("32"));
		assertTrue(legalMoves.contains("52"));
		assertTrue(legalMoves.contains("23"));
		assertTrue(legalMoves.contains("63"));
		assertTrue(legalMoves.contains("25"));
		assertTrue(legalMoves.contains("36"));
		assertTrue(legalMoves.contains("56"));
		assertTrue(legalMoves.contains("65"));
	}
	
	@Test
	@DisplayName("Queen is created correctly")
	void queenTest() {
		piece = new Queen(PieceColor.white, board);
		assertTrue(piece instanceof Queen && piece.getColor().equals(PieceColor.white));
		assertFalse(piece instanceof Queen && piece.getColor().equals(PieceColor.black));
		
		piece = new Queen(PieceColor.black, board);
		assertTrue(piece instanceof Queen && piece.getColor().equals(PieceColor.black));
		assertFalse(piece instanceof Queen && piece.getColor().equals(PieceColor.white));
		
		assertTrue(piece instanceof ChessPiece);
	}
	@Test
	@DisplayName("Queen's legal moves and position are correct")
	void queenPositionTest() {
		piece = new Queen(PieceColor.white, board);
		board.setPiece(1, 2, piece);
		assertEquals(1, piece.getFile());
		assertEquals(2, piece.getRank());
	}
	@Test
	@DisplayName("Queen's legal moves are correct") 
	void queenLegalMovesTest() {
		piece = new Queen(PieceColor.white, board);
		board.setPiece(0, 0, piece);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertEquals(21, legalMoves.size());
		assertTrue(legalMoves.contains("10"));
		assertTrue(legalMoves.contains("20"));
		assertTrue(legalMoves.contains("30"));
		assertTrue(legalMoves.contains("40"));
		assertTrue(legalMoves.contains("50"));
		assertTrue(legalMoves.contains("60"));
		assertTrue(legalMoves.contains("70"));
		assertTrue(legalMoves.contains("01"));
		assertTrue(legalMoves.contains("02"));
		assertTrue(legalMoves.contains("03"));
		assertTrue(legalMoves.contains("04"));
		assertTrue(legalMoves.contains("05"));
		assertTrue(legalMoves.contains("06"));
		assertTrue(legalMoves.contains("07"));
		assertTrue(legalMoves.contains("11"));
		assertTrue(legalMoves.contains("22"));
		assertTrue(legalMoves.contains("33"));
		assertTrue(legalMoves.contains("44"));
		assertTrue(legalMoves.contains("55"));
		assertTrue(legalMoves.contains("66"));
		assertTrue(legalMoves.contains("77"));
	}
	
	@Test
	@DisplayName("Bishop is created correctly")
	void bishopTest() {
		piece = new Bishop(PieceColor.white, board);
		assertTrue(piece instanceof Bishop && piece.getColor().equals(PieceColor.white));
		assertFalse(piece instanceof Bishop && piece.getColor().equals(PieceColor.black));
		
		piece = new Bishop(PieceColor.black, board);
		assertTrue(piece instanceof Bishop && piece.getColor().equals(PieceColor.black));
		assertFalse(piece instanceof Bishop && piece.getColor().equals(PieceColor.white));
		
		assertTrue(piece instanceof ChessPiece);
	}
	@Test
	@DisplayName("Bishop's legal moves and position are correct")
	void bishopPositionTest() {
		piece = new Bishop(PieceColor.white, board);
		board.setPiece(1, 2, piece);
		assertEquals(1, piece.getFile());
		assertEquals(2, piece.getRank());
	}
	@Test
	@DisplayName("Bishop's legal moves are correct") 
	void bishopLegalMovesTest() {
		piece = new Bishop(PieceColor.white, board);
		board.setPiece(1, 1, piece);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertEquals(9, legalMoves.size());
		assertTrue(legalMoves.contains("00"));
		assertTrue(legalMoves.contains("22"));
		assertTrue(legalMoves.contains("33"));
		assertTrue(legalMoves.contains("44"));
		assertTrue(legalMoves.contains("55"));
		assertTrue(legalMoves.contains("66"));
		assertTrue(legalMoves.contains("77"));
		assertTrue(legalMoves.contains("02"));
		assertTrue(legalMoves.contains("20"));
	}
	
	@Test
	@DisplayName("King is created correctly")
	void kingTest() {
		piece = new King(PieceColor.white, board);
		assertTrue(piece instanceof King && piece.getColor().equals(PieceColor.white));
		assertFalse(piece instanceof King && piece.getColor().equals(PieceColor.black));
		
		piece = new King(PieceColor.black, board);
		assertTrue(piece instanceof King && piece.getColor().equals(PieceColor.black));
		assertFalse(piece instanceof King && piece.getColor().equals(PieceColor.white));
		
		assertTrue(piece instanceof ChessPiece);
	}
	@Test
	@DisplayName("King's legal moves and position are correct")
	void kingPositionTest() {
		piece = new Bishop(PieceColor.white, board);
		board.setPiece(6, 7, piece);
		assertEquals(6, piece.getFile());
		assertEquals(7, piece.getRank());
	}
	@Test
	@DisplayName("King's legal moves are correct") 
	void kingLegalMovesTest() {
		piece = new King(PieceColor.white, board);
		board.setPiece(1, 1, piece);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertEquals(8, legalMoves.size());
		assertTrue(legalMoves.contains("00"));
		assertTrue(legalMoves.contains("01"));
		assertTrue(legalMoves.contains("02"));
		assertTrue(legalMoves.contains("10"));
		assertTrue(legalMoves.contains("20"));
		assertTrue(legalMoves.contains("12"));
		assertTrue(legalMoves.contains("21"));
		assertTrue(legalMoves.contains("22"));
	}
	
	@Test
	@DisplayName("King should not be able to move into a tile where an enemy tile can attack")
	void kingIsLimitedTest() {
		piece = new King(PieceColor.white, board);
		Queen killerQueen = new Queen(PieceColor.black, board);
		board.setPiece(3, 4, piece);
		board.setPiece(5, 5, killerQueen);
		ArrayList<String> legalMoves = piece.getLegalMoves();
		assertFalse(legalMoves.size() == 8);
		assertEquals(3, legalMoves.size());
		assertTrue(legalMoves.contains("23"));
		assertTrue(legalMoves.contains("24"));
		assertTrue(legalMoves.contains("43"));
	}
}
