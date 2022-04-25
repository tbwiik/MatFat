package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class IngredientContainer {

    private List<Ingredient> ingredients = new ArrayList<>();
    private TagBox tagBox;
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

    }

    // Used for making it possible to generate container in menu
    public IngredientContainer() {
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
                    item.updateIngredient(ingredient.getIngredientAmount(), ingredient.getIngredientMeasurement());
                    break;
                }
            }
        }

        addTag(ingredient);

    }

    /**
     * Add intersection of tags based on {@linkplain Ingredient}
     * <p>
     * Add and initialize {@linkplain TagBox} if null
     * 
     * @param ingredient
     */
    private void addTag(Ingredient ingredient) {

        if (tagBox == null) {
            tagBox = new TagBox(ingredient.getTags());
        } else {
            tagBox.retainAll(ingredient.getTags());
        }
    }

    public void addIngredients(List<Ingredient> ingredients) {
        ingredients.forEach((ingredient) -> {
            addIngredient(ingredient);
            addTag(ingredient);
        });
    }

    // TODO write documentation
    public void removeIngredient(Ingredient ingredientToRemove)
            throws IllegalArgumentException, IllegalAmountException {

        Optional<Ingredient> optIng = ingredients.stream()
                .filter((ingredient) -> ingredient.equals(ingredientToRemove))
                .findFirst();

        if (optIng.isPresent()) {
            Ingredient ingredient = optIng.get();

            int ingAmount = ingredient.getIngredientAmount();
            int ingRemAmount = ingredientToRemove.getIngredientAmount();

            if (ingAmount < ingRemAmount)
                throw new IllegalAmountException("Removing too high amount of ingredient");
            if (ingAmount == ingRemAmount)
                this.ingredients.remove(ingredient);
            if (ingAmount > ingRemAmount)
                ingredient.updateIngredient(ingAmount - ingRemAmount, ingredientToRemove.getIngredientMeasurement());

        } else {
            throw new IllegalArgumentException("Container does not contain this ingredient");
        }

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

        tagBox = new TagBox();

        ingredients.forEach((ingredient) -> {
            addTag(ingredient);
        });

    }

}
