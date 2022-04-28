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
    // TODO fix reading to default
    String filename = "mealDataBase"; // Default filepath

    public MealDataBase(String filename) throws IllegalFileFormatException, FileNotFoundException {
        this.filename = filename;
        readFromFile(filename);
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

    public File getFile(String filename) {
        return new File(MealDataBase.class.getResource("data/").getFile() + filename + ".txt");
    }

    private void readFromFile(String filename) throws FileNotFoundException, IllegalFileFormatException {

        Scanner scanner = null;

        try {

            // scanner = new Scanner(getFile(filename));
            scanner = new Scanner(getFile(filename));

            try {

                // Ignores tags when reading to database
                scanner.nextLine();
                scanner.nextLine();

                while (scanner.hasNext()) {

                    scanner.nextLine(); // Reads first line of opening bracket

                    String mealName = scanner.nextLine().split(":")[1].strip();
                    char difficulty = scanner.nextLine().split(":")[1].strip().toCharArray()[0];
                    List<Ingredient> ingList = new ArrayList<>();
                    List<String> recipeList = new ArrayList<>();
                    Set<String> mealTags = new HashSet<>();

                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine().strip();
                        if (line.equals("},"))
                            break;
                        ingList.add(GenericFunctions.strToIng(line));
                    }

                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine().strip();
                        if (line.equals("},"))
                            break;
                        recipeList.add(line);
                    }

                    mealTags.addAll(Arrays.asList(scanner.nextLine().split(":")[1].strip().split(" ")));

                    meals.add(new Meal(mealName, difficulty, ingList, recipeList, mealTags));

                    scanner.nextLine(); // Ensures that the last line in meal is read

                    // If writing to file is correct and file is non-corrupted this block will never
                    // run
                }

            } catch (IllegalArgumentException | IndexOutOfBoundsException exceptions) {
                exceptions.printStackTrace();
                throw new IllegalFileFormatException("Cannot read from file due to wrong format");
            }
        } catch (IOException ioeRead) {
            ioeRead.printStackTrace();
        } finally {
            // XXX this block necessary?
            try {
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void writeMealToFile(Menu menu, String filename) {

        String content = menuToStr(menu);

        try {
            // BufferedWriter writer = new BufferedWriter(new FileWriter(new
            // File(getFilePathString(filename))));

            // BufferedWriter writer = Files.newBufferedWriter(getFilePath(filename));

            PrintWriter writer = new PrintWriter(getFile(filename));

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

    private String menuToStr(Menu menu) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{\n");
        sBuilder.append("tags : " + tagsToStr(menu.getTags()) + "\n");
        menu.getMealList().forEach((meal) -> {
            sBuilder.append("meal : " + mealToStr(meal));
        });

        return sBuilder.toString();
    }

    public static void main(String[] args) throws FileNotFoundException, IllegalFileFormatException {
        MealDataBase mDB = new MealDataBase("mealDataBase");
        System.out.println(mDB.getMeals().size());
        Menu menu = new Menu(mDB.getMeals(), new HashSet<>());
        mDB.writeMealToFile(menu, "newFile");
    }
}
