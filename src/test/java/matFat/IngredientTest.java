package matFat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import matFat.Ingredient.MEASUREMENTS;

public class IngredientTest {

    Ingredient ingredient;

    @BeforeEach
    private void setup() {
        ingredient = new Ingredient("Melk", 2, "dl");
    }

    @Test
    public void testSetIngredientName() {

        // Check positive case for change of name
        ingredient.setIngredientName("Surmelk");
        Assertions.assertEquals("Surmelk", ingredient.getIngredientName());

        // Check too few characthers in name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientName("c");
        });

        // Check if too many chars
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            int n = 51;
            String s = "c";
            String tooManyChars = new String(new char[n]).replace("\0", s);
            ingredient.setIngredientName(tooManyChars);
        });
    }

    @Test
    public void testSetIngredientAmount() {

        // Checks positive case for change of amount
        ingredient.setIngredientAmount(100);
        Assertions.assertEquals(100, ingredient.getIngredientAmount());

        // Checks too high amount
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientAmount((int) Math.pow(10, 7));
        });

        // Checks too low amount
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientAmount(0);
        });

    }

    @Test
    public void testSetIngredientMeasurement() {

        // Checks positive case for setting measurement
        ingredient.setIngredientMeasurement("l");
        Assertions.assertEquals(MEASUREMENTS.l, ingredient.getIngredientMeasurement());

        // Checks negative case
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setIngredientMeasurement("lol");
        });
    }

    @Test
    void testSetTags() {

        // Checks positive case
        // XXX Shorten this
        Set<String> tag = new HashSet<>();
        tag.add("lol");

        ingredient.setTags("lol");
        Assertions.assertEquals(tag, ingredient.getTags());

        // Check too short tag
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setTags("c");
        });

        // Check too long tag
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ingredient.setTags("ccccccccccc");
        });
    }

    @Test
    void testUpdateIngredient() {

        // Checks positive case for updating ingredient
        ingredient.updateIngredient(5, MEASUREMENTS.dl);
        Assertions.assertEquals(5, ingredient.getIngredientAmount());

        // // Checks fail when different measurement
        // Assertions.assertThrows(IllegalMeasurementException.class, () -> {
        // ingredient.updateIngredient(1, MEASUREMENTS.foo);
        // });
        // XXX No point in this test unless updateingredient accept strings

    }

    @Test
    void testEquals() {

        // Checks psoitive case for equals. Note: different measurement
        Ingredient newIng = new Ingredient("Melk", 4, "l");
        Assertions.assertTrue(ingredient.equals(newIng));
    }

    // TODO write tests for getters. Test internal field not changable
}
