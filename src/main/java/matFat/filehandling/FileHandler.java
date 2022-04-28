package matFat.filehandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import matFat.MatFatApp;
import matFat.Objects.Ingredient;
import matFat.Objects.Meal;
import matFat.Objects.Menu;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalFileFormatException;

public class FileHandler implements fHandlerInterface {

    public String getFilePathString(String filename) {
        return (getClass().getResource("data/").getFile() + filename + ".txt");
    }

    public Path getFilePath(String filename) {
        return Paths.get((MatFatApp.class.getResource("/data/") + filename + ".txt"));
    }

    @Override
    public Menu readFromFile(String filename) throws IllegalArgumentException, IllegalFileFormatException {

        BufferedReader reader = null;
        Set<String> menuTags = new HashSet<>();
        List<Meal> mealList = new ArrayList<>();

        try {
            // reader = new BufferedReader(new
            // InputStreamReader(getClass().getResourceAsStream(filename + ".txt")));

            // reader = new BufferedReader(new FileReader(new
            // File(getFilePathString(filename))));

            // TODO fix reading from file
            reader = new BufferedReader(new FileReader(
                    "/Users/torbjornwiik/Docs_nonDrive/TDT4100/TheProject/TDT4100_prosjekt_torbjw/testfile.txt"));

            try {
                if (reader.ready()) {
                    reader.readLine();
                    menuTags.addAll(Arrays.asList(reader.readLine().split(":")[1].strip().split(" ")));
                    menuTags.forEach((item) -> item.strip());
                }

                while (reader.ready()) {

                    reader.readLine();
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

                    Meal meal = new Meal(mealName, difficulty, ingList, recipeList, mealTags);
                    mealList.add(meal);

                    reader.readLine();
                }

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

        return new Menu(mealList, menuTags);

    }

    @Override
    public void writeToFile(Menu menu, String filename) {

        String content = menuToStr(menu);

        try {
            // BufferedWriter writer = new BufferedWriter(new FileWriter(new
            // File(getFilePathString(filename))));

            // BufferedWriter writer = Files.newBufferedWriter(getFilePath(filename));

            PrintWriter writer = new PrintWriter(filename + ".txt");

            // writer.append(content);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // TODO change error quote
            System.err.println("Error writing content to file");
            e.printStackTrace();
        }
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

    public static void main(String[] args) throws IllegalArgumentException, IllegalFileFormatException {
        // FileHandler fhandler = new FileHandler();
        // String str = FileHandler.class.getResource("data/").toString();
        URL url = IllegalDifficultyException.class.getResource("/data/test.txt");
        System.out.println(url.toString());
        // System.out.println(IllegalDifficultyException);
    }

}