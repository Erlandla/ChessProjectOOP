package ChessProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class GameController {
	
	private ChessGame game;
	private SaveHandler saveHandler = new SaveHandler();
	
	@FXML
	GridPane chessBoard;
	@FXML
	Button restartButton;
	@FXML
	Button saveButton;
	@FXML
	TextField filename;
	@FXML
	Button loadButton;
	@FXML
	TextField errorTextField;
	@FXML
	TextField victoryScreen;
	@FXML 
	Pane promoteWindow;
	
	public GameController() {
		game = new ChessGame();
	}
	
	public ChessGame getGame() {
		return game;
	}
	
	public void initialize() {
		setStartBoard();
		updateBoard();
	}
	
	public void setStartBoard() {
		game = new ChessGame();
	}
	
	private String getPieceImage(int y, int x, ChessGame game) {
		String result = "";
		if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.white))
			result+="W";
		else
			result+="B";
		if (game.getChessBoard().getPiece(y, x) instanceof Rook)
			result+="Rook.png";
		else if (game.getChessBoard().getPiece(y, x) instanceof Bishop)
			result+="Bishop.png";
		else if (game.getChessBoard().getPiece(y, x) instanceof Knight)
			result+="Knight.png";
		else if (game.getChessBoard().getPiece(y, x) instanceof Queen)
			result+="Queen.png";
		else if (game.getChessBoard().getPiece(y, x) instanceof King)
			result+="King.png";
		else if (game.getChessBoard().getPiece(y, x) instanceof Pawn)
			result+="Pawn.png";
		return result;
	}
	
	@FXML
	public void updateBoard() {	
		chessBoard.getChildren().clear();
		chessBoard.setRotate(0);
		chessBoard.setDisable(false);
		errorTextField.clear();
		errorTextField.setVisible(false);
		promoteWindow.setVisible(false);
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				Pane tile = new Pane();
				if ((x%2 == 0 && y%2 == 1) || (x%2 == 1 && y%2 == 0)) {
					tile.setStyle("-fx-background-color: #654321; -fx-border-color: black; -fx-border-width: 1px;");
				}
				chessBoard.add(tile, y, x);
			}
		}
		
		if (game.getTurnTimer() % 2 == 0)
			chessBoard.setRotate(180);
		else if (game.getTurnTimer() % 2 == 1 && game.getTurnTimer() > 1)
			chessBoard.setRotate(0);
		
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if (game.getChessBoard().getPiece(y, x) != null) {
					
					if (game.getChessBoard().getPiece(y, x).getPromotable()) {
						promoteWindow.setVisible(true);
						chessBoard.setDisable(true);
					}
					
					String piece = getPieceImage(y,x,game);
					Image image;
					try {
						image = new Image(new FileInputStream(new File("src/main/java/ChessProject/img/" + "/" + piece).getAbsolutePath()));
						
						ImageView img = new ImageView(image);
						img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
							errorTextField.setVisible(false);
							int rank = GridPane.getRowIndex(img);
							int file = GridPane.getColumnIndex(img);
							pieceSelected(file, rank);
							event.consume();
						});
						
						if (game.getTurnTimer() % 2 == 0)
							img.setRotate(180);
						else if (game.getTurnTimer() % 2 == 1 && game.getTurnTimer() > 1)
							img.setRotate(0);
						
						chessBoard.add(img, y, x);
					} catch (FileNotFoundException e) {
						errorTextField.setText("Find not found: " + piece);
					}
				}
			}
		}
	}
	
	private void pieceSelected(int file, int rank) {
		// turnTimer even => whites can't move. turnTimer odd => blacks can't move.
		if (game.getChessBoard().getPiece(file, rank).getColor().equals(PieceColor.white) && game.getTurnTimer() % 2 == 0
				|| game.getChessBoard().getPiece(file, rank).getColor().equals(PieceColor.black) && game.getTurnTimer() % 2 == 1) {
			errorTextField.setText("Cannot select this piece");
			errorTextField.setVisible(true);
			return;
		}
		
		ArrayList<String> legalMoves = game.getChessBoard().getPiece(file, rank).getLegalMoves();
		
		// Marks selected tile as green, and can click on it again to cancel the selection
		Pane selectedTile = new Pane();
		selectedTile.setStyle("-fx-background-color: #60da31; -fx-opacity: 50%");
		selectedTile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			updateBoard();
			return;
		});
		chessBoard.add(selectedTile, file, rank);
		
		// Goes through legalMoves and "marks" the tiles that are legal. These tiles gets removed every time the board updates
		for (int i = 0; i < legalMoves.size(); i++) {
			
			String str= legalMoves.get(i);
			int tileFile = Integer.parseInt(str.substring(0, 1));
			int tileRank = Integer.parseInt(str.substring(1, 2));
			
			Pane tile = new Pane();
			tile.setStyle("-fx-background-color: #ffff6e; -fx-border-color: black; -fx-border-width: 0.5px; -fx-opacity: 50%");

			// Enemy piece? Tile turns red instead of yellow
			if (game.getChessBoard().getPiece(tileFile, tileRank) != null && 
				!game.getChessBoard().getPiece(tileFile, tileRank).getColor().equals(game.getChessBoard().getPiece(file, rank).getColor()))
				tile.setStyle("-fx-background-color: #ff1a1a; -fx-border-color: black; -fx-border-width: 0.5px; -fx-opacity: 50%");
			
			tile.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
				handleMove(file, rank, tileFile, tileRank);
				event.consume();
			});
			
			chessBoard.add(tile, tileFile, tileRank);
		}
	}
	
	// aaaaaaaaaaaa
	private void handleMove(int file, int rank, int endFile, int endRank) {
		game.movePiece(file, rank, endFile, endRank);
		updateBoard();
		
		
		
		if (game.getIsGameOver()) {
			if (game.getTurnTimer() % 2 == 0)
				victoryScreen.setText("Black wins!");
			else 
				victoryScreen.setText("White wins!");
			chessBoard.setDisable(true);
			victoryScreen.setVisible(true);
		}
	}
	
	@FXML
	public void restartGame() {
		initialize();
		victoryScreen.setVisible(false);
		chessBoard.setDisable(false);
	}
	
	@FXML
	public void handleSave() {
		try {
			saveHandler.saveFile("txt", getFilename(), game);
		} catch (FileNotFoundException e) {
			errorTextField.setVisible(true);
			errorTextField.setText("Could not save file " + getFilename());
		}
		errorTextField.setVisible(true);
		errorTextField.setText("Save successful for filename " + getFilename());
	}
	
	@FXML
	public void handleLoad() {
		victoryScreen.setVisible(false);
		chessBoard.setDisable(false);
		if (this.filename.getText() == "") {
			errorTextField.setVisible(true);
			errorTextField.setText("No file to load");
			return;
		}
		try {
			saveHandler.saveFile("txt", "thisWasOurBoardBeforeEverythingWentWrong", game);
			game = saveHandler.loadTxt(getFilename());
			updateBoard();
			System.out.println("Load successful");
		} catch (FileNotFoundException e) {
			errorTextField.setVisible(true);
			errorTextField.setText("Could not load file " + getFilename());
			try {
				game = saveHandler.loadFile("txt", "thisWasOurBoardBeforeEverythingWentWrong");
				updateBoard();
				System.out.println("Had to revert to previous board");
			} catch (FileNotFoundException e1) {
				errorTextField.setVisible(true);
				errorTextField.setText("For some reason our backup file went missing");
			}
		}
	}
	
	@FXML
	public void handlePromoteQueen() {
		for (int y=0; y<8; y++) {
			for (int x=0; x<8; x++) {
				if (game.getChessBoard().getPiece(y, x) != null) {
					if (game.getChessBoard().getPiece(y, x) instanceof Pawn) {
						if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.white) && game.getChessBoard().getPiece(y, x).getRank() == 0) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("queen");
						} else if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.black) && game.getChessBoard().getPiece(y, x).getRank() == 7) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("queen");
						}
					}
				}
			}
		}
		updateBoard();
	}
	@FXML
	public void handlePromoteKnight() {
		for (int y=0; y<8; y++) {
			for (int x=0; x<8; x++) {
				if (game.getChessBoard().getPiece(y, x) != null) {
					if (game.getChessBoard().getPiece(y, x) instanceof Pawn) {
						if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.white) && game.getChessBoard().getPiece(y, x).getRank() == 0) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("knight");
						} else if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.black) && game.getChessBoard().getPiece(y, x).getRank() == 7) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("knight");
						}
					}
				}
			}
		}
		updateBoard();
	}
	@FXML
	public void handlePromoteRook() {
		for (int y=0; y<8; y++) {
			for (int x=0; x<8; x++) {
				if (game.getChessBoard().getPiece(y, x) != null) {
					if (game.getChessBoard().getPiece(y, x) instanceof Pawn) {
						if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.white) && game.getChessBoard().getPiece(y, x).getRank() == 0) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("rook");
						} else if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.black) && game.getChessBoard().getPiece(y, x).getRank() == 7) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("rook");
						}
					}
				}
			}
		}
		updateBoard();
	}
	@FXML
	public void handlePromoteBishop() {
		for (int y=0; y<8; y++) {
			for (int x=0; x<8; x++) {
				if (game.getChessBoard().getPiece(y, x) != null) {
					if (game.getChessBoard().getPiece(y, x) instanceof Pawn) {
						if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.white) && game.getChessBoard().getPiece(y, x).getRank() == 0) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("bishop");
						} else if (game.getChessBoard().getPiece(y, x).getColor().equals(PieceColor.black) && game.getChessBoard().getPiece(y, x).getRank() == 7) {
							((Pawn) game.getChessBoard().getPiece(y, x)).promote("bishop");
						}
					}
				}	
			}
		}
		updateBoard();
	}
	
	private String getFilename() {
		String filename = this.filename.getText();
		if (filename.isEmpty())
			filename = "testSave";
		return filename;
	}
}
