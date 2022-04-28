package matFat.Objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matFat.exceptions.IllegalTagFormatException;

public class Menu extends Tagged {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private IngredientContainer ingredientContainer = new IngredientContainer();
    // Inherits tagbox from super

    /**
     * Create a menu with meals and userdefined tags
     * 
     * @param mealList
     * @param tags
     * @throws IllegalArgumentException  if trying to create a menu with no meals
     * @throws IllegalTagFormatException
     */
    public Menu(List<Meal> mealList, Set<String> tags) throws IllegalArgumentException, IllegalTagFormatException {

        setMealList(mealList);
        initializeIngredientContainer();
        tagBox = new TagBox(tags);
        numberOfMeals = mealList.size();

    }

    /**
     * Sets meal-list in menu
     * 
     * @param mealList
     * @throws IllegalArgumentException if trying to create empty meal-list
     */
    private void setMealList(List<Meal> mealList) throws IllegalArgumentException {

        if (mealList.isEmpty())
            throw new IllegalArgumentException("Can't create menu with no meals");

        this.mealList = new ArrayList<>(mealList);
    }

    /**
     * Constructs ingredient-container based on all ingredients in meal-list
     */
    private void initializeIngredientContainer() {

        mealList.forEach((meal) -> {
            ingredientContainer.addIngredients(meal.getIngredients());
        });

    }

    /**
     * @return copy of meal-list
     */
    public List<Meal> getMealList() {
        return new ArrayList<>(mealList);
    }

    /**
     * @return number of meals in menu
     */
    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    /**
     * @return user-defined tags and tags from ingredient-container
     */
    public Set<String> getTags() {
        Set<String> allTags = new HashSet<>();
        allTags.addAll(ingredientContainer.getTags()); // Tags from container
        allTags.addAll(tagBox.getTags()); // User defined tags for menu
        return allTags;
    }

    /**
     * @return ingredients in ingredient-container
     */
    public List<Ingredient> getIngredients() {
        return ingredientContainer.getIngredients();
    }

    /**
     * Add meal to menu
     * 
     * @param meal
     */
    public void addMeal(Meal meal) {
        mealList.add(meal);
        ingredientContainer.addIngredients(meal.getIngredients());
        numberOfMeals++;
    }

    /**
     * Removes meal from menu
     * 
     * @param meal
     * @throws IllegalArgumentException if removing a meal not in menu
     */
    public void removeMeal(Meal meal) throws IllegalArgumentException {
        if (!mealList.contains(meal))
            throw new IllegalArgumentException("Meal not in menu");
        mealList.remove(meal);
        ingredientContainer.removeIngredients(meal.getIngredients());
        numberOfMeals--;
    }

    @Override
    public String toString() {

        StringBuilder sBuilder = new StringBuilder();

        mealList.forEach((meal) -> {
            sBuilder.append("Meal: " + meal.getMealName() + "\n");
        });

        sBuilder.append("Number of meals: " + numberOfMeals + "\n");

        sBuilder.append("Tags: ");
        getTags().forEach((tag) -> sBuilder.append(tag + " "));
        sBuilder.append("\n");

        sBuilder.append("Ingredients: " + ingredientContainer.toString());

        return sBuilder.toString();

    }
}