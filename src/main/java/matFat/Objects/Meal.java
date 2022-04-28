package matFat.Objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalRecipeFormatException;
import matFat.exceptions.IllegalTagFormatException;

public class Meal extends Tagged {

    private String mealName;
    private char difficulty;
    private IngredientContainer ingredientContainer = new IngredientContainer();
    private Recipe recipe = new Recipe();
    // Inherit TagBox from superclass
    // TagBox does not contain tags from ingredient-container

    private final static char[] ACCEPTED_DIFFICUILTIES = { 'E', 'M', 'H' };
    private final static int MIN_LENGTH_NAME = 3;
    private final static int MAX_LENGTH_NAME = 30;

    /**
     * 
     * @param mealName
     * @param difficulty
     * @param ingredients
     * @param recipe
     * @param tags
     * @throws IllegalNameFormatException
     * @throws IllegalDifficultyException
     * @throws IllegalArgumentException     if trying to create meal with no
     *                                      ingredients or no recipe
     * @throws IllegalRecipeFormatException
     * @throws IllegalTagFormatException
     */
    public Meal(String mealName, char difficulty, List<Ingredient> ingredients, List<String> recipe, Set<String> tags)
            throws IllegalNameFormatException, IllegalDifficultyException, IllegalArgumentException,
            IllegalRecipeFormatException, IllegalTagFormatException {

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
     * @throws IllegalNameFormatException if too long or too short name
     */
    private void checkMealName(String mealName) throws IllegalNameFormatException {
        if (mealName.length() < MIN_LENGTH_NAME)
            throw new IllegalNameFormatException("Too short name of meal");
        if (mealName.length() > MAX_LENGTH_NAME)
            throw new IllegalNameFormatException("Too long name of meal");
    }

    /**
     * Sets new meal-name
     * 
     * @param mealName
     * @throws IllegalNameFormatException if unvalid input per
     *                                    {@linkplain #checkMealName(String)}
     */
    public void setMealName(String mealName) throws IllegalNameFormatException {
        checkMealName(mealName);
        this.mealName = mealName;
    }

    /**
     * @return name of meal
     */
    public String getMealName() {
        return mealName;
    }

    /**
     * Checks if input is an accepted difficulty
     * 
     * @param difficulty
     * @throws IllegalDifficultyException
     */
    private void checkDifficulty(char difficulty) throws IllegalDifficultyException {
        for (int i = 0; i < ACCEPTED_DIFFICUILTIES.length; i++) {
            if (ACCEPTED_DIFFICUILTIES[i] == difficulty)
                return;
        }
        throw new IllegalDifficultyException("Not accepted difficulty \n Must be either \"E/M/H\"");
    }

    /**
     * @return difficulty to meal
     */
    public char getDifficulty() {
        return difficulty;
    }

    /**
     * Sets new difficulty
     * <p>
     * Not case-sensitive input
     * 
     * @param difficulty
     * @throws IllegalDifficultyException if unvalid input per
     *                                    {@linkplain #checkDifficulty(char)}
     */
    public void setDifficulty(char difficulty) throws IllegalDifficultyException {
        char diff = (char) Character.toUpperCase(difficulty);
        checkDifficulty(diff);
        this.difficulty = difficulty;
    }

    /**
     * Return ingredient on specific index in list
     * 
     * @param index to ingredient
     * @return {@linkplain Ingredient}
     * @throws IndexOutOfBoundsException if index out of bounds for list
     */
    public Ingredient getIngredient(int index) throws IndexOutOfBoundsException {
        return ingredientContainer.getIngredient(index);
    }

    /**
     * @return all ingredients in meal
     */
    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredientContainer.getIngredients());
    }

    /**
     * @return whole recipe
     */
    public List<String> getRecipe() {
        return new ArrayList<>(recipe.getRecipe());
    }

    /**
     * @return tags in meal and ingredient-container
     */
    public Set<String> getTags() {
        Set<String> allTags = new HashSet<>();
        allTags.addAll(ingredientContainer.getTags()); // Tags from ingredient-container
        allTags.addAll(tagBox.getTags()); // User defined tags
        return allTags;
    }

    /**
     * Add ingredient to meal
     * 
     * @param ingredient
     */
    public void addIngredient(Ingredient ingredient) {
        ingredientContainer.addIngredient(ingredient);
    }

    /**
     * Remove ingredient from meal
     *
     * @param ingredient
     * @throws IllegalArgumentException if ingredient not in meal
     * @throws IllegalAmountException   if removing more ingredient than meal has
     */
    public void removeIngredient(Ingredient ingredient) throws IllegalArgumentException, IllegalAmountException {
        ingredientContainer.removeIngredient(ingredient);
    }

    /**
     * Adds step in recipe to end of list
     * 
     * @param recipeStep
     * @throws IllegalRecipeFormatException
     */
    public void addRecipeStep(String recipeStep) throws IllegalRecipeFormatException {
        recipe.addRecipeLine(recipeStep);
    }

    /**
     * Remove specific step in recipe
     * 
     * @param index
     * @throws IndexOutOfBoundsException if removing step not in list
     */
    public void removeRecipeStep(int index) throws IndexOutOfBoundsException {
        recipe.removeRecipeLine(index);
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Meal: " + mealName + "\n");
        stringBuilder.append("Difficulty: " + difficulty + "\n");

        // Dont use tagBox because tagBox doesnt contain tags from ingredient container
        stringBuilder.append("Tags: ");
        getTags().forEach((tag) -> stringBuilder.append(tag + " "));
        stringBuilder.append("\n");

        stringBuilder.append(ingredientContainer.toString());
        stringBuilder.append(recipe.toString());

        return stringBuilder.toString();
    }

}