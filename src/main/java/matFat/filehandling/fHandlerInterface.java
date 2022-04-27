package matFat.filehandling;

import matFat.Objects.Menu;
import matFat.exceptions.IllegalFileFormatException;

public interface fHandlerInterface {

    public Menu readFromFile(String filename) throws IllegalArgumentException, IllegalFileFormatException;

    public void writeToFile(Menu menu, String filename);

}
