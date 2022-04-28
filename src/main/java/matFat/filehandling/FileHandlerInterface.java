package matFat.filehandling;

import matFat.Objects.Meal;

public interface FileHandlerInterface {

    // TODO reconsider exceptions
    public Meal readMealFromFile(String filename) throws IllegalArgumentException;

    public void writeMealToFile(Meal menu, String filename);

}
