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

    private ManageData manageData = new ManageData();
    private List<Meal> meals = new ArrayList<>();
    private static String defaultFilename = "mealDataBase"; // Default filepath for dataBase

    /**
     * Create database with all meals already written to file
     * 
     * @throws IllegalFileFormatException
     * @throws FileNotFoundException
     */
    public MealDataBase() throws IllegalFileFormatException, FileNotFoundException {
        meals = manageData.readMealsFromFile(defaultFilename);
    }

    /**
     * @return meals from database
     */
    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }

    /**
     * Add meal to database
     * 
     * @param meal
     */
    public void addMeal(Meal meal) {
        meals.add(meal);
        manageData.writeMealsToFile(meals, defaultFilename);
    }

    /**
     * Removes meal from database
     * 
     * @param meal
     */
    public void removeMeal(Meal meal) {
        meals.remove(meal);
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

    /**
     * @return randomMeal from database
     */
    public Meal getRandomMeal() {

        Random rand = new Random();

        return meals.get(rand.nextInt(meals.size()));

    }

    /**
     * Return meals based on filter of meals in database with
     * {@linkplain #validateTags(Set, Set)}
     * 
     * @param userTags
     * @return
     * @throws NoSuchElementException if no element meets required tags
     */
    private List<Meal> validMeals(Set<String> userTags) throws NoSuchElementException {

        List<Meal> validMeals = meals.stream().filter((meal) -> validateTags(userTags, meal.getTags())).toList();

        if (validMeals.isEmpty())
            throw new NoSuchElementException("No meal meets the required tags");

        return new ArrayList<>(validMeals);
    }

    /**
     * Check whether amount of meals wanted is valid
     * 
     * @param n number of meals
     * @throws IllegalAmountException
     */
    private void checkNum(int n) throws IllegalAmountException {
        if (n <= 0)
            throw new IllegalAmountException("Too low amount of meals");
        if (n > 100)
            throw new IllegalAmountException("Too high amount of meals");

    }

    /**
     * Return random meal based on the {@linkplain #validMeals(Set)} method
     * 
     * @param userTags
     * @return {@linkplain Meal}
     * @throws NoSuchElementException
     */
    public Meal getRandomMeal(Set<String> userTags) throws NoSuchElementException {

        Random rand = new Random();
        List<Meal> validMeals = validMeals(userTags);
        if (validMeals.size() == 1)
            return validMeals.get(0);
        Meal meal = validMeals.get(rand.nextInt(validMeals.size() - 1));

        return meal;
    }

    /**
     * Get random meals using {@linkplain #getRandomMeal(Set)}
     * 
     * @param userTags
     * @param amount   of meals
     * @return List with meals
     * @throws NoSuchElementException if no meals meet the required tags
     */
    public List<Meal> getRandomMeals(Set<String> userTags, int amount) throws NoSuchElementException {

        List<Meal> randomMeals = new ArrayList<>();

        checkNum(amount);

        while (randomMeals.size() < amount)
            randomMeals.add(getRandomMeal(userTags));

        return randomMeals;

    }

}
