package matFat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

import matFat.Objects.Ingredient;
import matFat.Objects.Meal;
import matFat.Objects.Menu;
import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalFileFormatException;

public class MealDataBase {

    private List<Meal> meals = new ArrayList<>();

    String defaultFilename = "mealDataBase"; // Default filepath

    public MealDataBase(String filename) throws IllegalFileFormatException, FileNotFoundException {

    }

    public MealDataBase() throws IllegalFileFormatException, FileNotFoundException {
        readFromFile(filename);
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
        writeMealToFile(meal, filename);
    }

    public Meal getRandomMeal() {

        Random rand = new Random();

        return meals.get(rand.nextInt(meals.size()));

    }

    // TODO fix this messy thing
    private List<Meal> validMeals(Predicate predicate) throws NoSuchElementException {

        List<Meal> validMeals = new ArrayList<Meal>(meals.stream().filter(predicate).toList());

        if (validMeals.isEmpty())
            throw new NoSuchElementException("No meal meets the required tags");

        return validMeals;
    }

    private void checkNum(int n) throws IllegalAmountException {
        if (n <= 0)
            throw new IllegalAmountException("Too low amount of meals");
        if (n > 100)
            throw new IllegalAmountException("Too high amount of meals");

    }

    public Meal getRandomMeal(Predicate predicate) throws NoSuchElementException {

        Random rand = new Random();
        Meal meal = validMeals(predicate).get(rand.nextInt(meals.size()));

        return meal;
    }

    public List<Meal> getRandomMeals(Predicate predicate, int amount) throws NoSuchElementException {

        List<Meal> randomMeals = new ArrayList<>();

        checkNum(amount);

        while (randomMeals.size() < amount)
            getRandomMeal(predicate);

        return randomMeals;

    }

}
