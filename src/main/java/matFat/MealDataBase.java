package matFat;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import matFat.Objects.Meal;
import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalFileFormatException;
import matFat.filehandling.ManageData;

public class MealDataBase {

    ManageData manageData = new ManageData();
    private List<Meal> meals = new ArrayList<>();

    String defaultFilename = "mealDataBase"; // Default filepath for dataBase

    public MealDataBase() throws IllegalFileFormatException, FileNotFoundException {
        meals = manageData.readMealsFromFile(defaultFilename);
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        manageData.writeMealsToFile(meals, defaultFilename);
    }

    /**
     * Checks if mealTags contains all specified userTags (or more)
     *
     * @param userTags
     * @param mealTags
     * @return false if mealtags doesnt contain a user-specified tag
     */
    private boolean validateTags(Set<String> userTags, Set<String> mealTags) {

        if (userTags.isEmpty())
            return true;

        for (String tag : userTags) {
            if (!mealTags.contains(tag))
                return false;
        }

        return true;

    }

    public Meal getRandomMeal() {

        Random rand = new Random();

        return meals.get(rand.nextInt(meals.size()));

    }

    // TODO implement predicate instead of userTags?
    private List<Meal> validMeals(Set<String> userTags) throws NoSuchElementException {

        List<Meal> validMeals = meals.stream().filter((meal) -> validateTags(userTags, meal.getTags())).toList();

        if (validMeals.isEmpty())
            throw new NoSuchElementException("No meal meets the required tags");

        return new ArrayList<>(validMeals);
    }

    private void checkNum(int n) throws IllegalAmountException {
        if (n <= 0)
            throw new IllegalAmountException("Too low amount of meals");
        if (n > 100)
            throw new IllegalAmountException("Too high amount of meals");

    }

    public Meal getRandomMeal(Set<String> userTags) throws NoSuchElementException {

        Random rand = new Random();
        Meal meal = validMeals(userTags).get(rand.nextInt(meals.size()));

        return meal;
    }

    public List<Meal> getRandomMeals(Set<String> userTags, int amount) throws NoSuchElementException {

        List<Meal> randomMeals = new ArrayList<>();

        checkNum(amount);

        while (randomMeals.size() < amount)
            randomMeals.add(getRandomMeal(userTags));

        return randomMeals;

    }

}
