package matFat.filehandling;

import java.io.FileNotFoundException;
import java.util.List;

import matFat.Objects.Meal;
import matFat.Objects.Menu;
import matFat.exceptions.IllegalFileFormatException;

public interface FileHandlerInterface {

    public Menu readMenuFromFile(String filename) throws FileNotFoundException, IllegalFileFormatException;

    public List<Meal> readMealsFromFile(String filename) throws FileNotFoundException, IllegalFileFormatException;

    public void writeMealsToFile(List<Meal> meals, String filename);

    public void writeMenuToFile(Menu menu, String filename);

}
