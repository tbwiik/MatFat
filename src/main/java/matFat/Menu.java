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
     * Constructs menu
     * 
     * @param mealList
     * @param tags
     * @throws IllegalArgumentException if unvalid tags
     */
    public Menu(List<Meal> mealList, Set<String> tags) throws IllegalArgumentException {

        this.mealList = new ArrayList<>(mealList);
        initializeIngredientContainer();
        tagBox = new TagBox(tags);
        numberOfMeals = mealList.size();

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