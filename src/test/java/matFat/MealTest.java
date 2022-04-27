package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import matFat.Objects.Ingredient;
import matFat.Objects.Meal;

public class MealTest {

    Meal meal;
    String mealName;
    char difficulty;

    Ingredient ing1, ing2, ing3;
    List<Ingredient> ingList = new ArrayList<>();

    String recLine1, recLine2, recLine3;
    List<String> recipeList = new ArrayList<>();

    String tag1, tag2, extraTag;
    Set<String> tags = new HashSet<>(); // Used for test of constructor and tags

    @BeforeEach
    private void setup() {

        mealName = "Pasta Carbonara";

        difficulty = 'E';

        ing1 = new Ingredient("Pasta", 1, "pk");
        ing2 = new Ingredient("Egg", 2, "stk");
        ing3 = new Ingredient("Meat", 400, "g");
        ingList.add(ing1);
        ingList.add(ing2);
        ingList.add(ing3);

        recLine1 = "Boil water and cook pasta";
        recLine2 = "Add raw egg to pasta";
        recLine3 = "Steak meat and add to pasta";
        recipeList.add(recLine1);
        recipeList.add(recLine2);
        recipeList.add(recLine3);

        tag1 = "Meat";
        tag2 = "Fast";
        extraTag = "tasty";
        tags.add(tag1); // Used for test of constructor and tags
        tags.add(tag2); // Used for test of constructor and tags

        meal = new Meal(mealName, difficulty, ingList, recipeList, tags);

    }

    @Test
    void testConstructor() {
        Assertions.assertEquals(mealName, meal.getMealName());
        Assertions.assertEquals(difficulty, meal.getDifficulty());
        Assertions.assertEquals(ingList, meal.getIngredients());
        Assertions.assertEquals(recipeList, meal.getRecipe());
        Assertions.assertEquals(tags, meal.getTags());
    }

    @Test
    void testSetName() {

        // Tests valid case
        meal.setMealName("Pastalove");
        Assertions.assertEquals("Pastalove", meal.getMealName());

        // Tests invalid case - too short name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meal.setMealName("Fo");
        });

        // Tests invalid case - too long name
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meal.setMealName("thisIsALittleBitTooLongMealNameTooUse");
        });
    }

    @Test
    void testSetDifficulty() {

        // Tests valid case for changing difficulty
        meal.setDifficulty('M');
        Assertions.assertEquals('M', meal.getDifficulty());

        // Tests invalid case for changing difficulty
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meal.setDifficulty('F');
        });

    }

    @Test
    void testAddTag() {

        meal.addTag(extraTag);
        tags.add(extraTag);
        Assertions.assertEquals(tags, meal.getTags());

    }

    @Test
    void testRemoveTag() {

        // Tests valid case
        meal.removeTag(tag1);
        tags.remove(tag1);
        Assertions.assertEquals(tags, meal.getTags());

        // Tests invalid case
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            meal.removeTag("foo");
        });

    }
}
