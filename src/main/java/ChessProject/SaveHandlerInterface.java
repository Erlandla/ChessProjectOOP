package ChessProject;

import java.io.FileNotFoundException;

public interface SaveHandlerInterface {
	
	public void saveFile(String format, String filename, ChessGame game) throws FileNotFoundException;
	
	public ChessGame loadFile(String format, String filename) throws FileNotFoundException;
	
}
