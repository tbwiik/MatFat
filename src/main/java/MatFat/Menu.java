package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private TagBox tagBox = new TagBox();
    private IngredientContainer ingredientContainer; // Make a list/set or have one giant container??

    public Menu(List<Meal> mealList, String... tags) throws IllegalArgumentException {

        this.mealList = mealList;

        for (String tag : tags) {
            addTag(tag);
        }

        numberOfMeals = mealList.size();
    }

    private void setIngredientContainer() {

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
    }

    public void removeMeal(Meal meal) throws IllegalArgumentException {
        if (!mealList.contains(meal))
            throw new IllegalArgumentException("Meal not in menu");
        mealList.remove(meal);
    }

}