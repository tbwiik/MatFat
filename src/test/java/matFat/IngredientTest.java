package matFat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IngredientTest {

    Ingredient ingredient;

    @BeforeEach
    private void setup() {
        ingredient = new Ingredient("Melk", 2, "dl");
    }

    @Test
    public void testSetIngredientName() {

        // TODO check positive case

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientName("c");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            int n = 51;
            String s = "c";
            String tooManyChars = new String(new char[n]).replace("\0", s);
            ingredient.setIngredientName(tooManyChars);
        });
    }

    @Test
    public void testSetIngredientAmount() {

        // TODO check positive case

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientAmount(10 ^ 7);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientAmount(-1);
        });

    }

    @Test
    public void testSetIngredientMeasurement() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientMeasurement("lol");
        });
    }

}
