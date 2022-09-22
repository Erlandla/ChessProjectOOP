package ChessProject;

import java.util.ArrayList;

public class ChessGame {
//	private Map<ChessPlayer, ChessPiece> selectedPiece;
	private boolean isGameOver;
	private int turnTimer;
	private ArrayList<ChessPiece> deadPieces = new ArrayList<>();
	private ChessBoard chessBoard;
	private boolean promoteState = false;
	
//	public static final int //Might not need these after all, we'll see
//    A8 = 56, B8 = 57, C8 = 58, D8 = 59, E8 = 60, F8 = 61, G8 = 62, H8 = 63, 
//    A7 = 48, B7 = 49, C7 = 50, D7 = 51, E7 = 52, F7 = 53, G7 = 54, H7 = 55, 
//    A6 = 40, B6 = 41, C6 = 42, D6 = 43, E6 = 44, F6 = 45, G6 = 46, H6 = 47, 
//    A5 = 32, B5 = 33, C5 = 34, D5 = 35, E5 = 36, F5 = 37, G5 = 38, H5 = 39, 
//    A4 = 24, B4 = 25, C4 = 26, D4 = 27, E4 = 28, F4 = 29, G4 = 30, H4 = 31, 
//    A3 = 16, B3 = 17, C3 = 18, D3 = 19, E3 = 20, F3 = 21, G3 = 22, H3 = 23, 
//    A2 =  8, B2 =  9, C2 = 10, D2 = 11, E2 = 12, F2 = 13, G2 = 14, H2 = 15, 
//    A1 =  0, B1 =  1, C1 =  2, D1 =  3, E1 =  4, F1 =  5, G1 =  6, H1 =  7;
//	public static final int
//	A = 0, B = 1, C = 2, D = 3, E = 4, F = 5, G = 6, H = 7;
	
	public ChessGame() {	//Constructor; default chess position
		chessBoard = new ChessBoard(); //initating board¨
		gameReset();
		

	
	}
	public ChessGame(String FEN) {
		
	}
	//Planning to implement Constructor using special piece combinations etc. 
	public void nextTurn() {
		turnTimer ++;
	}
	public int getTurnTimer() {
		return turnTimer;
	}
	public void setTurnTimer(int turnTimer) {
		if (turnTimer < 0) {
			throw new IllegalArgumentException("Cannot set turnTimer to a negative number.");
		}
		this.turnTimer = turnTimer;
	}
	public ChessBoard getChessBoard() {
		return this.chessBoard;
	}
	public boolean getPromoteState() {
		return this.promoteState;
	}
//	public void movePiece(String startLocation, String endLocation) {	//files can be considered xCoordinates, ranks can be considered yCoordinates, where right/down is positive
//		ChessPiece mover = chessBoard.getPiece(startLocation);
//		ChessPiece ender = chessBoard.getPiece(endLocation);
//		if (ender != null) {
//			chessBoard.setPiece(endLocation, mover);
//			chessBoard.setPiece(startLocation, null);
//			}
//		else {
//			deadPieces.add(ender);
//			chessBoard.removePiece(endLocation);
//			chessBoard.setPiece(endLocation, mover);
//			chessBoard.setPiece(startLocation, null);
//			}
//		nextTurn();
//	}
	
	public void movePiece(int startFile, int startRank, int endFile, int endRank) {	//files can be considered xCoordinates, ranks can be considered yCoordinates, where right/down is positive
		gameOverCheck();
		validateMove(startFile, startRank, endFile, endRank);
		ChessPiece mover = chessBoard.getPiece(startFile, startRank);
		ChessPiece ender = chessBoard.getPiece(endFile, endRank);
		if (ender instanceof King && !mover.getColor().equals(ender.getColor())) {
			setGameOver();
			if (this.getTurnTimer() % 2 == 0) {
				System.out.println("Black wins");
				deadPieces.add(ender);
				chessBoard.removePiece(endFile, endRank);
				chessBoard.setPiece(endFile, endRank, mover);
				chessBoard.setPiece(startFile, startRank, null);
				return;
			}
			System.out.println("White wins");
			deadPieces.add(ender);
			chessBoard.removePiece(endFile, endRank);
			chessBoard.setPiece(endFile, endRank, mover);
			chessBoard.setPiece(startFile, startRank, null);
			return;
		}
		else if (ender == null) {
			chessBoard.setPiece(endFile, endRank, mover);
			chessBoard.setPiece(startFile, startRank, null);
			}
		else {
			deadPieces.add(ender);
			chessBoard.removePiece(endFile, endRank);
			chessBoard.setPiece(endFile, endRank, mover);
			chessBoard.setPiece(startFile, startRank, null);
			}
		// 
//		promote(endFile, endRank, " ");
		nextTurn();
	}
	private void validateMove(int startFile, int startRank, int endFile, int endRank) {
		if (this.getChessBoard().getPiece(startFile, startRank) == null)
			throw new IllegalArgumentException("No piece on selected tile");
		if (this.getChessBoard().getPiece(startFile, startRank).getColor().equals(PieceColor.black) && this.turnTimer % 2 == 1)
			throw new IllegalArgumentException("Cannot move a black piece on white's turn");
		if (this.getChessBoard().getPiece(startFile, startRank).getColor().equals(PieceColor.white) && this.turnTimer % 2 == 0)
			throw new IllegalArgumentException("Cannot move a white piece on black's turn");
		
		ArrayList<String> legalMoves = this.getChessBoard().getPiece(startFile, startRank).getLegalMoves();
		for (String legalMove : legalMoves) {
			int legalFile = Integer.parseInt(legalMove.substring(0,1));
			int legalRank = Integer.parseInt(legalMove.substring(1,2));
			if (endFile == legalFile && endRank == legalRank)
				return;
		}
		throw new IllegalArgumentException("Invalid move");
	}
	
	// bruh
//	public void promote(int file, int rank, String piece) {
//		if (canPromote(file, rank)) {
//				System.out.println("You can promote your pawn!\nType queen, rook, bishop, or knight to promote to that respective piece");
//				String piecePromotedTo = "";
//	
//				piecePromotedTo = piece;
//				Scanner answer = new Scanner(System.in);
//				piecePromotedTo = answer.nextLine();
//				answer.close();
//				
//				PieceColor color = this.getChessBoard().getPiece(file, rank).getColor();
//				if (piecePromotedTo.equals("queen")) {
//					this.getChessBoard().setPiece(file, rank, new Queen(color, this.getChessBoard()));
//					return;
//				}
//				else if (piecePromotedTo.equals("rook")) {
//					this.getChessBoard().setPiece(file, rank, new Rook(color, this.getChessBoard()));
//					return;
//				}
//				else if (piecePromotedTo.equals("bishop")) {
//					this.getChessBoard().setPiece(file, rank, new Bishop(color, this.getChessBoard()));
//					return;
//				}
//				else if (piecePromotedTo.equals("knight")) {
//					this.getChessBoard().setPiece(file, rank, new Knight(color, this.getChessBoard()));
//					return;
//				}
//				else {
//					promote(file, rank, " ");
//					return;
//				}
//		}
//	}
//	public boolean canPromote(int file, int rank) {
//		if (this.getChessBoard().getPiece(file, rank) instanceof Pawn && 
//				this.getChessBoard().getPiece(file, rank).getColor().equals(PieceColor.black) &&
//				rank == 7) {
//			this.promoteState = true;
//		} else if (this.getChessBoard().getPiece(file, rank) instanceof Pawn && 
//				this.getChessBoard().getPiece(file, rank).getColor().equals(PieceColor.white) &&
//				rank == 0) {
//			this.promoteState = true;
//		}
//		return this.promoteState;
//	}
	
	public boolean getIsGameOver() {
		return this.isGameOver;
	}
	private void setGameOver() {
		this.isGameOver = true;
	}
	private void gameOverCheck() {
		if (this.isGameOver == true)
			throw new IllegalStateException("Game is over");
	}
//	private void removePiece(String position) {
//		deadPieces.add(chessBoard.getPiece(position));
//		chessBoard.setPiece(position, null);
//	}
	
//	public boolean isValidMove(ChessPiece piece, int newFile, char newRank) {
//		return isGameOver;
//		
//	}
	
	public void gameReset() {
		turnTimer = 1;
		isGameOver = false;
		
		//wiping the board
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; i <= 7; i++) {
				chessBoard.setPiece(i, j, null);
			}
		}
		
		//setting all the white pieces 
		chessBoard.setPiece(0,0, new Rook(PieceColor.black, chessBoard));
		chessBoard.setPiece(1,0, new Knight(PieceColor.black, chessBoard));
		chessBoard.setPiece(2,0, new Bishop(PieceColor.black, chessBoard));
		chessBoard.setPiece(3,0, new Queen(PieceColor.black, chessBoard));
		chessBoard.setPiece(4,0, new King(PieceColor.black, chessBoard));
		chessBoard.setPiece(5,0, new Bishop(PieceColor.black, chessBoard));
		chessBoard.setPiece(6,0, new Knight(PieceColor.black, chessBoard));
		chessBoard.setPiece(7,0, new Rook(PieceColor.black, chessBoard));
		for (int i = 0; i <= 7; i++) {
			chessBoard.setPiece(i, 1, new Pawn(PieceColor.black, chessBoard));
		}
		//setting all the black pieces
		chessBoard.setPiece(0,7, new Rook(PieceColor.white, chessBoard));
		chessBoard.setPiece(1,7, new Knight(PieceColor.white, chessBoard));
		chessBoard.setPiece(2,7, new Bishop(PieceColor.white, chessBoard));
		chessBoard.setPiece(3,7, new Queen(PieceColor.white, chessBoard));
		chessBoard.setPiece(4,7, new King(PieceColor.white, chessBoard));
		chessBoard.setPiece(5,7, new Bishop(PieceColor.white, chessBoard));
		chessBoard.setPiece(6,7, new Knight(PieceColor.white, chessBoard));
		chessBoard.setPiece(7,7, new Rook(PieceColor.white, chessBoard));
		for (int i = 0; i <= 7; i++) {
			chessBoard.setPiece(i, 6, new Pawn(PieceColor.white, chessBoard));
		}
		if (!deadPieces.isEmpty())
			this.deadPieces.clear(); //clearing the list of dead pieces
	}
	
	@Override
	public String toString() {
		String board = "";
		for (int y=0; y<8; y++) {
			for (int x=0; x<8; x++) {
				if (this.getChessBoard().getPiece(x, y) instanceof Rook) {
					if (this.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
						board += "R";
					else
						board += "r";
				} else if (this.getChessBoard().getPiece(x, y) instanceof Knight) {
					if (this.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
						board += "N";
					else
						board += "n";
				} else if (this.getChessBoard().getPiece(x, y) instanceof Bishop) {
					if (this.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
						board += "B";
					else
						board += "b";
				} else if (this.getChessBoard().getPiece(x, y) instanceof Queen) {
					if (this.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
						board += "Q";
					else
						board += "q";
				} else if (this.getChessBoard().getPiece(x, y) instanceof King) {
					if (this.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
						board += "K";
					else
						board += "k";
				} else if (this.getChessBoard().getPiece(x, y) instanceof Pawn) {
					if (this.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
						board += "P";
					else
						board += "p";
				}
				else
					board += " ";
				
				if (x == 7)
					board+="\n";
			}
		}
		return board;
	}
	
	public static void main(String[] args) {
	}
}
