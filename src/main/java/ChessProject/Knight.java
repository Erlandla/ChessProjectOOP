package ChessProject;

import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {
	private ArrayList<String> legalMoves = new ArrayList<>();
	
	public ArrayList<String> getLegalMoves() {
		updateLegalMoves();
		return legalMoves;
	}
	
	private void updateLegalMoves() {
		if (legalMoves != null)
			legalMoves.clear();	//fjerner forrige liste
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				int currentFile = this.getFile() + i;
				int currentRank = this.getRank() + j;
				if (currentFile >= 0 && currentFile <= 7 && currentRank >= 0 && currentRank <= 7 && Math.abs(i) + Math.abs(j) == 3) { //tile 
					if (this.getChessBoard().getPiece(currentFile, currentRank) == null) {
						this.legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
					}
							
					else if (this.getChessBoard().getPiece(currentFile, currentRank) != null) {
						if (this.getChessBoard().getPiece(currentFile, currentRank).getColor() != this.getColor()) {
							this.legalMoves.add(0, new String(Integer.toString(currentFile) + Integer.toString(currentRank)));
							
						}
					}
				}
			}
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s", Knight.class);
	}
	
	
	public Knight(PieceColor color, ChessBoard chessBoard) {
		super(color, chessBoard);
	}
}
