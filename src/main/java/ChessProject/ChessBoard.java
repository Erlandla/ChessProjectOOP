package ChessProject;

public class ChessBoard { //is supposed to hold a value for the actual square, as well as store the ID of the specific piece that is there
	private static ChessPiece[][] Square;
	
	public ChessBoard() {
		Square = new ChessPiece[8][8];
	
	}
	public ChessPiece getPiece(int file, int rank) {
		if (file >= 8 || file < 0 || rank >= 8 || rank < 0)
			throw new IllegalArgumentException("Coordinates chosen is outside the chessboard");
		return ChessBoard.Square[file][rank];
	}
	
//	public ChessPiece getPiece(String position) {
//		validatePosition(position);
//		int file = position.charAt(0) - 'a';
//		int rank = position.charAt(1) - '0' -1;
//		return ChessBoard.Square[file][rank];
//	}
	public void setPiece(int file, int rank, ChessPiece piece) {
		ChessBoard.Square[file][rank] = piece;
		if (piece != null) {
			informChessPiece(file, rank, piece);
		}
	}
//	public void setPiece(String position, ChessPiece piece) {
//		validatePosition(position);
//		int file = position.charAt(0) - 'a';
//		int rank = position.charAt(1) - '0' -1;
//		ChessBoard.Square[file][rank] = piece;
//		if (piece != null) {
//			informChessPiece(file, rank, piece);
//		}
//	}
//	public void removePiece(String position) {
//		validatePosition(position);
//		int file = position.charAt(0) - 'a';
//		int rank = position.charAt(1) - '0' -1;
//		ChessBoard.Square[file][rank] = null;
//	}
//	private void validatePosition(String position) {
//		if (position.length() == 2) {
//			int file = position.charAt(0) - 'a';
//			int rank = position.charAt(1) - '0' -1;
//		
//			if (file >= 0 && file <= 7 && rank >= 0 && rank <= 7) {
//					return;
//				}
//		}
//		else throw new IllegalArgumentException("Invalid position, must be on the format FileRank, File = a-h, Rank = 1-8");
//	}
	
	private void informChessPiece(int file, int rank, ChessPiece piece){
		piece.updateInternalPosition(file, rank);
	}
	
	public static void main(String[] args) {

	}
	public void removePiece(int file, int rank) {
		if (file >= 8 || file < 0 || rank >= 8 || rank < 0)
			throw new IllegalArgumentException("Coordinates chosen is outside the chessboard");
		ChessBoard.Square[file][rank] = null;
		
	}
}
