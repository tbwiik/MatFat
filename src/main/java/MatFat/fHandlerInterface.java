package matFat;

import java.util.Set;

public interface fHandlerInterface {

    public Set<Meal> readFromFile(String filename);

    public void writeToFile(Set<Meal> meals, String filename);

}
