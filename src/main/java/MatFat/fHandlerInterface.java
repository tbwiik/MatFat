package matFat;

import java.util.HashMap;

public interface fHandlerInterface {

    public HashMap<Integer, Meal> readFromFile(String filename);

    public void writeToFile(HashMap<Integer, Meal> meals, String filename);

}
