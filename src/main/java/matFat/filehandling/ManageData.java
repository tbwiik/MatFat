package matFat.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import matFat.GenericFunctions;
import matFat.Objects.Ingredient;
import matFat.Objects.Meal;
import matFat.Objects.Menu;
import matFat.exceptions.IllegalFileFormatException;

public class ManageData {

    private static File getFile(String filename) {
        return new File(ManageData.class.getResource("data/").getFile() + filename + ".txt");
    }

    public static Menu readMenuFromFile(String filename) throws FileNotFoundException, IllegalFileFormatException {

        Scanner scanner = null;
        List<Meal> mealList = new ArrayList<>();
        Set<String> tagList = new HashSet<>();

        try {

            // scanner = new Scanner(getFile(filename));
            scanner = new Scanner(getFile(filename));

            try {

                // Tags irrelevant when writing dataBase
                scanner.nextLine();
                tagList.addAll(Arrays.asList(scanner.nextLine().split(":")[1].strip().split(" "))); // TODO move this
                                                                                                    // out
                tagList.forEach((item) -> item.strip());

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

                    mealList.add(new Meal(mealName, difficulty, ingList, recipeList, mealTags));

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

        return new Menu(mealList, tagList);

    }

    public static List<Meal> readMealsFromFile(String filename)
            throws FileNotFoundException, IllegalFileFormatException {
        return new ArrayList<>(readMenuFromFile(filename).getMealList());
    }

    public static void writeMenuToFile(Menu menu, String filename) {

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

    public static void writeMealsToFile(List<Meal> mealList, String filename) {
        writeMenuToFile(new Menu(mealList, new HashSet<>()), filename);
    }

    private static String ingsToStr(List<Ingredient> ingList) {

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

    private static String tagsToStr(Set<String> tags) {

        StringBuilder sBuilder = new StringBuilder();

        tags.forEach((tag) -> sBuilder.append(tag + " "));

        return sBuilder.toString();
    }

    private static String recipeToStr(List<String> recipeList) {

        StringBuilder sBuilder = new StringBuilder();

        recipeList.forEach((recipeLine) -> sBuilder.append("\t" + recipeLine + "\n"));

        return sBuilder.toString();
    }

    private static String mealToStr(Meal meal) {

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

    private static String menuToStr(Menu menu) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{\n");
        sBuilder.append("tags : " + tagsToStr(menu.getTags()) + "\n");
        menu.getMealList().forEach((meal) -> {
            sBuilder.append("meal : " + mealToStr(meal));
        });

        return sBuilder.toString();
    }

    public static void main(String[] args) {
        ManageData md = new ManageData();
        md.getFile("mealDataBase").toString();
    }
}
