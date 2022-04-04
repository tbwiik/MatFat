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
import java.util.List;
import java.util.Set;

import matFat.Ingredient.MEASUREMENTS;

public class fHandler implements fHandlerInterface {

    HashMap<Integer, Meal> allMeals;
    Integer id;
    Meal meal;

    @Override
    public HashMap<Integer, Meal> readFromFile(String filename) {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(filename)));
            while (reader.ready()) {
                String line = reader.readLine();
                //TODO Fix reading
                meal.addIngredient(toIng(line));
                allMeals.put(key, value)
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

        return null;
    }

    private Ingredient toIng(final String inputline) {
        String[] ingComp = inputline.split(" ");
        String name = ingComp[0];
        Integer amount = Integer.parseInt(ingComp[1]);
        MEASUREMENTS measurement = MEASUREMENTS.valueOf(ingComp[2]);
        return new Ingredient(name, amount, measurement);
    }

    @Override
    public void writeToFile(HashMap<Integer, Meal> meals, String filename) {

        String content = hashMapToStr(meals);

        try {
            BufferedWriter writer = new BufferedWriter(
                    new FileWriter(new File(filename), true));
            writer.append(content);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            // TODO change quote
            System.err.println("Shit happened");
            e.printStackTrace();
        }
    }

    private String hashMapToStr(HashMap<Integer, Meal> meals) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("{\n\n");
        meals.forEach((key, value) -> sBuilder.append(mealToStr(value) + "\n"));
        sBuilder.append("}\n");

        return sBuilder.toString();
    }

    /**
     * Converts list of ingredients to text-format
     * 
     * @param ingList List of Ingredients
     * @return String in JSON format of IngredientList
     */
    private String listToStr(List<Ingredient> ingList) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("ingredientlist: {");
        ingList.forEach((ingredient) -> {
            sBuilder.append(
                    "\n\t" + ingredient.getIngredientName() +
                            " " + ingredient.getIngredientAmount() +
                            " " + ingredient.getIngredientMeasurement());
        });
        sBuilder.append("\t},");

        return sBuilder.toString();
    }

    private String setToStr(Set<String> tagSet) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append("tagSet : {");
        tagSet.stream().forEach((tag) -> sBuilder.append(tag + " "));
        sBuilder.append("}");

        return sBuilder.toString();
    }

    private String mealToStr(Meal meal) {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(
                meal.getId() + ": {\n" +
                        "mealID : " + meal.getId() + ",\n" +
                        "mealName : " + meal.getMealName() + ",\n" +
                        "difficulty : " + meal.getDifficulty() + ",\n" +
                        listToStr(meal.getIngredientList()) + "\n" +
                        setToStr(meal.getTags()) + ",\n" +
                        "}\n");

        return sBuilder.toString();
    }

    public static void main(String[] args) {
        // HashMap<String, Integer> foo = new HashMap<>();
        // foo.put("1", 1);
        // foo.put("2", 2);
        // fHandler fhandler = new fHandler();
        // fhandler.writeToFile(foo, "testfile");
    }
}