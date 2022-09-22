package ChessProject;

import java.util.ArrayList;

public class Rook extends ChessPiece{
	private boolean hasMoved = false;
	private ArrayList<String> legalMoves = new ArrayList<>();
	
	private void updateLegalMoves() {
		if (legalMoves != null)
			legalMoves.clear();	//fjerner forrige liste
		//right
		for (int i = 1; i + this.getFile() <= 7; i++) {
			int currentFile = i + this.getFile();
			int currentRank = this.getRank();
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
				legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor()) { //if the pieces are different color, this piece can move there
					legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
				}
				break;							//but then the direction movement ends
			}
			 
		}
		//left
		for (int i = 1; -i + this.getFile() >= 0; i++) {
			int currentFile = -i + this.getFile();
			int currentRank = this.getRank();
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
				legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor()) { //if the pieces are different color, this piece can move there
					legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
				}
				break;							//but then the direction movement ends
			}
		}
		//up
		for (int i = 1; -i + this.getRank() >= 0; i++) {
			int currentFile = this.getFile();
			int currentRank = -i + this.getRank();
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
				legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor()) { //if the pieces are different color, this piece can move there
					legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
				}
				break;							//but then the direction movement ends
			} 
		}
		//down
		for (int i = 1; i + this.getRank() <= 7; i++) {
			int currentFile = this.getFile();
			int currentRank = i + this.getRank();
			
			if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {				
				legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
			}
			else  {
				if (this.getColor() != this.getChessBoard().getPiece(currentFile, currentRank).getColor()) { //if the pieces are different color, this piece can move there
					legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
				}
				break;							//but then the direction movement ends
			} 
		}
	}
	
	
	public ArrayList<String> getLegalMoves() {
		updateLegalMoves();
		return legalMoves;
	}
	
	public Rook(PieceColor color, ChessBoard chessBoard) {
		super(color, chessBoard);
	}

	@Override
	public String toString() {
		return String.format("%s", Rook.class);
	}
	
}
