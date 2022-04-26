package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

public class Model {

    List<Meal> allMeals = new ArrayList<>();
    Menu menu = new Menu();

    public Model(List<Meal> allMeals) {
        this.allMeals = allMeals;
    }

    // XXX Unsure about innkapsling in whole file
    private void generateMenu(int numberOfMeals, Set<String> tags) throws NoSuchElementException {

        List<Meal> validMeals = allMeals.stream().filter((meal) -> validateTags(tags, meal.getTags())).toList();

        if (validMeals.isEmpty())
            throw new NoSuchElementException("No meal meets the required tags");

        while (menu.getNumberOfMeals() < numberOfMeals) {
            menu.addMeal(getRandomMeal(validMeals));
        }

        menu.addTags(tags);

    }

    // TODO redefine this too only have similar? or userTags more? or mealTags more?
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

        for (String tag : userTags) {
            if (!mealTags.contains(tag))
                return false;
        }
        return true;
    }

    private Meal getRandomMeal(List<Meal> allMeals) {

        Random rand = new Random();

        List<Meal> allMealsList = new ArrayList<>(allMeals); // XXX This one necessary?

        return allMealsList.get(rand.nextInt(allMealsList.size()));

    }

    private Menu getMenu() {
        return menu;
    }

    public static void main(String[] args) {
        FileHandler fhandler = new FileHandler();
        List<Meal> meals = fhandler.readFromFile("").getMealList();
        Model model = new Model(meals);
        Set<String> tags = new HashSet<>();
        model.generateMenu(4, tags);
        System.out.println(model.getMenu());
    }
}
