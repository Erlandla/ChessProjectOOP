package ChessProject;

import java.util.ArrayList;

public abstract class ChessPiece {
//	private int[][] position;
	private int file;
	private int rank;
	private PieceColor color;
	private boolean killed = false;
	private ChessBoard chessBoard;
	private boolean promotable = false;
	
	public ChessPiece(PieceColor color, ChessBoard chessBoard) {
		this.color = color;
		this.chessBoard = chessBoard;
	}
	
	public void setChessBoard(ChessBoard chessBoard) {
		this.chessBoard = chessBoard;
	}
	
	public ChessBoard getChessBoard() {
		return chessBoard;
	}
	
	public PieceColor getColor() {
		return this.color;
	}
	
//	public int[][] getPosition(){
//		return position;
//	}
	public int getFile() {
		return file;
	}
	public int getRank() {
		return rank;
	}
	
	public boolean getPromotable() {
		return promotable;
	}
	
	public boolean isKilled() {
		return this.killed;
	}

	public void setKilled(boolean killed) {
		this.killed = killed;
	}
		
	@Override
	public String toString() {
		return String.format("%s", ChessPiece.class);
	}
	
	
	public static void main(String[] args) {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(PieceColor.black, board);
		ArrayList<String> legalMoves = pawn.getLegalMoves();
		System.out.println(legalMoves);
	}

	public void updateInternalPosition(int file, int rank) {
//        int[][] tempPosition = new int[file][rank];
//        this.position = tempPosition;
        this.file = file;
        this.rank = rank;
        if (this instanceof Pawn) {
            if (this.getColor().equals(PieceColor.white) && this.rank == 0) {
                promotable = true;
            }
            else if (this.getColor().equals(PieceColor.black) && this.rank == 7) {
                promotable = true;
            }
            else
            	promotable = false;
        }
    }


	protected abstract ArrayList<String> getLegalMoves(); //All strings are on a format "intFile+intRank" for example "01" or "74"
}
