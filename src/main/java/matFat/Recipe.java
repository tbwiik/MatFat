package matFat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Recipe {

    List<String> recipe = new ArrayList<>();

    public Recipe(List<String> recipe) throws IllegalArgumentException {
        setRecipe(recipe);
    }

    private void setRecipe(List<String> recipe) throws IllegalArgumentException {
        // XXX Consider making public to be able to rewrite whole recipe in one push.

        for (String recipeLine : recipe) {
            checkRecipeLine(recipeLine);
            this.recipe.add(recipeLine);
        }

    }

    /**
     * Checks if a line in recipe is valid
     * 
     * @param recipeLine point in the recipe-list
     * @throws IllegalArgumentException if too long or too short
     */
    private void checkRecipeLine(String recipeLine) throws IllegalArgumentException {
        if (recipeLine.length() < 3)
            throw new IllegalArgumentException("Too short recipeline");

        // XXX Consider changing the length of this?
        if (recipeLine.length() > 3000)
            throw new IllegalArgumentException("Too long recipeline");
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
     */
    public void updateRecipeLines(Map<Integer, String> recipeLines) {
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
     */
    public void updateRecipeLine(int listIndex, String recipeLine) {
        checkRecipeLine(recipeLine);
        recipe.set(listIndex, recipeLine);
    }

}
