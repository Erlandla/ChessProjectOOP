package ChessProject;

import java.util.ArrayList;

public class King extends ChessPiece {

	private boolean hasMoved = false;
	private boolean inCheck;
	private boolean inCheckmate = false;
	private boolean inStalemate = false;
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
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				
				int currentFile = this.getFile() + i;
				int currentRank = this.getRank() + j;
				
				if (currentFile >= 0 && currentFile <= 7 && currentRank>= 0 && currentRank <= 7 && Math.abs(i) + Math.abs(j) >= 1) { //tile must be inside board and also not on self
					//tile must contain nothing or a piece of the opposite team
					if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {	//if nothing
						if (!checkForCheck(currentFile, currentRank)) {		//tile cannot be pinned by enemy piece
							this.legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
						}
					}
					else if (this.getChessBoard().getPiece(currentFile, currentRank) != null) {	//if enemy piece
						if (this.getChessBoard().getPiece(currentFile, currentRank).getColor() != this.getColor()) {
							if (!checkForCheck(currentFile, currentRank)) {	//tile cannot be pinned by enemy piece
								this.legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
							}
						}	
					} 
						
				}
			}
		}
	}
	
	private boolean checkForCheck(int file, int rank) {
		// TODO Auto-generated method stub
			//Look for pawns
		
		//skrått fremover der det er en brikke
		int forwardRank = rank + direction;
		int forwardLeftFile = file -1;
		int forwardRightFile = file +1;
		
		//sjekker om bonden kan flytte skrått til venstre og framover
		if (forwardLeftFile >= 0 && forwardRank <= 7 && forwardRank >= 0) {	//må sjekke at ruten eksisterer
			if (this.getChessBoard().getPiece(forwardLeftFile, forwardRank) != null) { //må sjekke at det er en brikke der
				if (this.getChessBoard().getPiece(forwardLeftFile, forwardRank).getColor() == oppositeColor && this.getChessBoard().getPiece(forwardLeftFile, forwardRank) instanceof Pawn) { //må sjekke at brikken der har motsatt farge
					return true;
				}
			}
		}
		if (forwardRightFile <= 7 && forwardRank <= 7 && forwardRank >= 0) { //samme som over for høyre side diagonalt istedet
			if (this.getChessBoard().getPiece(forwardRightFile, forwardRank) != null) {
				if (this.getChessBoard().getPiece(forwardRightFile, forwardRank).getColor() == oppositeColor && this.getChessBoard().getPiece(forwardRightFile, forwardRank) instanceof Pawn) {
					return true;
				}
			}
		}
			//Look for rooks or queens or kings
		//right
		for (int i = 1; i + file <= 7; i++) {
			int currentFile = i + file;
			int currentRank = rank;
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {							
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Rook || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King) ) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			}
			 
		}
		//left
		for (int i = 1; -i + file >= 0; i++) {
			int currentFile = -i + file;
			int currentRank = rank;
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Rook || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			}
		}
		//up
		for (int i = 1; -i + rank >= 0; i++) {
			int currentFile = file;
			int currentRank = -i + rank;
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Rook || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			} 
		}
		//down
		for (int i = 1; i + rank <= 7; i++) {
			int currentFile = file;
			int currentRank = i + rank;
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Rook || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			} 
		}
			//Look for bishops or queens or kings
		//up-left
		for (int i = 1; -i + file >= 0 && -i + rank >= 0; i++) {
			int currentFile = -i + file; //-
			int currentRank = -i + rank; //-
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Bishop || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			}
		}
		//up-right
		for (int i = 1; i + file <= 7 && -i + rank >= 0; i++) {
			int currentFile = i + file; //+
			int currentRank = -i + rank; //-
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Bishop || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			}
		}
		//down-left
		for (int i = 1; -i + file >= 0 && i + rank <= 7; i++) {
			int currentFile = -i + file; //-
			int currentRank = i + rank; //+
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Bishop || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			}
		}
		//down-right
		for (int i = 1; i + file <= 7 && i + rank <= 7; i++) {
			int currentFile = i + file; //+
			int currentRank = i + rank; //+
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
			}
			else {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor() && (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Bishop || this.getChessBoard().getPiece(currentFile, currentRank) instanceof Queen || this.getChessBoard().getPiece(currentFile, currentRank) instanceof King)) { //if the pieces are different color, this piece can move there
					return true;
				}
				break;							//but then the direction movement ends
			}		
		}
			//Look for knights
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				int currentFile = file + i;
				int currentRank = rank + j;
				if (currentFile >= 0 && currentFile <= 7 && currentRank >= 0 && currentRank <= 7 && Math.abs(i) + Math.abs(j) == 3) { //tile 
					if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {
					}		
					else {
						if (this.getChessBoard().getPiece(currentFile, currentRank) instanceof Knight) {
							if (this.getChessBoard().getPiece(currentFile, currentRank).getColor() == this.oppositeColor) {
								return true;
							}	
						}
						break;
					}
				}
			}
		}
		return false;
	}

	public King(PieceColor color, ChessBoard chessBoard) {
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
	
	private void updateCheck() {
		if (checkForCheck(this.getFile(), this.getRank())) {
			this.inCheck = true;
		}
		else if (!checkForCheck(this.getFile(), this.getRank())) {
			this.inCheck = false;
		}
	}
	
	private void updateCheckmate() {
		if (checkForCheck(this.getFile(), this.getRank())) {
			
			//if setning som krever true fra checkForCheck på alle rutene rundt kongen som ikke er dekt av en egen brikke
			return;
		}
	}
	private void updateStalemate() {
		
	}
	
	//Under movePiece()
	private void castling() {
		return;
	}
	private void validateCastling() {
		return;
	}
	
	@Override
	public String toString() {
		return String.format("%s", King.class);
	}
	
}
