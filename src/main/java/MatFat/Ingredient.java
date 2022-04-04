package matFat;

import java.util.regex.Pattern;

public class Ingredient {

    private String ingredientName;
    private Integer ingredientAmount;
    private MEASUREMENTS ingredientMeasurement;

    public static enum MEASUREMENTS {
        g, // gram
        kg, // kilogram
        ml, // millilitre
        cl, // centilitre
        dl, // desilitre
        l, // litre
        pcs, // packages
        pk, // pakke (norwegian)
        stk, // stykk (norwegian)
        ts, // teaspoon
        tbsp, // tablespoon
        cup, // cup
        Oz, // ounce
        dz, // dozen
    }

    // TODO Change regex such that you must have more than one chars
    private final static String ingNameReg = "[a-zA-ZæøåÆØÅ]*";

    public Ingredient(String ingredientName, Integer ingredientAmount, MEASUREMENTS ingredientMeasurement) {
        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        this.ingredientMeasurement = ingredientMeasurement;
    }

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

    public MEASUREMENTS getIngredientMeasurement() {
        return ingredientMeasurement;
    }

    public void setIngredientMeasurement(String ingredientMeasurement) throws IllegalArgumentException {
        try {
            this.ingredientMeasurement = MEASUREMENTS.valueOf(ingredientMeasurement);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Not accepted measurement-type: " + ingredientMeasurement);
        }
    }

    public void setIngredientMeasurement(MEASUREMENTS ingredientMeasurement) {
        this.ingredientMeasurement = ingredientMeasurement;
    }

    /**
     * 
     * @param ingredient
     * @return true if name is similar
     */
    // TODO remove this one if other is good enough
    public boolean equals(Ingredient ingredient) {
        if (this.ingredientName.toLowerCase() == ingredient.ingredientName.toLowerCase()) {
            return true;
        }
        return false;
    }

    // XXX this necessary
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ingredientName == null) ? 0 : ingredientName.hashCode());
        return result;
    }

    // TODO change this?
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        if (ingredientName == null) {
            if (other.ingredientName != null)
                return false;
        } else if (!ingredientName.toLowerCase().equals(other.ingredientName.toLowerCase()))
            return false;
        return true;
    }

    @Override
    public String toString() {
        // TODO write better using StringBuilder
        return "Ingredient [ingredientAmount=" + ingredientAmount + ", ingredientMeasurement=" + ingredientMeasurement
                + ", ingredientName=" + ingredientName + "]";
    }

}
