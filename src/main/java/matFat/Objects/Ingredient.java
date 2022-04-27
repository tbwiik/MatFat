package matFat.Objects;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalMeasurementException;

public class Ingredient {

    private String ingredientName;
    private Integer ingredientAmount;
    private MEASUREMENTS ingredientMeasurement;
    private TagBox tagBox = new TagBox();

    private static final String INGNAMEREG_STRING = "[a-zA-ZæøåÆØÅ-]*"; // Dont allow whitespace due to filehandling

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

    /**
     * Constructs Ingredient object where measurement is enum
     * 
     * @param ingredientName
     * @param ingredientAmount
     * @param ingredientMeasurement
     * @param tags
     * @throws IllegalArgumentException
     * @throws IllegalAmountException
     */
    public Ingredient(String ingredientName, Integer ingredientAmount, MEASUREMENTS ingredientMeasurement,
            String... tags)
            throws IllegalArgumentException, IllegalAmountException {
        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        this.ingredientMeasurement = ingredientMeasurement;
        this.tagBox = new TagBox(strArrayToSet(tags));
    }

    // TODO put these together?
    public Ingredient(String ingredientName, Integer ingredientAmount, String ingredientMeasurement, String... tags)
            throws IllegalArgumentException, IllegalAmountException {

        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        setIngredientMeasurement(ingredientMeasurement);
        this.tagBox = new TagBox(strArrayToSet(tags));
    }

    // TODO implement iterator??
    /**
     * Constructs Ingredient
     * <p>
     * Used in filehandling
     * 
     * @param ingArgs name, amount, measurement, tags...
     * @throws IllegalArgumentException
     * @throws IllegalAmountException
     */
    public Ingredient(String[] ingArgs) throws IllegalArgumentException, IllegalAmountException {
        setIngredientName(ingArgs[0]);
        try {
            setIngredientAmount(Integer.parseInt(ingArgs[1]));
        } catch (NumberFormatException nFormatException) {
            throw new IllegalArgumentException("Cannot parse ingredient-amount to int");
        }
        setIngredientMeasurement(ingArgs[2]);

        if (ingArgs.length > 3) {
            Set<String> tagSet = new HashSet<>();
            for (int i = 3; i < ingArgs.length; i++)
                tagSet.add(ingArgs[i]);
            tagBox = new TagBox(tagSet);
        }

    }

    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Checks if ingreidnetName meets specified requirements
     * 
     * @param ingredientName
     * @throws IllegalArgumentException
     */
    private void checkIngredientName(String ingredientName) throws IllegalArgumentException {

        if (ingredientName.length() < 3)
            throw new IllegalArgumentException("Too short Ingredient-name");

        if (ingredientName.length() > 50)
            throw new IllegalArgumentException("Too long Ingredient-name");

        boolean iNameMatch = Pattern.matches(INGNAMEREG_STRING, ingredientName);

        if (!iNameMatch)
            throw new IllegalArgumentException("Ingredient-name can only consist of chars");
    }

    /**
     * Sets new name of Ingredient
     * 
     * @param ingredientName
     * @throws IllegalArgumentException if unvalid name per
     *                                  {@linkplain #checkIngredientName(String)}
     */
    public void setIngredientName(String ingredientName) throws IllegalArgumentException {
        checkIngredientName(ingredientName);
        this.ingredientName = ingredientName;
    }

    public Integer getIngredientAmount() {
        return ingredientAmount;
    }

    /**
     * Checks if amount meets specified requirements
     * 
     * @param ingredientAmount
     * @throws IllegalAmountException
     */
    private void checkIngredientAmount(Integer ingredientAmount) throws IllegalAmountException {
        if (ingredientAmount <= 0)
            throw new IllegalAmountException("The amount of ingredient must be greater than 0");

        if ((int) ingredientAmount > (int) (Math.pow(10, 6)))
            throw new IllegalAmountException("Way too much ingredients...");

    }

    /**
     * Sets new amount of Ingredient
     * 
     * @param ingredientAmount
     * @throws IllegalAmountException if unvalid input per
     *                                {@linkplain #checkIngredientAmount(Integer)}
     */
    public void setIngredientAmount(Integer ingredientAmount) throws IllegalAmountException {
        checkIngredientAmount(ingredientAmount);
        this.ingredientAmount = ingredientAmount;
    }

    public MEASUREMENTS getIngredientMeasurement() {
        return ingredientMeasurement;
    }

    /**
     * Converts measurement from String to enun format
     * 
     * @param ingredientMeasurement
     * @return measurement in enum format
     * @throws IllegalMeasurementException if unvalid measurement
     */
    private MEASUREMENTS convertIngredientMeasurement(String ingredientMeasurement) throws IllegalMeasurementException {
        try {
            return MEASUREMENTS.valueOf(ingredientMeasurement);
        } catch (Exception e) {
            throw new IllegalMeasurementException("Not accepted measurement-type: " + ingredientMeasurement);
        }
    }

    /**
     * Sets new measurement of Ingredient
     * 
     * @param ingredientMeasurement
     * @throws IllegalMeasurementException
     */
    public void setIngredientMeasurement(String ingredientMeasurement) throws IllegalMeasurementException {
        this.ingredientMeasurement = convertIngredientMeasurement(ingredientMeasurement);
    }

    /**
     * Sets new measurement of Ingredient
     * 
     * @param ingredientMeasurement
     */
    public void setIngredientMeasurement(MEASUREMENTS ingredientMeasurement) {
        this.ingredientMeasurement = ingredientMeasurement;
    }

    /**
     * Converts string[] to Set of strings
     * 
     * @param strArray
     * @return Set of Strings
     */
    private Set<String> strArrayToSet(String... strArray) {

        Set<String> set = new HashSet<>();
        for (String tag : strArray) {
            set.add(tag);
        }

        return set;
    }

    public Set<String> getTags() {
        return new HashSet<>(tagBox.getTags());
    }

    /**
     * Update ingredient with new amount
     * 
     * @param ingredientAmount
     * @throws IllegalAmountException if adding/removing too high amount
     */
    public void updateIngredient(Integer ingredientAmount)
            throws IllegalAmountException {
        setIngredientAmount(this.ingredientAmount + ingredientAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ingredient other = (Ingredient) obj;
        if (ingredientMeasurement != other.ingredientMeasurement)
            return false;
        if (ingredientName == null) {
            if (other.ingredientName != null)
                return false;
        } else if (!ingredientName.equals(other.ingredientName))
            return false;
        return true;
    }

    @Override
    public String toString() {

        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(
                ingredientName + " " + ingredientAmount.toString() + " " + ingredientMeasurement.toString() + ", "
                        + tagBox.toString());

        return sBuilder.toString();
    }

}
