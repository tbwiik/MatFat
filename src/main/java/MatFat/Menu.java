package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private TagBox tagBox = new TagBox();
    private IngredientContainer ingredientContainer;

    public Menu(List<Meal> mealList, String... tags) throws IllegalArgumentException {

        this.mealList = mealList;
        initializeIngredientContainer();
        initializeTags(tags);
        numberOfMeals = mealList.size();
    }

    private void initializeTags(String... tags) {
        generateTags();

        for (String tag : tags) {
            addTag(tag);
        }

    }

    private void initializeIngredientContainer() {

        mealList.forEach((meal) -> {
            ingredientContainer.addIngredients(meal.getIngredients());
        });

    }

    // XXX Utilize abstract class /interface to reuse code more places?? Many
    // classes have a similar method as this one
    private void generateTags() {

        tagBox.addTags(mealList.get(0).getIngredient(0).getTags());

        mealList.forEach((meal) -> {
            meal.getIngredients().forEach((ingredient) -> {
                tagBox.retainAll(ingredient.getTags());
            });
        });

    }

    public List<Meal> getMealList() {
        return new ArrayList<>(mealList);
    }

    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    public Set<String> getTags() {
        return new HashSet<>(tagBox.getTags());
    }

    public void addTag(String tag) throws IllegalArgumentException {
        tagBox.addTag(tag);
    }

    public IngredientContainer getIngredientContainer() {
        return ingredientContainer;
    }

    public void addMeal(Meal meal) {
        mealList.add(meal);
        numberOfMeals++;
    }

    public void removeMeal(Meal meal) throws IllegalArgumentException {
        if (!mealList.contains(meal))
            throw new IllegalArgumentException("Meal not in menu");
        mealList.remove(meal);
        numberOfMeals--;
    }

}