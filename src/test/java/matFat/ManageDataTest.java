package matFat;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import matFat.Objects.Meal;
import matFat.Objects.Menu;
import matFat.exceptions.IllegalFileFormatException;
import matFat.filehandling.ManageData;

public class ManageDataTest {

    ManageData manageData = new ManageData();
    Menu menu;
    List<Meal> mealList = new ArrayList<>();
    String defaultDataBaseName = "mealDataBase";

    @Test
    void testReadMealsFromFile() throws FileNotFoundException, IllegalFileFormatException {

        meal

        mealList = manageData.readMealsFromFile(defaultDataBaseName);

    }

    @Test
    void testReadMenuFromFile() throws FileNotFoundException, IllegalFileFormatException {

        menu = manageData.readMenuFromFile(defaultDataBaseName);

    }

    @Test
    void testWriteMealsToFile() throws FileNotFoundException, IllegalFileFormatException {

        manageData.writeMealsToFile(mealList, defaultDataBaseName);
        Assertions.assertEquals(mealList, manageData.readMealsFromFile(defaultDataBaseName));

    }

    @Test
    void testWriteMenuToFile() throws FileNotFoundException, IllegalFileFormatException {

        manageData.writeMenuToFile(menu, defaultDataBaseName);
        Assertions.assertEquals(mealList, manageData.readMenuFromFile(defaultDataBaseName));

    }
}
