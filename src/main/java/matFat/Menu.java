package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu extends TagBoxUser {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private IngredientContainer ingredientContainer = new IngredientContainer();

    /**
     * Constructs menu with undefined length of tags
     * 
     * @param mealList
     * @param tags
     * @throws IllegalArgumentException if unvalid tags
     */
    public Menu(List<Meal> mealList, String... tags) throws IllegalArgumentException {

        this.mealList = new ArrayList<>(mealList);
        initializeIngredientContainer();
        tagBox = new TagBox(tags);
        numberOfMeals = mealList.size();
    }

    // TODO similar problem in meal related to filewriting
    public Menu(List<Meal> mealList, Set<String> tags) throws IllegalArgumentException {

        this.mealList = new ArrayList<>(mealList);
        initializeIngredientContainer();
        tagBox = new TagBox(tags);
        numberOfMeals = mealList.size();

    }

    /**
     * Constructs empty menu with empty tagBox
     * <p>
     * Used in filehandling
     */
    public Menu() {
        // TODO fix this better?
        tagBox = new TagBox();
    }

    /**
     * Constructs igredient-container based on all ingredients in meal-list
     */
    private void initializeIngredientContainer() {

        mealList.forEach((meal) -> {
            ingredientContainer.addIngredients(meal.getIngredients());
        });

    }

    public List<Meal> getMealList() {
        return new ArrayList<>(mealList);
    }

    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    public Set<String> getTags() {
        Set<String> allTags = new HashSet<>();
        allTags.addAll(ingredientContainer.getTags());
        allTags.addAll(tagBox.getTags());
        return allTags;
    }

    public List<Ingredient> getIngredients() {
        return ingredientContainer.getIngredients();
    }

    public void addMeal(Meal meal) {
        mealList.add(meal);
        ingredientContainer.addIngredients(meal.getIngredients());
        numberOfMeals++;
    }

    public void removeMeal(Meal meal) throws IllegalArgumentException {
        if (!mealList.contains(meal))
            throw new IllegalArgumentException("Meal not in menu");
        mealList.remove(meal);
        ingredientContainer.removeIngredients(meal.getIngredients());
        numberOfMeals--;
    }

}