package ChessProject;

import java.util.ArrayList;

public class Pawn extends ChessPiece {

//	private boolean promote = false;
//	private boolean hasMoved = false;
	private ArrayList<String> legalMoves = new ArrayList<>();
	private int direction;
	private PieceColor oppositeColor;
	
	public ArrayList<String> getLegalMoves() {
		updateLegalMoves();
		return legalMoves;
	}
	
	private void updateLegalMoves() {
		if (legalMoves != null)
			legalMoves.clear();	//fjerner forrige liste
		//rett forover
		if (this.getChessBoard().getPiece(this.getFile(), this.getRank() + direction) == null && this.getRank() + direction <= 7 && this.getRank() + direction >= 0) {
			legalMoves.add(0, new String(Integer.toString(this.getFile()) + Integer.toString(this.getRank() + direction)));
			//2step forover i begynnelsen, gitt at det er ledig rett forover
			if ((this.getRank() == 1 && this.getColor().equals(PieceColor.black)) || (this.getRank() == 6 && this.getColor().equals(PieceColor.white) )){
				legalMoves.add(0, new String(Integer.toString(this.getFile()) + Integer.toString(this.getRank()+2*direction)));
			}
		}
		
		//skrått fremover der det er en brikke
		int forwardRank = this.getRank() + direction;
		int forwardLeftFile = this.getFile() -1;
		int forwardRightFile = this.getFile() +1;
		
		//sjekker om bonden kan flytte skrått til venstre og framover
		if (forwardLeftFile >= 0 && forwardRank <= 7 && forwardRank >= 0) {	//må sjekke at ruten eksisterer
			if (this.getChessBoard().getPiece(forwardLeftFile, forwardRank) != null) { //må sjekke at det er en brikke der
				if (this.getChessBoard().getPiece(forwardLeftFile, forwardRank).getColor() == oppositeColor) { //må sjekke at brikken der har motsatt farge
					legalMoves.add(0, new String(Integer.toString(forwardLeftFile) + Integer.toString(forwardRank)));
				}
			}
		}
		if (forwardRightFile <= 7 && forwardRank <= 7 && forwardRank >= 0) { //samme som over for høyre side diagonalt istedet
			if (this.getChessBoard().getPiece(forwardRightFile, forwardRank) != null) {
				if (this.getChessBoard().getPiece(forwardRightFile, forwardRank).getColor() == oppositeColor) {
					legalMoves.add(0, new String(Integer.toString(forwardRightFile) + Integer.toString(forwardRank)));
				}
			}
		}
	}
	
	public Pawn(PieceColor color, ChessBoard chessBoard) {
		super(color, chessBoard);
		if (this.getColor().equals(PieceColor.white)) {
			this.direction = -1;
			this.oppositeColor = PieceColor.black;
		}
		else if (this.getColor().equals(PieceColor.black)) {
			this.direction = 1;
			this.oppositeColor = PieceColor.white;
		}
	}
	
	
	//Turns the pawn into either a rook, bishop, knight, or queen
	public void promote(String target) {
        ChessBoard board = this.getChessBoard();
        int file = this.getFile();
        int rank = this.getRank();
        if (target == "queen") {
            ChessPiece piece = new Queen(this.getColor(), board);
            board.setPiece(file, rank, piece);
        }
        else if (target == "rook") {
            ChessPiece piece = new Rook(this.getColor(), board);
            board.setPiece(file, rank, piece);
        }
        else if (target == "bishop") {
            ChessPiece piece = new Bishop(this.getColor(), board);
            board.setPiece(file, rank, piece);
        }
        else if (target == "knight") {
            ChessPiece piece = new Knight(this.getColor(), board);
            board.setPiece(file, rank, piece);
        }

    }
	@Override
	public String toString() {
		return String.format("%s", Pawn.class);
	}
	
	
//	private boolean canPromote() {
//		if (this.getColor().equals(PieceColor.white) && this.getRank() == 0) {
//			this.promote = true;
//		} else if (this.getColor().equals(PieceColor.black) && this.getRank() == 7) {
//			this.promote = true;
//		}
//		return this.promote;
//	}
	
//	private void enPeasant() {
//		
//	}
}
