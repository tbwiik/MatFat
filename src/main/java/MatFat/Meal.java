package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal {
    // Attributes
    private String mealName;
    private char difficulty;
    private List<Ingredient> ingredientList = new ArrayList<>();
    private Set<String> tags = new HashSet<>();
    private final static char[] acceptedDifficulties = { 'E', 'M', 'H' };

    // Constructor
    public Meal(String mealName, char difficulty, List<Ingredient> ingredientList, Set<String> tags) {
        setMealName(mealName);
        setDifficulty(difficulty);

        for (Ingredient ing : ingredientList) {
            addIngredient(ing);
        }

        for (String tag : tags) {
            addTag(tag);
        }
    }

    // Method

    private void checkMealName(String mealName) throws IllegalArgumentException {
        if (mealName.length() < 3)
            throw new IllegalArgumentException("Too short mealName");
        if (mealName.length() > 20)
            throw new IllegalArgumentException("Too long mealName");
    }

    public void setMealName(String mealName) throws IllegalArgumentException {
        checkMealName(mealName);
        this.mealName = mealName;
    }

    public String getMealName() {
        return mealName;
    }

    public char getDifficulty() {
        return difficulty;
    }

    private void checkDifficulty(char difficulty) throws IllegalArgumentException {
        for (int i = 0; i < acceptedDifficulties.length; i++) {
            if (acceptedDifficulties[i] == difficulty)
                return;
        }
        throw new IllegalArgumentException("Not accepted difficulty");
    }

    // TODO add lowercase? Yes. Use event listener?
    public void setDifficulty(char difficulty) throws IllegalArgumentException {
        checkDifficulty(difficulty);
        this.difficulty = difficulty;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void addIngredient(String ingredientName, Integer ingredientAmount, String ingredientMeasurement) {
        // TODO check for duplicates (not same ingredient added twice)
        Ingredient newIngredient = new Ingredient(ingredientName, ingredientAmount, ingredientMeasurement);
        ingredientList.add(newIngredient);
    }

    public void addIngredient(Ingredient newIngredient) {
        // TODO add checks
        ingredientList.add(newIngredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        if (!ingredientList.contains(ingredient))
            throw new IllegalArgumentException("This ingredient does not exist in meal");
        ingredientList.remove(ingredient);
    }

    public void updateIngredient(Ingredient ingredient, String ingredientName, Integer ingredientAmount,
            String ingredientMeasurement) {
        // TODO change such that only one thing can be edited
        ingredient.setIngredientName(ingredientName);
        ingredient.setIngredientAmount(ingredientAmount);
        ingredient.setIngredientMeasurement(ingredientMeasurement);
    }

    public Set<String> getTags() {
        return tags;
    }

    private void checkTag(String tag) {
        if (tag.length() > 10)
            throw new IllegalArgumentException("Too long tag");
        if (tag.length() < 3)
            throw new IllegalArgumentException("Too short tag");
    }

    public void addTag(String tag) {
        checkTag(tag);
        tags.add(tag);
    }

    public void removeTag(String tag) {
        for (String iTag : tags) {
            if (iTag.equals(tag))
                tags.remove(tag);
        }
    }

}
