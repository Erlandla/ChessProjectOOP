package ChessProject;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

class SaveHandlerTest {
	
	private SaveHandler saveHandler = new SaveHandler();
	private ChessGame game;
	
	@BeforeEach
	void setUp() {
		game = new ChessGame();
	}

	@Test
	@DisplayName("Checks if created file exists")
	void saveTxtTest() {
		// Wipes the board and puts a pawn on 4,3
		wipeBoard(game);
		game.getChessBoard().setPiece(4, 3, new Pawn(PieceColor.black, game.getChessBoard()));
		try {
			saveHandler.saveTxt("saveTxtTest", game);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(new File(SaveHandler.getFilePathAsTxt("saveTxtTest")).exists());
		delTestFiles("saveTxtTest");
		assertFalse(new File(SaveHandler.getFilePathAsTxt("saveTxtTest")).exists());
	}
	
	@Test
	@DisplayName("Checks if created file has the same data on load")
	void loadTxtTest() {
		wipeBoard(game);
		game.getChessBoard().setPiece(4, 3, new Pawn(PieceColor.black, game.getChessBoard()));
		// Creates a save file for the test to load in later
		try {
			saveHandler.saveTxt("loadTxtTest", game);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// new game => resets board to original state
		game = new ChessGame();
		// Loads the recently saved file to check if the pawn is in the correct spot
		try {
			saveHandler.loadTxt("loadTxtTest");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(game.getChessBoard().getPiece(4, 3) instanceof Pawn);
		delTestFiles("loadTxtTest");
	}
	
	@Test
	@DisplayName("Checks if an exception is thrown when loading a file that doesn't exist")
	void loadFileThatDoesNotExist() {
		assertThrows(FileNotFoundException.class, () -> {
			saveHandler.loadTxt("hahaThisFileDoesntExists");
		});
	}
	
	@Test
	@DisplayName("Checks if overwriting a file works")
	void overwriteFileTest() {
		wipeBoard(game);
		game.getChessBoard().setPiece(4, 3, new Queen(PieceColor.white, game.getChessBoard()));
		try {
			saveHandler.saveTxt("overwriteTest", game);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		game.movePiece(4, 3, 4, 4);
		try {
			saveHandler.saveTxt("overwriteTest", game);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		game = new ChessGame();
		try {
			saveHandler.loadTxt("overwriteTest");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assertTrue(game.getChessBoard().getPiece(4, 4) instanceof Queen);
		delTestFiles("overwriteTest");
	}
	
	private void delTestFiles(String filename) {
		new File(SaveHandler.getFilePathAsTxt(filename)).delete();
	}
	private void wipeBoard(ChessGame game) {
		for (int y=0; y<8; y++) {
			for (int x=0; x<8; x++) {
				game.getChessBoard().setPiece(x, y, null);
			}
		}
	}
}
