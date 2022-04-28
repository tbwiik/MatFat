package matFat.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import matFat.exceptions.IllegalRecipeFormatException;

public class Recipe {

    List<String> recipe = new ArrayList<>();

    public Recipe(String recipeLine) throws IllegalArgumentException, IllegalRecipeFormatException {
        addRecipeLine(recipeLine);
    }

    public Recipe(List<String> recipe) throws IllegalArgumentException, IllegalRecipeFormatException {
        setRecipe(recipe);
    }

    public Recipe() {
    }

    private void setRecipe(List<String> recipe) throws IllegalArgumentException, IllegalRecipeFormatException {
        // XXX Consider making public to be able to rewrite whole recipe in one push.
        if (recipe.isEmpty())
            throw new IllegalArgumentException("Can't create recipe with no content");

        for (String recipeLine : recipe) {
            addRecipeLine(recipeLine);
        }

    }

    /**
     * Checks if a line in recipe is valid
     * 
     * @param recipeLine point in the recipe-list
     * @throws IllegalRecipeFormatException if too long or too short
     */
    private void checkRecipeLine(String recipeLine) throws IllegalRecipeFormatException {
        if (recipeLine.length() < 3)
            throw new IllegalRecipeFormatException("Too short recipeline");

        // XXX Consider changing the length of this?
        if (recipeLine.length() > 3000)
            throw new IllegalRecipeFormatException("Too long recipeline");
    }

    public List<String> getRecipe() {
        return new ArrayList<>(recipe);
    }

    public String getRecipeLine(int index) throws IndexOutOfBoundsException {
        if (index <= 0 || index > recipe.size())
            throw new IndexOutOfBoundsException("Recipe-index out of bounds");
        return recipe.get(index);
    }

    /**
     * Updates multiple lines in recipe-list
     * 
     * @param recipeLines
     * @param indexes
     * @throws IllegalRecipeFormatException
     */
    public void updateRecipeLines(Map<Integer, String> recipeLines) throws IllegalRecipeFormatException {
        recipeLines.forEach((index, strUpdate) -> {
            checkRecipeLine(strUpdate);
            recipe.set(index, strUpdate);
            // TODO make it possible to update valid lines even though one of them is
            // unvalid?
        });
    }

    /**
     * Updates specific line in recipe-list
     * 
     * @param recipeLine
     * @param listIndex
     * @throws IllegalRecipeFormatException
     */
    public void updateRecipeLine(int listIndex, String recipeLine) throws IllegalRecipeFormatException {
        checkRecipeLine(recipeLine);
        recipe.set(listIndex, recipeLine);
    }

    /**
     * Append point in recipe to end of recipe-list
     * 
     * @param recipeLine
     * @throws IllegalRecipeFormatException
     */
    public void addRecipeLine(String recipeLine) throws IllegalRecipeFormatException {
        checkRecipeLine(recipeLine);
        recipe.add(recipeLine);
    }

    // TODO add tests
    public void removeRecipeLine(int index) throws IllegalArgumentException {
        if (index < 0 && index > recipe.size() - 1)
            throw new IllegalArgumentException("No such index in recipe-list");
        recipe.remove(index);
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Recipe: \n");
        recipe.forEach((point) -> stringBuilder.append(point + "\n"));

        return stringBuilder.toString();

    }

}
