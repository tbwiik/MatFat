package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IngredientContainer {

    private List<Ingredient> ingredients = new ArrayList<>();
    private TagBox tagBox = new TagBox();
    private int numberOfIngredients;

    /**
     * Creates container with {@linkplain Ingredient}
     * <p>
     * Add ingredients to container according to
     * {@linkplain IngredientContainer#addIngredient()}.
     * Merges similar ingredients to one
     * 
     * @param ingredients
     */
    public IngredientContainer(List<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients) {
            addIngredient(ingredient);
        }

        generateTags();

    }

    /**
     * <b>Adds ingredient to container</b>
     * <p>
     * Add ingredient to container if it does not exist
     * <p>
     * Update ingredient in container if it already exist
     * <p>
     * If ingredient exist in container but measurement is different the new
     * ingredient will be added to container - not updated
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
                    try {
                        item.updateIngredient(ingredient.getIngredientAmount(), ingredient.getIngredientMeasurement());
                    } catch (IllegalMeasurementException IllegalMeasurementException) {
                        this.ingredients.add(ingredient);
                    }
                    break;
                }
            }
        }

        // Intersection
        if (tagBox.getTags().isEmpty()) {
            tagBox.addTags(ingredient.getTags());
        } else {
            tagBox.retainAll(ingredient.getTags());
        }

    }

    public void addIngredients(List<Ingredient> ingredients) {
        ingredients.forEach((ingredient) -> addIngredient(ingredient));
    }

    // TODO feels like this code is ineffectively written... Clean up?
    // TODO write documentation
    public void removeIngredient(Ingredient ingredientToRemove)
            throws IllegalArgumentException, IllegalAmountException {

        boolean updated = false;

        if (!ingredients.contains(ingredientToRemove))
            throw new IllegalArgumentException("Container does not contain this ingredient");

        for (Ingredient ingredient : ingredients) {
            if (ingredient.equals(ingredientToRemove)) {
                if (ingredient.getIngredientMeasurement().equals(ingredientToRemove.getIngredientMeasurement())) {
                    int ingAmount = ingredient.getIngredientAmount();
                    int ingRemAmount = ingredientToRemove.getIngredientAmount();
                    if (ingAmount < ingRemAmount)
                        throw new IllegalAmountException("Removing too high amount of ingredient");
                    if (ingAmount == ingRemAmount)
                        this.ingredients.remove(ingredient);
                    if (ingAmount > ingRemAmount)
                        ingredient.updateIngredient(ingAmount - ingRemAmount,
                                ingredientToRemove.getIngredientMeasurement());

                    updated = true;
                    break;
                }
            }
        }

        // XXX is this redundant code? what about different measurements??
        if (!updated)
            throw new IllegalArgumentException("No such ingredient");

        generateTags();

    }

    public void removeIngredients(List<Ingredient> ingredientsToRemove)
            throws IllegalArgumentException, IllegalAmountException {
        ingredientsToRemove.forEach((ingredient) -> removeIngredient(ingredient));
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public Ingredient getIngredient(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > ingredients.size())
            throw new IndexOutOfBoundsException("Index in ingredient-container out of bounds");
        return ingredients.get(index);
    }

    public Set<String> getTags() {
        return new HashSet<>(tagBox.getTags());
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    /**
     * Generate tags to {@linkplain IngredientContainer} based on all
     * {@linkplain Ingredient} tags
     * <p>
     * Only add tags that every ingredient has.
     * This ensures that tags in container is valid for whole container.
     * E.g. dont add vegan if container contains meat as ingredient
     *
     */
    public void generateTags() {

        // Adds tags to have an amount of tags to start with
        tagBox.addTags(ingredients.get(0).getTags());

        // Iterates through ingredients and only keep tags that are similar
        ingredients.forEach((ingredient) -> {
            tagBox.retainAll(ingredient.getTags());
        });

    }

}
