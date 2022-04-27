package matFat.Objects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import matFat.exceptions.IllegalAmountException;

public class IngredientContainer {

    private List<Ingredient> ingredients = new ArrayList<>();
    private TagBox tagBox;
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
     */
    public IngredientContainer(List<Ingredient> ingredients) {

        for (Ingredient ingredient : ingredients) {
            addIngredient(ingredient);
        }

    }

    /**
     * Generate empty container for use in construction of {@linkplain Menu} and
     * model
     */
    public IngredientContainer() {
        // TODO do things smarter to remove this?
    }

    // Use in Controller
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

    public void removeIngredients(List<Ingredient> ingredientsToRemove)
            throws IllegalArgumentException, IllegalAmountException {
        ingredientsToRemove.forEach((ingredient) -> removeIngredient(ingredient));
    }

    public Ingredient getIngredient(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index > ingredients.size())
            throw new IndexOutOfBoundsException("Index in ingredient-container out of bounds");
        return ingredients.get(index);
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    // TODO make sure that this is implemented everywhere
    public int getNumberOfIngredients() {
        return numberOfIngredients;
    }

    /**
     * Add intersection of tags based on {@linkplain Ingredient}s
     * <p>
     * Initialize {@linkplain TagBox} if null
     * 
     * @param ingredient
     */
    private void addTags(Ingredient ingredient) {

        if (tagBox == null) {
            tagBox = new TagBox(ingredient.getTags());
        } else {
            tagBox.retainAll(ingredient.getTags());
        }
    }

    public Set<String> getTags() {
        return new HashSet<>(tagBox.getTags());
    }

    /**
     * Generate tags to {@linkplain IngredientContainer} based on all
     * {@linkplain Ingredient} tags
     * <p>
     * Only adds intersection of tags
     */
    public void generateTags() {
        ingredients.forEach((ingredient) -> {
            addTags(ingredient);
        });

    }

    @Override
    public String toString() {

        StringBuilder sBuilder = new StringBuilder();

        ingredients.forEach((ingredient) -> {
            sBuilder.append(ingredient + "\n");
        });

        return sBuilder.toString();
    }
}
