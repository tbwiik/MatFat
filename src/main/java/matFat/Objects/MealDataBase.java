package matFat.Objects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalFileFormatException;
import matFat.filehandling.FileHandlerInterface;

public class MealDataBase implements FileHandlerInterface {

    private List<Meal> meals = new ArrayList<>();
    // TODO fix reading to default
    String filename = "mealDataBase"; // Default place

    public MealDataBase(String filename) throws IllegalFileFormatException {
        this.filename = filename;
        readMealFromFile(filename);
    }

    public MealDataBase() throws IllegalFileFormatException {
        readMealFromFile(filename);
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

    @Override
    public Meal readMealFromFile(String filename) throws IllegalArgumentException {

        BufferedReader reader = null;
        Meal meal = null;

        try {
            // reader = new BufferedReader(new
            // InputStreamReader(getClass().getResourceAsStream(filename + ".txt")));

            // reader = new BufferedReader(new FileReader(new
            // File(getFilePathString(filename))));

            // TODO fix reading from file
            reader = new BufferedReader(new FileReader(
                    "/Users/torbjornwiik/Docs_nonDrive/TDT4100/TheProject/TDT4100_prosjekt_torbjw/testfile.txt"));

            try {

                String mealName = reader.readLine().split(":")[1].strip();
                char difficulty = reader.readLine().split(":")[1].strip().charAt(0);
                List<Ingredient> ingList = new ArrayList<>();
                List<String> recipeList = new ArrayList<>();
                Set<String> mealTags = new HashSet<>();

                // Reads ingredients
                reader.readLine();
                while (true) {
                    String line = reader.readLine().strip();
                    if (line.equals("},"))
                        break;
                    ingList.add(new Ingredient(line.strip().split(" ")));
                }

                // Reads recipe
                reader.readLine();
                while (true) {
                    String line = reader.readLine().strip();
                    if (line.equals("},"))
                        break;
                    recipeList.add(line);
                }

                mealTags.addAll(Arrays.asList(reader.readLine().split(":")[1].strip().split(" ")));

                meal = new Meal(mealName, difficulty, ingList, recipeList, mealTags);

                reader.readLine();

                // If writing to file is correct and file is non-corrupted this block will never
                // run
            } catch (IllegalArgumentException | IndexOutOfBoundsException exceptions) {
                exceptions.printStackTrace();
                throw new IllegalFileFormatException("Cannot read from file due to wrong format");
            }

        } catch (

        IOException ioeRead) {
            ioeRead.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ioeClose) {
                ioeClose.printStackTrace();
            }
        }

        return meal;

    }

    @Override
    public void writeMealToFile(Meal meal, String filename) {

        String content = mealToStr(meal);

        try {
            // BufferedWriter writer = new BufferedWriter(new FileWriter(new
            // File(getFilePathString(filename))));

            // BufferedWriter writer = Files.newBufferedWriter(getFilePath(filename));

            PrintWriter writer = new PrintWriter(filename + ".txt");

            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // TODO change error quote?
            System.err.println("Error writing content to file");
            e.printStackTrace();
        }
    }

    private String ingsToStr(List<Ingredient> ingList) {

        StringBuilder sBuilder = new StringBuilder();

        ingList.forEach((ingredient) -> {
            sBuilder.append(
                    "\n\t" + ingredient.getIngredientName() +
                            " " + ingredient.getIngredientAmount() +
                            " " + ingredient.getIngredientMeasurement() +
                            " " + tagsToStr(ingredient.getTags()));

        });
        sBuilder.append("\n");

        return sBuilder.toString();
    }

    private String tagsToStr(Set<String> tags) {

        StringBuilder sBuilder = new StringBuilder();

        tags.forEach((tag) -> sBuilder.append(tag + " "));

        return sBuilder.toString();
    }

    private String recipeToStr(List<String> recipeList) {

        StringBuilder sBuilder = new StringBuilder();

        recipeList.forEach((recipeLine) -> sBuilder.append("\t" + recipeLine + "\n"));

        return sBuilder.toString();
    }

    private String mealToStr(Meal meal) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(
                "{\n" +
                        "mealName : " + meal.getMealName() + "\n" +
                        "difficulty : " + meal.getDifficulty() + "\n" +
                        "ingredients : {" + ingsToStr(meal.getIngredients()) + "},\n" +
                        "recipe : {\n" + recipeToStr(meal.getRecipe()) + "},\n" +
                        "tags : " + tagsToStr(meal.getTags()) + "\n" +
                        "}\n");

        return sBuilder.toString();
    }

}
