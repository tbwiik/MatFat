package matFat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Menu {

    private List<Meal> mealList = new ArrayList<>();
    private int numberOfMeals;
    private Set<String> tags = new HashSet<>();
    private List<Ingredient> ingredients = new ArrayList<>();

    public Menu(List<Meal> mealList, int numberOfMeals, Set<String> tags) {
        this.mealList.addAll(mealList);
        this.numberOfMeals = numberOfMeals;
        this.tags.addAll(tags);

    }

    public List<Meal> getMenu() {
        return menu;
    }

    public int getNumberOfMeals() {
        return numberOfMeals;
    }

    public Set<String> getTags() {
        return tags;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Generate a list of ingredients where duplicates are summaried together
     * 
     * 
     * @param ingredients
     * @return non-duplicate ingredient-list
     */
    private List<String> setIngredient(List<String> ingredients) {

    }

    // TODO write toString using Stringbuilder
}
