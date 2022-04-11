package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matFat.Ingredient.MEASUREMENTS;

public class Meal {

    private String mealName;
    private char difficulty;
    private IngredientContainer ingredientContainer;
    private Set<String> tags = new HashSet<>();

    private final static char[] ACCEPTED_DIFFICUILTIES = { 'E', 'M', 'H' };

    public Meal(String mealName, char difficulty, List<Ingredient> ingredients, Set<String> tags) {

        setMealName(mealName);
        setDifficulty(difficulty);

        this.ingredientContainer = new IngredientContainer(ingredients);

        tags.addAll(tags);
        tags.addAll(ingredientContainer.getTags());

    }

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
        for (int i = 0; i < ACCEPTED_DIFFICUILTIES.length; i++) {
            if (ACCEPTED_DIFFICUILTIES[i] == difficulty)
                return;
        }
        throw new IllegalArgumentException("Not accepted difficulty");
    }

    // TODO add lowercase? Yes. Use event listener?
    public void setDifficulty(char difficulty) throws IllegalArgumentException {
        checkDifficulty(difficulty);
        this.difficulty = difficulty;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        Ingredient.checkIngredientTag(tag);
        tags.add(tag);
    }

    /**
     * Remove tag from tag-set
     * <p>
     * Overwrites whatever tags {@linkplain Ingredient} or
     * {@linkplain IngredientContainer} has.
     * 
     * @param tag
     */
    public void removeTag(String tag) {
        tags.remove(tag);
    }

    @Override
    public String toString() {
        // TODO write better using Stringbuilder
        return "Meal [difficulty=" + difficulty + ", id=" + id + ", ingredientList=" + ingredientList + ", mealName="
                + mealName + ", tags=" + tags + "]";
    }

    public static void main(String[] args) {
        Ingredient ing = new Ingredient("Lol", 2, "stk");
    }
}