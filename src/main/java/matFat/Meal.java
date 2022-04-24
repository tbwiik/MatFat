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
    private TagBox tagBox = new TagBox();

    private final static char[] ACCEPTED_DIFFICUILTIES = { 'E', 'M', 'H' };
    private final static int MIN_LENGTH_NAME = 3;
    private final static int MAX_LENGTH_NAME = 30;

    public Meal(String mealName, char difficulty, List<Ingredient> ingredients, List<String> recipe, String... tags)
            throws IllegalArgumentException {

        setMealName(mealName);
        setDifficulty(difficulty);
        this.ingredientContainer = new IngredientContainer(ingredients);
        this.recipe = new Recipe(recipe);
        tagBox.addTags(tags);
    }

    private void checkMealName(String mealName) throws IllegalArgumentException {
        if (mealName.length() < MIN_LENGTH_NAME)
            throw new IllegalArgumentException("Too short mealName");
        if (mealName.length() > MAX_LENGTH_NAME)
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
        Set<String> allTags = new HashSet<>();
        allTags.addAll(ingredientContainer.getTags());
        allTags.addAll(tagBox.getTags());
        return allTags;
    }

    /**
     * Add tag to meal
     * <p>
     * Overwrites whatever tags {@linkplain Ingredient} or
     * {@linkplain IngredientContainer} has.
     * E. g. If specified to add "meat" it add thus even though everything in it
     * is
     * vegan
     *
     * @param tag
     * @throws IllegalArgumentException if unvalid length or format per
     *                                  {@linkplain TagBox#checkTag(String)}
     */
    public void addTag(String tag) throws IllegalArgumentException {
        tagBox.addTag(tag);
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
     * @throws IllegalArgumentException if removing a tag not existing in meal
     */
    public void removeTag(String tag) throws IllegalArgumentException {
        tagBox.removeTag(tag);
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredientContainer.getIngredients());
    }

    public Ingredient getIngredient(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > ingredientContainer.getIngredients().size())
            throw new IndexOutOfBoundsException("Index in ingredient-container out of bounds");
        return ingredientContainer.getIngredient(index);
    }

    public List<String> getRecipe() {
        return new ArrayList<>(recipe.getRecipe());
    }

}