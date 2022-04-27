package matFat.Objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal extends Tagged {

    private String mealName;
    private char difficulty;
    private IngredientContainer ingredientContainer;
    private Recipe recipe;
    // Inherit TagBox from superclass

    private final static char[] ACCEPTED_DIFFICUILTIES = { 'E', 'M', 'H' };
    private final static int MIN_LENGTH_NAME = 3;
    private final static int MAX_LENGTH_NAME = 30;

    /**
     * Constructs a meal
     * 
     * @param mealName
     * @param difficulty
     * @param ingredients
     * @param recipe
     * @param tags
     * @throws IllegalArgumentException if invalid input
     */
    public Meal(String mealName, char difficulty, List<Ingredient> ingredients, List<String> recipe, Set<String> tags)
            throws IllegalArgumentException {

        setMealName(mealName);
        setDifficulty(difficulty);
        this.ingredientContainer = new IngredientContainer(ingredients);
        this.recipe = new Recipe(recipe);
        tagBox = new TagBox(tags);
    }

    /**
     * Checks if length of name is valid
     * 
     * @param mealName
     * @throws IllegalArgumentException
     */
    private void checkMealName(String mealName) throws IllegalArgumentException {
        if (mealName.length() < MIN_LENGTH_NAME)
            throw new IllegalArgumentException("Too short mealName");
        if (mealName.length() > MAX_LENGTH_NAME)
            throw new IllegalArgumentException("Too long mealName");
    }

    /**
     * Sets new meal-name
     * 
     * @param mealName
     * @throws IllegalArgumentException if unvalid input per
     *                                  {@linkplain #checkMealName(String)}
     */
    public void setMealName(String mealName) throws IllegalArgumentException {
        checkMealName(mealName);
        this.mealName = mealName;
    }

    public String getMealName() {
        return mealName;
    }

    /**
     * Checks if accepted difficulty
     * 
     * @param difficulty
     * @throws IllegalArgumentException
     */
    private void checkDifficulty(char difficulty) throws IllegalArgumentException {
        for (int i = 0; i < ACCEPTED_DIFFICUILTIES.length; i++) {
            if (ACCEPTED_DIFFICUILTIES[i] == difficulty)
                return;
        }
        throw new IllegalArgumentException("Not accepted difficulty");
    }

    public char getDifficulty() {
        return difficulty;
    }

    // TODO add lowercase? Yes. Use event listener?
    /**
     * Sets new difficulty
     * 
     * @param difficulty
     * @throws IllegalArgumentException if unvalid input per
     *                                  {@linkplain #checkDifficulty(char)}
     */
    public void setDifficulty(char difficulty) throws IllegalArgumentException {
        checkDifficulty(difficulty);
        this.difficulty = difficulty;
    }

    public Ingredient getIngredient(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > ingredientContainer.getIngredients().size())
            throw new IndexOutOfBoundsException("Index in ingredient-container out of bounds");
        return ingredientContainer.getIngredient(index);
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredientContainer.getIngredients());
    }

    public List<String> getRecipe() {
        return new ArrayList<>(recipe.getRecipe());
    }

    /**
     * @return tags in meal and container
     */
    public Set<String> getTags() {
        Set<String> allTags = new HashSet<>();
        allTags.addAll(ingredientContainer.getTags());
        allTags.addAll(tagBox.getTags());
        return allTags;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Meal: " + mealName);
        stringBuilder.append("\nDifficulty: " + difficulty);

        stringBuilder.append("\nTags: ");
        getTags().forEach((tag) -> stringBuilder.append(tag + " "));

        stringBuilder.append("\nIngredients: \n" + ingredientContainer.toString());
        stringBuilder.append("Recipe: \n" + recipe.toString());

        return stringBuilder.toString();
    }
}