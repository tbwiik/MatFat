package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TheAppMainThing {

    // XXX Consider changing to List to make it easier to get random
    Set<Meal> allMeals = new HashSet<>();
    Menu menu;

    // XXX Unsure about innkapsling in whole file
    private void generateMenu(int numberOfMeals, Set<String> tags) {

        // TODO implement tags
        // Only make it possible to get dinners matching input-tags
        // allMeals.stream().filter(Meal.getTags.c)

        Set<Meal> meals = new HashSet<>();

        while (meals.size() < numberOfMeals)
            meals.add(getRandomMeal(allMeals)); // Ensures that there are non-duplicate and fullmenu

    }

    private Meal getRandomMeal(Set<Meal> allMeals) {

        Random rand = new Random();

        List<Meal> allMealsList = new ArrayList<>(allMeals);

        return allMealsList.get(rand.nextInt(allMealsList.size()));

    }

    private Set<Meal> getAllMeals() {

    }

}
