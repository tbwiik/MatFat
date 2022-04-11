package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matFat.Ingredient.MEASUREMENTS;

public class IngredientContainer {

    private List<Ingredient> ingredients = new ArrayList<>();
    private Set<String> tags = new HashSet<>();
    private int numberOfIngredients;

    public IngredientContainer(List<Ingredient> ingredients) {
        generateIngredientSummary(ingredients);
    }

    private void generateIngredientSummary(List<Ingredient> ingredients) throws IllegalArgumentException {

        for (Ingredient ingredient : ingredients) {
            addIngredient(ingredient);
        }

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
     * @throws IllegalArgumentException if updating ingredient
     */
    public void addIngredient(Ingredient ingredient) throws IllegalArgumentException {

        if (!this.ingredients.contains(ingredient))
            this.ingredients.add(ingredient);

        for (Ingredient item : this.ingredients) {
            if (item.equals(ingredient)) {
                try {
                    item.updateIngredient(ingredient.getIngredientAmount(), ingredient.getIngredientMeasurement());
                } catch (IllegalMeasurementException IllegalMeasurementException) {
                    this.ingredients.add(ingredient);
                }
            }
        }

        // Adds tags from ingredient to container-set
        tags.addAll(ingredient.getTags());

    }

    /**
     * Remove ingredient if {@linkplain Ingredient#equals(Object) equals()} to one
     * in container
     * 
     * @param ingredientToRemove ingredient to remove
     * @throws IllegalArgumentException if trying to remove ingredient not in
     *                                  container
     */
    public void removeIngredient(Ingredient ingredientToRemove) throws IllegalArgumentException {
        if (!ingredients.contains(ingredientToRemove))
            throw new IllegalArgumentException("Container does not contain this ingredient");
        ingredients.forEach((ingredient) -> {
            if (ingredient.equals(ingredientToRemove))
                ingredients.remove(ingredient);
        });

        generateTags();

    }

    /**
     * Generate tags to {@linkplain IngredientContainer} based on all
     * {@linkplain Ingredient} tags
     */
    private void generateTags() {
        ingredients.forEach((ingredient) -> tags.addAll(ingredient.getTags()));
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public Set<String> getTags() {
        return tags;
    }

    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

}
