package ChessProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class SaveHandler implements SaveHandlerInterface {

	public final static String SAVE_FOLDER = "src/main/java/ChessProject/saves/";
	
	@Override
	public void saveFile(String format, String filename, ChessGame game) throws FileNotFoundException {
		if (format == "txt") {
			saveTxt(filename, game);
		}
	}
	
	@Override
	public ChessGame loadFile(String format, String filename) throws FileNotFoundException {
		if (format == "txt") {
			ChessGame game = loadTxt(filename);
			return game;
		}
		else {
			return null;
		}
	}
	
	
	public void saveTxt(String filename, ChessGame game) throws FileNotFoundException {
		try (PrintWriter writer = new PrintWriter(getFilePathAsTxt(filename))){
			int emptyTilesOnRow = 0;
			for (int y=0; y<8; y++) {
				// Next line? Add a '/'
				if(y > 0)
					writer.print("/");
				for (int x=0; x<8; x++) {
					// End of the row and no piece? Write emptyTilesOnRow+1=8 and reset it to 0
					if (x == 7 && (pieceCheck(x, y, game) == null)) {
						writer.print(emptyTilesOnRow+1);
						emptyTilesOnRow = 0;
					// Piece and no there has been empty tiles previously? Write the amount of empty tiles, then the chess piece
					} else if (!(pieceCheck(x, y, game) == null) && emptyTilesOnRow != 0) {
						writer.print((emptyTilesOnRow) + pieceCheck(x, y, game));
						emptyTilesOnRow = 0;
					// Not a piece? Increment empty rows
					} else if ((pieceCheck(x, y, game) == null)) 
						emptyTilesOnRow++;
					else 
					// Write the piece
						writer.print(pieceCheck(x, y, game));
				}
			}
			// At the end, write the turnTimer
			writer.print(" " + game.getTurnTimer());
		} 
		System.out.println("Save successful");
	}

	public ChessGame loadTxt(String filename) throws FileNotFoundException {
		String FENish = "";
		ChessGame game = new ChessGame();
		try (Scanner scanner = new Scanner(new File(getFilePathAsTxt(filename)))) {
			// Wipes the board
			for (int y=0; y<8; y++) {
				for (int x=0; x<8; x++) {
					game.getChessBoard().setPiece(x, y, null);
				}
			}
			while (scanner.hasNextLine()) {	
				FENish += scanner.nextLine();
			}
			
			// Turns every row into its own string. turnTimer is parsed to an int at the end
			String row1 = FENish.substring(0, FENish.indexOf('/'));
			String rest1 = FENish.substring(FENish.indexOf('/')+1);
			String row2 = rest1.substring(0, rest1.indexOf('/'));
			String rest2 = rest1.substring(rest1.indexOf('/')+1);
			String row3 = rest2.substring(0, rest2.indexOf('/'));
			String rest3 = rest2.substring(rest2.indexOf('/')+1);
			String row4 = rest3.substring(0, rest3.indexOf('/'));
			String rest4 = rest3.substring(rest3.indexOf('/')+1);
			String row5 = rest4.substring(0, rest4.indexOf('/'));
			String rest5 = rest4.substring(rest4.indexOf('/')+1);
			String row6 = rest5.substring(0, rest5.indexOf('/'));
			String rest6 = rest5.substring(rest5.indexOf('/')+1);
			String row7 = rest6.substring(0, rest6.indexOf('/'));
			String rest7 = rest6.substring(rest6.indexOf('/')+1);
			String row8 = rest7.substring(0, rest7.indexOf(' '));
			int turnTimer = Integer.parseInt(rest7.substring(rest7.indexOf(' ')+1));
			
			String[] segments = {row1, row2, row3, row4, row5, row6, row7, row8};

			// Length > 8 in any row = not right
			for (String string : segments) {
				if (string.length() > 8)
					throw new IllegalArgumentException("Characters in the FEN string will be out of bounds");
			}
			
			for (int j = 0; j < 8; j++) {
				int charPointer = 0;
				for (int i = 0; i < 8; i++) {
					char c = segments[j].charAt(charPointer);
					charPointer++;
					if (c == '/') {
						break;
					} else if (c == '1') {
						i += 0;
					} else if (c == '2') {
						i += 1;
					} else if (c == '3') {
						i += 2;
					} else if (c == '4') {
						i += 3;
					} else if (c == '5') {
						i += 4;
					} else if (c == '6') {
						i += 5;
					} else if (c == '7') {
						i += 6;
					} else if (c == '8') {
						i += 7;
					} else if (c == 'R') {
						game.getChessBoard().setPiece(i, j, new Rook(PieceColor.white, game.getChessBoard()));
					} else if (c == 'r') {
						game.getChessBoard().setPiece(i, j, new Rook(PieceColor.black, game.getChessBoard()));
					} else if (c == 'B') {
						game.getChessBoard().setPiece(i, j, new Bishop(PieceColor.white, game.getChessBoard()));
					} else if (c == 'b') {
						game.getChessBoard().setPiece(i, j, new Bishop(PieceColor.black, game.getChessBoard()));
					} else if (c == 'N') {
						game.getChessBoard().setPiece(i, j, new Knight(PieceColor.white, game.getChessBoard()));
					} else if (c == 'n') {
						game.getChessBoard().setPiece(i, j, new Knight(PieceColor.black, game.getChessBoard()));
					} else if (c == 'P') {
						game.getChessBoard().setPiece(i, j, new Pawn(PieceColor.white, game.getChessBoard()));
					} else if (c == 'p') {
						game.getChessBoard().setPiece(i, j, new Pawn(PieceColor.black, game.getChessBoard()));
					} else if (c == 'K') {
						game.getChessBoard().setPiece(i, j, new King(PieceColor.white, game.getChessBoard()));
					} else if (c == 'k') {
						game.getChessBoard().setPiece(i, j, new King(PieceColor.black, game.getChessBoard()));
					} else if (c == 'Q') {
						game.getChessBoard().setPiece(i, j, new Queen(PieceColor.white, game.getChessBoard()));
					} else if (c == 'q') {
						game.getChessBoard().setPiece(i, j, new Queen(PieceColor.black, game.getChessBoard()));
					}
					else {
						throw new IllegalArgumentException("Load String contained illegal Characters");
					}
				}
			}
			game.setTurnTimer(turnTimer);
			return game;
		}
		catch(IllegalArgumentException iae) {
			System.out.println("Could not load properly, file might be corrupted");
			throw new FileNotFoundException("Could not load properly, file might be corrupted");
		}
		catch(Exception e) {
			System.out.println("Unexpected Error occured, could not load");
			throw new FileNotFoundException("Unexpected Error occured, could not load");
		}
		
		
		
	}
	
	public static String getFilePathAsTxt(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}
	
	private String pieceCheck(int x, int y, ChessGame game) {
		if (game.getChessBoard().getPiece(x, y) instanceof Rook) {
			if (game.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
				return "R";
			return "r";
		} else if (game.getChessBoard().getPiece(x, y) instanceof Knight) {
			if (game.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
				return "N";
			return "n";
		} else if (game.getChessBoard().getPiece(x, y) instanceof Bishop) {
			if (game.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
				return "B";
			return "b";
		} else if (game.getChessBoard().getPiece(x, y) instanceof Queen) {
			if (game.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
				return "Q";
			return "q";
		} else if (game.getChessBoard().getPiece(x, y) instanceof King) {
			if (game.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
				return "K";
			return "k";
		} else if (game.getChessBoard().getPiece(x, y) instanceof Pawn) {
			if (game.getChessBoard().getPiece(x, y).getColor().equals(PieceColor.white))
				return "P";
			return "p";
		}
		return null;
	}
	
	public static void main(String[] args) {
//		ChessGame game = new ChessGame();
//		SaveHandler test = new SaveHandler();
//		test.saveTxt("testSave", game);
//		
//		ChessGame testLoadGame = test.loadTxt("testLoad");
//		System.out.println(testLoadGame);
//		System.out.println(testLoadGame.getChessBoard().getPiece(1, 0) instanceof Knight);
//		System.out.println(testLoadGame.getTurnTimer());
//		
//		System.out.println();
//		
//		ChessGame testLoadGameWDefaultState = test.loadTxt("testSave");
//		System.out.println(testLoadGameWDefaultState);
//		System.out.println(testLoadGameWDefaultState.getChessBoard().getPiece(0, 0) instanceof Rook);
//		System.out.println(testLoadGame.getTurnTimer());
	}
}
