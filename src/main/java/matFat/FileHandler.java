package matFat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileHandler implements fHandlerInterface {

    @Override
    public Menu readFromFile(String filename) throws IllegalArgumentException {

        BufferedReader reader = null;
        Menu menu = new Menu();
        Set<String> menuTags = new HashSet<>();

        try {
            // reader = new BufferedReader(new
            // InputStreamReader(getClass().getResourceAsStream(filename + ".txt")));
            // TODO fix reading from file
            reader = new BufferedReader(new FileReader(
                    "/Users/torbjornwiik/Docs_nonDrive/TDT4100/TheProject/TDT4100_prosjekt_torbjw/testfile.txt"));

            // TODO try/catch this one
            if (reader.ready()) {
                reader.readLine(); // Ignores first line
                menuTags.addAll(Arrays.asList(reader.readLine().split(":")[1].strip().split(" ")));
                menuTags.forEach((item) -> item.strip()); // TODO effectivize this?
                menu.addTags(menuTags);
            }

            while (reader.ready()) {

                try {

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

                    menu.addMeal(meal);

                    reader.readLine();

                } catch (IllegalArgumentException | IndexOutOfBoundsException exceptions) {
                    exceptions.printStackTrace();
                    // XXX change exception?
                    throw new IllegalArgumentException("Cannot read from file due to wrong format");
                }
            }

        } catch (IOException ioeRead) {
            ioeRead.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException ioeClose) {
                ioeClose.printStackTrace();
            }
        }

        return menu;

    }

    // private List<Ingredient> StrToIngs(BufferedReader reader) {
    // re

    // }

    private void StrToTags(BufferedReader reader) {

    }

    private void StrToRecipe(BufferedReader reader) {

    }

    private void StrToMeal(BufferedReader reader) {

    }

    private void StrToMenu(BufferedReader reader) {

    }

    @Override
    public void writeToFile(Menu menu, String filename) {

        String content = menuToStr(menu);

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(filename + ".txt"), true));
            writer.append(content);
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

}