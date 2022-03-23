package matFat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Ingredient {

    private String ingredientName;
    private Integer ingredientAmount;
    private String ingredientMeasurement;

    // TODO add more cooking measurements
    private final static Set<String> sizeTypes = new HashSet<>(
            Arrays.asList("g", "kg", "ml", "cl", "dl", "l", "pk", "stk"));
    private final static String ingNameReg = "[a-zA-ZæøåÆØÅ]*";

    public Ingredient(String ingredientName, Integer ingredientAmount, String ingredientMeasurement) {
        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        setIngredientMeasurement(ingredientMeasurement);
    }

    public String getIngredientName() {
        return ingredientName;
    }

    private void checkIngredientName(String ingredientName) throws IllegalArgumentException {

        if (ingredientName.length() < 3)
            throw new IllegalArgumentException("Too short Ingredient-name");

        if (ingredientName.length() > 50)
            throw new IllegalArgumentException("Too long Ingredient-name");

        // TODO add norwegian charachters?
        boolean iNameMatch = Pattern.matches(ingNameReg, ingredientName);

        if (!iNameMatch)
            throw new IllegalArgumentException("Ingredient-name can onlyconsist of chars");
    }

    public void setIngredientName(String ingredientName) throws IllegalArgumentException {
        checkIngredientName(ingredientName);
        this.ingredientName = ingredientName;
    }

    public Integer getIngredientAmount() {
        return ingredientAmount;
    }

    private void checkIngredientAmount(Integer ingredientAmount) throws IllegalArgumentException {
        if (ingredientAmount <= 0)
            throw new IllegalArgumentException("The amount of ingredient must be greater than 0");

        if (ingredientAmount > (Math.pow(10, 6)))
            throw new IllegalArgumentException("Way too much ingredients...");

    }

    public void setIngredientAmount(Integer ingredientAmount) throws IllegalArgumentException {
        checkIngredientAmount(ingredientAmount);
        this.ingredientAmount = ingredientAmount;
    }

    public String getIngredientMeasurement() {
        return ingredientMeasurement;
    }

    private void checkIngredientMeasurement(String ingredientMeasurement) throws IllegalArgumentException {
        boolean isValidType = sizeTypes.contains(ingredientMeasurement);
        if (!isValidType)
            throw new IllegalArgumentException("Not accepted type of measurement");
    }

    public void setIngredientMeasurement(String ingredientMeasurement) throws IllegalArgumentException {
        checkIngredientMeasurement(ingredientMeasurement);
        this.ingredientMeasurement = ingredientMeasurement;
    }

    /**
     * 
     * @param ingredient
     * @return true if name is similar
     */
    public boolean equals(Ingredient ingredient) {
        if (this.ingredientName.toLowerCase() == ingredient.ingredientName.toLowerCase()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Ingredient ing = new Ingredient("Melk", 2, "dl");
        // ing.setIngredientAmount(400);
        System.out.println(ing.getIngredientAmount());
    }

    @Override
    public String toString() {
        return "Ingredient [ingredientAmount=" + ingredientAmount + ", ingredientMeasurement=" + ingredientMeasurement
                + ", ingredientName=" + ingredientName + "]";
    }

}
