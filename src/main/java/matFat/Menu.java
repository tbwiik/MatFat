package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private TagBox tagBox = new TagBox();
    private IngredientContainer ingredientContainer = new IngredientContainer();

    public Menu(List<Meal> mealList, String... tags) throws IllegalArgumentException {

        this.mealList = new ArrayList<>(mealList);
        initializeIngredientContainer();
        tagBox.addTags(tags);
        numberOfMeals = mealList.size();
    }

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

    // TODO exact similar code in Meal. how to utilize TagBoxUser??
    // writing twice?
    public void addTag(String tag) throws IllegalArgumentException {
        tagBox.addTag(tag);
    }

    public void removeTag(String tag) throws IllegalArgumentException {
        tagBox.removeTag(tag);
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