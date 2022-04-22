package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal {

    private String mealName;
    private char difficulty;
    private IngredientContainer ingredientContainer;
    private Recipe recipe;
    private Set<String> tags = new HashSet<>();

    private final static char[] ACCEPTED_DIFFICUILTIES = { 'E', 'M', 'H' };

    public Meal(String mealName, char difficulty, List<Ingredient> ingredients, List<String> recipe, String... tags) {

        setMealName(mealName);
        setDifficulty(difficulty);
        this.ingredientContainer = new IngredientContainer(ingredients);
        this.recipe = new Recipe(recipe); // Will maybe end up throwing exception due to nonvalid input. Check after
                                          // actually writing class
        setTags(tags);
    }

    private void setTags(String... tags) {
        this.tags.addAll(ingredientContainer.getTags());
        for (String tag : tags) {
            addTag(tag);
        }
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
        return new HashSet<>(tags);
    }

    /**
     * Add tag to meal
     * <p>
     * Overwrites whatever tags {@linkplain Ingredient} or
     * {@linkplain IngredientContainer} has.
     * E. g. If specified to add "meat" it add thus even though everything in it is
     * vegan
     * 
     * @param tag
     * @throws IllegalArgumentException if unvalid length or format per
     *                                  {@linkplain Ingredient#checkTag(String)}
     */
    public void addTag(String tag) throws IllegalArgumentException {
        Ingredient.checkTag(tag);
        tags.add(tag);
    }

    /**
     * Remove tag from tag-set
     * <p>
     * Overwrites whatever tags {@linkplain Ingredient} or
     * {@linkplain IngredientContainer} has.
     * E. g. If specified to remove "vegan" it removes thus even though everything
     * in it is vegan
     * 
     * @param tag
     * @throws IllegalArgumentException if removing tag not existing in meal
     */
    public void removeTag(String tag) throws IllegalArgumentException {
        if (tags.contains(tag))
            tags.remove(tag);
        throw new IllegalArgumentException("Tag does not exist");
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredientContainer.getIngredients());
    }

    public List<String> getRecipe() {
        return new ArrayList<>(recipe.getRecipe());
    }

}