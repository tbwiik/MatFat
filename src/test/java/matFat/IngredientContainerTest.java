package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import matFat.Ingredient.MEASUREMENTS;

public class IngredientContainerTest {

    IngredientContainer ingredientContainer;
    List<Ingredient> ingList = new ArrayList<>();
    Ingredient ing1, ing2, ing3, ing4, dupIng;

    @BeforeEach
    private void setup() {

        ing1 = new Ingredient("Burgerbrød", 2, "stk", "vegan");
        ing2 = new Ingredient("Salat", 1, "stk", "vegan");
        ing3 = new Ingredient("Veganburger", 2, "stk", "vegan");

        ingList.add(ing1);
        ingList.add(ing2);
        ingList.add(ing3);

        ingredientContainer = new IngredientContainer(ingList);

        // Non-duplicate ingredient
        ing4 = new Ingredient("Bacon", 3, "stk", "meat");

        // Duplicate ingredient (per equal)
        dupIng = new Ingredient("Salat", 2, "stk");

    }

    @Test
    void testAddIngredientNew() {

        List<Ingredient> addedIngList = new ArrayList<>();
        addedIngList.add(ing1);
        addedIngList.add(ing2);
        addedIngList.add(ing3);
        addedIngList.add(ing4);

        ingredientContainer.addIngredient(ing4);
        Assertions.assertEquals(addedIngList, ingredientContainer.getIngredients());

    }

    @Test
    void testAddIngredientDup() {

        List<Ingredient> updatedIngList = new ArrayList<>();
        updatedIngList.add(ing1);
        ing2.updateIngredient(2, MEASUREMENTS.stk);
        updatedIngList.add(ing2);
        updatedIngList.add(ing3);

        ingredientContainer.addIngredient(dupIng);
        Assertions.assertEquals(updatedIngList, ingredientContainer.getIngredients());
    }

    @Test
    void testRemoveIngredient() {

        // Check positive case for removing ingredient
        List<Ingredient> updatedIngList = new ArrayList<>();
        updatedIngList.add(ing2);
        updatedIngList.add(ing3);

        ingredientContainer.removeIngredient(ing1);
        Assertions.assertEquals(updatedIngList, ingredientContainer.getIngredients());

        // Check negative case for removing ingredient
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredientContainer.removeIngredient(ing4);
        });
    }

    // TODO Move this too tagBoxTest?
    // @Test
    // void testGenerateTags() {

    // Set<String> tags = new HashSet<>();

    // tags.add("vegan");
    // Assertions.assertEquals(tags, ingredientContainer.getTags());

    // ingredientContainer.addIngredient(ing4);
    // tags.remove("vegan");
    // Assertions.assertEquals(tags, ingredientContainer.getTags());

    // }

    // TODO write tests for getters. Test internal field not changable?
}
