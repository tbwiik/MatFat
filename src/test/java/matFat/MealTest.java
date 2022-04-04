package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MealTest {

    Integer id;
    Meal meal;
    String mealName;
    char difficulty;
    Ingredient ing1;
    List<Ingredient> ingList = new ArrayList<>();
    Set<String> tags = new HashSet<>();

    @BeforeEach
    private void setup() {

        mealName = "Pasta Carbonara";
        difficulty = 'E';

        // XXX Add every ingredient as attributes (only need one for testing)
        ing1 = new Ingredient("Pasta", 1, "pk");
        Ingredient ing2 = new Ingredient("Egg", 2, "stk");
        Ingredient ing3 = new Ingredient("Meat", 400, "g");

        ingList.add(ing1);
        ingList.add(ing2);
        ingList.add(ing3);

        String tag1 = "Meat";
        String tag2 = "Fast";

        tags.add(tag1);
        tags.add(tag2);

        meal = new Meal(mealName, difficulty, ingList, tags);
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(mealName, meal.getMealName());
        Assertions.assertEquals(difficulty, meal.getDifficulty());
        Assertions.assertEquals(ingList, meal.getIngredientList());
        Assertions.assertEquals(tags, meal.getTags());
    }

    @Test
    public void testSetDifficulty() {

        meal.setDifficulty('M');
        Assertions.assertEquals('M', meal.getDifficulty());

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meal.setDifficulty('F');
        });

    }

    @Test
    public void testAddIngredient() {

    }

    @Test
    public void testRemoveIngredient() {

    }

    @Test
    public void testUpdateIngredient() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meal.updateIngredient(ing1, ";D:%");
        });

        meal.updateIngredient(ing1, "3");
        Assertions.assertEquals(3, ing1.getIngredientAmount());

        meal.updateIngredient(ing1, "kg");
        Assertions.assertEquals("kg", ing1.getIngredientMeasurement());

        meal.updateIngredient(ing1, "Pastaaa");
        Assertions.assertEquals("Pastaaa", ing1.getIngredientName());

    }

    @Test
    public void testAddTag() {

    }

    @Test
    public void testRemoveTag() {

    }
}
