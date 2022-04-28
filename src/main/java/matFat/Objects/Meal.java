package matFat.Objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalRecipeFormatException;

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
            throws IllegalNameFormatException, IllegalDifficultyException {

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
     * @throws IllegalNameFormatException
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

    public String getMealName() {
        return mealName;
    }

    /**
     * Checks if accepted difficulty
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

    public char getDifficulty() {
        return difficulty;
    }

    // TODO add lowercase? Yes. Use event listener?
    /**
     * Sets new difficulty
     * 
     * @param difficulty
     * @throws IllegalDifficultyException if unvalid input per
     *                                    {@linkplain #checkDifficulty(char)}
     */
    public void setDifficulty(char difficulty) throws IllegalDifficultyException {
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
        allTags.addAll(ingredientContainer.getTags()); // Tags from ingredient-container
        allTags.addAll(tagBox.getTags()); // User defined tags
        return allTags;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredientContainer.addIngredient(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) throws IllegalArgumentException, IllegalAmountException {
        ingredientContainer.removeIngredient(ingredient);
    }

    public void addRecipeStep(String recipeStep) throws IllegalRecipeFormatException {
        recipe.addRecipeLine(recipeStep);
    }

    public void removeRecipeStep(int index) throws IllegalArgumentException {
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