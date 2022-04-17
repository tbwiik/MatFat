package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Meal {

    private String mealName;
    private char difficulty;
    private IngredientContainer ingredientContainer;
    private Set<String> tags = new HashSet<>();
    private List<String> recipe = new ArrayList<>();

    private final static char[] ACCEPTED_DIFFICUILTIES = { 'E', 'M', 'H' };

    public Meal(String mealName, char difficulty, List<Ingredient> ingredients, Set<String> tags, List<String> recipe) {

        setMealName(mealName);
        setDifficulty(difficulty);

        this.ingredientContainer = new IngredientContainer(ingredients);

        tags.addAll(tags);
        tags.addAll(ingredientContainer.getTags());

        setRecipe(recipe);

    }

    private void addTag(List<Ingredient> ingredients) {
        // TODO only add tags if every ingredient has it.
        // E.g. a meal is not vegan if it contains one ingredient being meat

        Set<String> tags = ingredients.get(0).getTags();

        // Remove difference in tags from var "tags"
        // e.g. only keep tag if ingredient also has it
        for (Ingredient ingredient : ingredients) {
            if (tags.contains(ingredient.getTags())) {

            }
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
        return tags;
    }

    public void addTag(String tag) {
        Ingredient.checkTag(tag);
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

    private void setRecipe(List<String> recipe) {
        // TODO write checks for recipe
    }

    public List<String> getRecipe() {
        return recipe;
    }

    public IngredientContainer getIngredientContainer() {
        return ingredientContainer;
    }
}