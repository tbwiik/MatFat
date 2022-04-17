package matFat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

import matFat.Ingredient.MEASUREMENTS;

public class fHandler implements fHandlerInterface {

    Set<Meal> allMeals = new HashSet<>();

    // TODO written, never tested, not entirely finished either
    // Need to implement recipe
    @Override
    public Set<Meal> readFromFile(String filename) throws IllegalArgumentException {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            while (reader.ready()) {

                try {

                    reader.readLine(); // Ignores first line
                    String mealName = reader.readLine().split(":")[1];
                    char difficulty = reader.readLine().split(":")[1].charAt(0);
                    List<Ingredient> ingredientList = new ArrayList<>();
                    Set<String> tags = new HashSet<>();

                    reader.readLine();
                    while (true) {
                        String line = reader.readLine().strip();
                        if (line.equals("},"))
                            break;
                        ingredientList.add(new Ingredient(line.split(" ")));
                    }

                    reader.readLine();
                    while (true) {
                        String line = reader.readLine().strip();
                        if (line.equals("},"))
                            break;
                        tags.add(line);
                    }

                    allMeals.add(new Meal(mealName, difficulty, new IngredientContainer(ingredientList), tags, recipe))

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

        return allMeals;
    }

    // XXX wtf did i use this for??? - probably to create and ingredient...
    // This should throw some exceptions?!?!
    private Ingredient toIng(final String inputline) {
        String[] ingComp = inputline.split(" ");
        String name = ingComp[0];
        Integer amount = Integer.parseInt(ingComp[1]);
        MEASUREMENTS measurement = MEASUREMENTS.valueOf(ingComp[2]);
        return new Ingredient(name, amount, measurement);
    }

    // XXX Honestly think this should work. Need to fix reading first to be able to
    // check...?

    @Override
    public void writeToFile(Set<Meal> meals, String filename) {

        String content = mealSetToStr(meals);

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(filename), true));
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // TODO change error quote
            System.err.println("Shit happened");
            e.printStackTrace();
        }
    }

    private String mealSetToStr(Set<Meal> meals) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{\n");
        meals.forEach((meal) -> sBuilder.append(mealToStr(meal) + "\n"));
        sBuilder.append("}\n");

        return sBuilder.toString();
    }

    /**
     * Converts list of ingredients to text-format
     * 
     * @param ingList List of Ingredients
     * @return String of formatted list
     */
    private String ingListToStr(List<Ingredient> ingList) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("ingredientlist:{");
        ingList.forEach((ingredient) -> {
            sBuilder.append(
                    "\n\t" + ingredient.getIngredientName() +
                            " " + ingredient.getIngredientAmount() +
                            " " + ingredient.getIngredientMeasurement());
        });
        sBuilder.append("\n},");

        return sBuilder.toString();
    }

    // XXX Consider smashing this together with ingListToStr
    private String ingContainerToStr(IngredientContainer ingCont) {
        return ingListToStr(ingCont.getIngredients());
    }

    private String tagSetToStr(Set<String> tagSet) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("tagSet:{\n");
        tagSet.stream().forEach((tag) -> sBuilder.append(tag + "\n"));
        sBuilder.append("},\n");

        return sBuilder.toString();
    }

    private String mealToStr(Meal meal) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(
                "{\n" +
                        "mealName : " + meal.getMealName() + ",\n" +
                        "difficulty : " + meal.getDifficulty() + ",\n" +
                        ingContainerToStr(meal.getIngredientContainer()) + "\n" +
                        tagSetToStr(meal.getTags()) + ",\n" +
                        "}\n");

        return sBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(Math.pow(10, 7));
    }
}