package matFat.Objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import matFat.exceptions.IllegalAmountException;

public class IngredientContainer {

    private List<Ingredient> ingredients = new ArrayList<>();
    private TagBox tagBox; // Initialize tagBox here breaks code used for generating correct tags
    private int numberOfIngredients;

    /**
     * Creates container with {@linkplain Ingredient}s
     * <p>
     * Add ingredients to container according to
     * {@linkplain IngredientContainer#addIngredient()}.
     * <p>
     * Merge similar ingredients to one
     * 
     * @param ingredients
     * @throws IllegalArgumentException if trying to create empty
     *                                  ingredient-container
     */
    public IngredientContainer(List<Ingredient> ingredients) throws IllegalArgumentException {

        if (ingredients.isEmpty())
            throw new IllegalArgumentException("Can't create ingredient-container with no ingredients");
        for (Ingredient ingredient : ingredients) {
            addIngredient(ingredient);
        }

    }

    /**
     * Empty constructor used for initalizing in other classes
     * <p>
     * Ensures that toString and other functions don't break the program
     */
    public IngredientContainer() {
    }

    /**
     * Create container with one ingredient
     * 
     * @param ingredient
     */
    public IngredientContainer(Ingredient ingredient) {
        addIngredient(ingredient);
    }

    /**
     * <b>Adds ingredient to container</b>
     * <p>
     * Add ingredient to container if it does not exist
     * <p>
     * Update ingredient in container if it already exist
     * <p>
     * Add tags from each ingredient to container
     * 
     * @param ingredient
     */
    public void addIngredient(Ingredient ingredient) {

        if (!this.ingredients.contains(ingredient)) {
            this.ingredients.add(ingredient);
        } else {
            for (Ingredient item : this.ingredients) {
                if (item.equals(ingredient)) {
                    item.updateIngredient(ingredient.getIngredientAmount());
                    break;
                }
            }
        }

        addTags(ingredient);

    }

    /**
     * Adds ingredients to container using {@linkplain #addIngredient(Ingredient)}
     * 
     * @param ingredients
     */
    public void addIngredients(List<Ingredient> ingredients) {
        ingredients.forEach((ingredient) -> {
            addIngredient(ingredient);
            addTags(ingredient);
        });
    }

    /**
     * <b>Remove ingredient from container<\b>
     * <p>
     * Remove ingredient if removing same amount as in container
     * <p>
     * Update ingredient if removing less amount than in container
     * <p>
     * Regenerate tags based on new content
     * 
     * @param ingredientToRemove
     * @throws IllegalArgumentException if trying to remove ingredient not in
     *                                  container
     * @throws IllegalAmountException   if removing more ingredient than is possible
     */
    public void removeIngredient(Ingredient ingredientToRemove)
            throws IllegalArgumentException, IllegalAmountException {

        // Create a optional ingredient that mach inputArgs
        Optional<Ingredient> optIng = ingredients.stream()
                .filter((ingredient) -> ingredient.equals(ingredientToRemove))
                .findFirst();

        if (optIng.isPresent()) {
            Ingredient ingredient = optIng.get();

            int ingAmount = ingredient.getIngredientAmount();
            int ingRemAmount = ingredientToRemove.getIngredientAmount();

            if (ingAmount < ingRemAmount)
                throw new IllegalAmountException("Removing too high amount of ingredient");
            if (ingAmount == ingRemAmount) {
                this.ingredients.remove(ingredient);
                numberOfIngredients--;
            }
            if (ingAmount > ingRemAmount)
                ingredient.updateIngredient(ingAmount - ingRemAmount);

        } else {
            throw new IllegalArgumentException("Container does not contain this ingredient");
        }

        generateTags();

    }

    /**
     * Remove multiple ingredients using {@linkplain #removeIngredient(Ingredient)}
     * 
     * @param ingredientsToRemove
     * @throws IllegalArgumentException
     * @throws IllegalAmountException
     */
    public void removeIngredients(List<Ingredient> ingredientsToRemove)
            throws IllegalArgumentException, IllegalAmountException {
        ingredientsToRemove.forEach((ingredient) -> removeIngredient(ingredient));
    }

    /**
     * Returns ingredient on specific index
     * 
     * @param index in ingredient-list
     * @return {@linkplain Ingredient}
     * @throws IndexOutOfBoundsException if index is out of bounds for list
     */
    public Ingredient getIngredient(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > ingredients.size())
            throw new IndexOutOfBoundsException("Index in ingredient-container out of bounds");
        return ingredients.get(index);
    }

    /**
     * @return all ingredients in list
     */
    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    /**
     * @return number of ingredients in list
     */
    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    /**
     * Add intersection of tags based on {@linkplain Ingredient}s
     * <p>
     * Initialize {@linkplain TagBox} if null
     * 
     * @param ingredient added
     */
    private void addTags(Ingredient ingredient) {

        if (tagBox == null) {
            tagBox = new TagBox(ingredient.getTags());
        } else {
            tagBox.retainAll(ingredient.getTags());
        }
    }

    /**
     * @return tags in container
     */
    public Set<String> getTags() {
        return new HashSet<>(tagBox.getTags());
    }

    /**
     * Generate tags to {@linkplain IngredientContainer} based on all
     * {@linkplain Ingredient} tags
     * <p>
     * Only adds intersection of tags
     * <p>
     * Use {@linkplain #addTags(Ingredient)} method
     */
    public void generateTags() {
        ingredients.forEach((ingredient) -> {
            addTags(ingredient);
        });

    }

    @Override
    public String toString() {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("Ingredients: \n");
        ingredients.forEach((ingredient) -> {
            sBuilder.append(ingredient + "\n");
        });

        return sBuilder.toString();
    }

}
