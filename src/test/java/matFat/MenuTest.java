package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuTest {

    Menu menu;

    List<Meal> mealList = new ArrayList<>();
    Meal m1, m2, newMeal;

    List<Ingredient> il1 = new ArrayList<>();
    List<Ingredient> il2 = new ArrayList<>();
    List<Ingredient> il3 = new ArrayList<>();

    List<String> re1 = new ArrayList<>();
    List<String> re2 = new ArrayList<>();
    List<String> re3 = new ArrayList<>();

    String tagCheap; // Used for testing of getTags()

    @BeforeEach
    private void setup() {

        String tagEasy = "easy";
        String tagVegan = "vegan";
        String tagQuick = "quick";
        String tagMeat = "meat";
        String tagAnimal = "animal";
        tagCheap = "cheap";

        Ingredient i11 = new Ingredient("Burgerbrød", 2, "stk", tagVegan);
        Ingredient i12 = new Ingredient("Salat", 1, "stk", tagVegan);
        Ingredient i13 = new Ingredient("Veganburger", 2, "stk", tagVegan);
        il1.add(i11);
        il1.add(i12);
        il1.add(i13);

        Ingredient i21 = new Ingredient("Tomatsuppe Toro", 1, "stk", tagVegan);
        Ingredient i22 = new Ingredient("Pølse", 500, "g", tagMeat);
        Ingredient i23 = new Ingredient("Makaroni", 500, "g", tagQuick);
        il2.add(i21);
        il2.add(i22);
        il2.add(i23);

        Ingredient i31 = new Ingredient("Pasta", 1, "pk");
        Ingredient i32 = new Ingredient("Egg", 2, "stk", tagAnimal);
        Ingredient i33 = new Ingredient("Meat", 400, "g", tagMeat, tagAnimal);
        il3.add(i31);
        il3.add(i32);
        il3.add(i33);

        re1.add("Grill burger.");
        re1.add("Varm the bread.");
        re1.add("Put everything together and serve!");

        re2.add("Boil water and insert macaroni. Cook for 10 min.");
        re2.add("Heat the soup slowly and boil for 2 min.");
        re2.add("Heat sausages and put everything together.");

        re3.add("Boil water and cook pasta for 15min.");
        re3.add("Boil eggs.");
        re3.add("Grill meat.");

        m1 = new Meal("Pasta Carbonara", 'M', il1, re1, tagQuick);
        m2 = new Meal("Tomatosoup", 'E', il2, re2, tagEasy);
        mealList.add(m1);
        mealList.add(m2);

        menu = new Menu(mealList, tagCheap);

        newMeal = new Meal("Pasta with egg and meat", 'E', il3, re3, tagEasy, tagQuick);
    }

    // XXX delete this?
    @Test
    void testConstructor() {

        Assertions.assertEquals(mealList, menu.getMealList());
        // Assertions.assertEquals(?, menu.getTags()); Tested in another file
        Assertions.assertEquals(mealList.size(), menu.getNumberOfMeals());
        // Assertions.assertEquals(?, menu.getIngredients()); Tested in another file

    }

    @Test
    void testAddTag() {

        Set<String> newTagSet = new HashSet<>();

        String newTag = "untasty";
        menu.addTag(newTag);

        newTagSet.add(newTag);
        newTagSet.add(tagCheap);

        Assertions.assertEquals(newTagSet, menu.getTags());

    }

    @Test
    void testRemoveTag() {

        menu.removeTag(tagCheap);
        Assertions.assertEquals(new HashSet<>(), menu.getTags());

    }

    @Test
    void testAddMeal() {

        menu.addMeal(newMeal);

        mealList.add(newMeal);
        Assertions.assertEquals(mealList, menu.getMealList());

        Assertions.assertEquals(mealList.size(), menu.getNumberOfMeals());

        Set<String> tagSet = new HashSet<>();
        tagSet.add(tagCheap);

        Assertions.assertEquals(tagSet, menu.getTags());

        List<Ingredient> allIngredients = new ArrayList<>();
        allIngredients.addAll(il1);
        allIngredients.addAll(il2);
        allIngredients.addAll(il3);
        IngredientContainer newIngCont = new IngredientContainer(allIngredients);
        Assertions.assertEquals(newIngCont.getIngredients(), menu.getIngredients());

    }

    @Test
    void testRemoveMeal() {

        // Test removing meal not in menu
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            menu.removeMeal(newMeal);
        });

        // Test removing meal in menu
        menu.removeMeal(m2);

        mealList.remove(m2);

        Assertions.assertEquals(mealList, menu.getMealList());

        Assertions.assertEquals(mealList.size(), menu.getNumberOfMeals());

        Set<String> tagSet = new HashSet<>();
        tagSet.add(tagCheap);
        Assertions.assertEquals(tagSet, menu.getTags());

        IngredientContainer newIngCont = new IngredientContainer(il1);
        Assertions.assertEquals(newIngCont.getIngredients(), menu.getIngredients());

    }
}
