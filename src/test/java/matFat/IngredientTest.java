package matFat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IngredientTest {

    @Test
    public void testSetIngredientName() {
        Ingredient ingredient = new Ingredient("Melk", 2, "dl");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientName("c");
        });
    }
}
