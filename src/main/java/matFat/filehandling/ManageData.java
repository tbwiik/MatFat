package matFat.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

public class ManageData implements FileHandlerInterface {

    private File getFile(String filename) {
        return new File(ManageData.class.getResource("data/").getFile() + filename + ".txt");
    }

    @Override
    public Menu readMenuFromFile(String filename) throws FileNotFoundException, IllegalFileFormatException {

        List<Meal> mealList = new ArrayList<>();
        Set<String> tagList = new HashSet<>();

        try (Scanner scanner = new Scanner(getFile(filename))) {

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

                    // Reads ingredient
                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine().strip();
                        if (line.equals("},"))
                            break;
                        ingList.add(GenericFunctions.strToIng(line));
                    }

                    // Reads ingredient
                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        String line = scanner.nextLine().strip();
                        if (line.equals("},"))
                            break;
                        recipeList.add(line);
                    }

                    List<String> tmpTags = Arrays.asList(scanner.nextLine().split(":")[1].strip().split(" "));
                    mealTags.addAll(tmpTags);

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
        }
        return new Menu(mealList, tagList);

    }

    @Override
    public List<Meal> readMealsFromFile(String filename)
            throws FileNotFoundException, IllegalFileFormatException {
        return new ArrayList<>(readMenuFromFile(filename).getMealList());
    }

    @Override
    public void writeMenuToFile(Menu menu, String filename) {

        String content = menuToStr(menu);
        try {

            // PrintWriter writer = new PrintWriter(getFile(filename));
            // PrintWriter writer = new PrintWriter(new FileOutputStream(getFile(filename)),
            // false);
            FileWriter writer = new FileWriter(getFile(filename));

            writer.write(content); // Does this write or overwrite?? XXX
            // writer.flush();
            writer.close();
            System.out.println(content);
        } catch (Exception e) {
            // TODO change error quote?
            System.err.println("Error writing content to file");
            e.printStackTrace();
        }
    }

    @Override
    public void writeMealsToFile(List<Meal> mealList, String filename) {
        Menu tmpMenu = new Menu(mealList, new HashSet<>());
        writeMenuToFile(tmpMenu, filename);
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

        if (tags.isEmpty())
            return "";

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

}
