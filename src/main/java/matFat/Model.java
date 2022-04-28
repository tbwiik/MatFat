package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import matFat.Objects.*;
import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalFileFormatException;
import matFat.exceptions.IllegalMeasurementException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalTagFormatException;
import matFat.filehandling.*;

public class Model {

    private List<Meal> allMeals = new ArrayList<>();

    public Model() throws IllegalFileFormatException {
        FileHandler fileHandler = new FileHandler();
        this.allMeals = fileHandler.readFromFile("test").getMealList();
    }

    public List<Meal> getAllMeals() {
        return new ArrayList<>(allMeals);
    }

    public Menu generateMenu(int numberOfMeals, Set<String> tags)
            throws NoSuchElementException, IllegalArgumentException {

        checkNum(numberOfMeals);

        List<Meal> validMeals = allMeals.stream().filter((meal) -> validateTags(tags, meal.getTags())).toList();
        List<Meal> mealList = new ArrayList<>();

        if (validMeals.isEmpty())
            throw new NoSuchElementException("No meal meets the required tags");

        while (mealList.size() < numberOfMeals) {
            mealList.add(getRandomMeal(validMeals));
        }

        return new Menu(mealList, tags);

    }

    /**
     * Checks if mealTags contains all specified userTags (or more)
     * 
     * @param userTags
     * @param mealTags
     * @return true if mealtags = usertags or more
     */
    private boolean validateTags(Set<String> userTags, Set<String> mealTags) {

        if (userTags.isEmpty())
            return true;

        // TODO fix to be exclusive
        for (String tag : userTags) {
            if (!mealTags.contains(tag))
                return false;
        }

        return true;
    }

    private void checkNum(int n) throws IllegalAmountException {
        if (n <= 0)
            throw new IllegalAmountException("Too low amount of meals");
        if (n > 100)
            throw new IllegalAmountException("Too high amount of meals");

    }

    private Meal getRandomMeal(List<Meal> allMeals) {

        Random rand = new Random();

        List<Meal> allMealsList = new ArrayList<>(allMeals); // XXX This one necessary?

        return allMealsList.get(rand.nextInt(allMealsList.size()));

    }

    public static Ingredient strToIng(String ingStr) throws IllegalNameFormatException, IllegalAmountException,
            IllegalMeasurementException, IllegalTagFormatException {
        String[] ingArgs = ingStr.strip().split(" ");
        return new Ingredient(ingArgs);
    }

    public static Set<String> strToStrSet(String str) {
        // TODO add more validation here?
        Set<String> tmpSet = new HashSet<>();

        if (!str.strip().isEmpty()) {
            String[] tmpArray = str.strip().split(",");
            for (String item : tmpArray) {
                tmpSet.add(item.strip());
            }
        }

        return tmpSet;
    }

    public static int strToInt(String str) throws IllegalArgumentException {
        try {
            return (int) Integer.parseInt(str.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Not valid number");
        }
    }

    public static char strToChar(String str) throws IllegalDifficultyException {
        char[] charArray = str.strip().toCharArray();
        if (charArray.length != 1)
            throw new IllegalDifficultyException("Not accepted character");
        return Character.toUpperCase(charArray[0]);
    }

    // TODO
    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setAlertType(alertType);
    }

    public static void main(String[] args) {
        Set<String> tmp = strToStrSet("lol, foo, bar");
        tmp.forEach(System.out::println);
    }
}
