package matFat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeTest {

    Recipe recipe;
    String rline1;
    String rline2;
    String rline3;

    @BeforeEach
    private void setup() {

        rline1 = "Add first dry ingredients to bowl";
        rline2 = "Mix";
        rline3 = "Add wet ingredients";

        recipe = new Recipe(rline1, rline2, rline3);

    }

    @Test
    void testUpdateRecipeLines() {

        Map<Integer, String> updates = new HashMap<>();
        String lineUpdate1 = "Add milk, sugar and butter";
        String lineUpdate2 = "Stirr";

        updates.put(1, lineUpdate1);
        updates.put(2, lineUpdate2);

        // Tests valid line-updates
        recipe.updateRecipeLines(updates);
        Assertions.assertEquals(lineUpdate1, recipe.getRecipeLine(1));
        Assertions.assertEquals(lineUpdate2, recipe.getRecipeLine(2));

        // Tests invalid line-updates
        String invalidLineUpdate = "Hi";
        updates.put(3, invalidLineUpdate);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recipe.updateRecipeLines(updates);
        });
    }

    @Test
    void testUpdateRecipeLine() {

        // Tests valid update of line
        recipe.updateRecipeLine(2, "Shake it");
        Assertions.assertEquals("Shake it", recipe.getRecipeLine(2));

        // Tests invalid update of line - too short
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recipe.updateRecipeLine(1, "hi");
        });

        // TODO Tests invalid update of line - too long

    }

    // TODO write tests for getters. Test internal field not changable
}
