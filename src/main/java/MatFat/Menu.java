package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private Set<String> tags = new HashSet<>();
    private IngredientContainer ingredientContainer; // Make a list/set or have one giant container??

    public Menu(List<Meal> mealList, Set<String> tags) {

    }

    public List<Meal> getMealList() {
        return mealList;
    }

    // Consider deleting method and do in constructor instead
    private void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    // Consider do in constructor
    private void setNumberOfMeals() {
        this.numberOfMeals = mealList.size();
    }

    public Set<String> getTags() {
        return tags;
    }

    public void addTag(String tag) throws IllegalArgumentException {
        Ingredient.checkTag(tag);
        this.tags.add(tag);
    }

    public IngredientContainer getIngredientContainer() {
        return ingredientContainer;
    }

}