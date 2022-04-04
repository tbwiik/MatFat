package matFat;

import java.util.ArrayList;
import java.util.List;

public class IngredientContainer {

    List<String> ingredients = new ArrayList<>();
    // List<String> ingredientSummary = new ArrayList<>();

    public IngredientContainer(final List<String> ingredients) {
        this.ingredients = ingredients;
    }

    // Ingredient summary
    // XXX moved from menu. Move back??
    // TODO fix and clean
    public List<Ingredient> getIngredients() {
        List<Ingredient> filteredList = new ArrayList<>();
        menu.forEach((meal) -> meal.getIngredientList().forEach((ingredient) -> {
            if (!filteredList.contains(ingredient))
                filteredList.add(ingredient);
            else
                filteredList.forEach((item) -> {
                    if (item.equals(ingredient))
                        // TODO assumes that ingredientMeasurement is the same (which it does not need
                        // to be). fix that
                        ingredient.setIngredientAmount(ingredient.getIngredientAmount() + item.getIngredientAmount());
                });
        }));

        return filteredList;

    }

}
