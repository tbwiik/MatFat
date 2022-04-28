package matFat.Objects;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import matFat.GenericFunctions;
import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalIngredientFormatException;
import matFat.exceptions.IllegalMeasurementException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalTagFormatException;

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
     * @throws IllegalNameFormatException
     * @throws IllegalAmountException
     * @throws IllegalTagFormatException
     */
    public Ingredient(String ingredientName, Integer ingredientAmount, MEASUREMENTS ingredientMeasurement,
            String... tags)
            throws IllegalNameFormatException, IllegalAmountException, IllegalTagFormatException {
        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        this.ingredientMeasurement = ingredientMeasurement;
        this.tagBox = new TagBox(GenericFunctions.strArrayToSet(tags));
    }

    // TODO put these together?
    /**
     * Constructs Ingredient object where measurement is string
     * 
     * @param ingredientName
     * @param ingredientAmount
     * @param ingredientMeasurement
     * @param tags
     * @throws IllegalNameFormatException
     * @throws IllegalAmountException
     * @throws IllegalMeasurementException
     * @throws IllegalTagFormatException
     */
    public Ingredient(String ingredientName, Integer ingredientAmount, String ingredientMeasurement, String... tags)
            throws IllegalNameFormatException, IllegalAmountException, IllegalMeasurementException,
            IllegalTagFormatException {

        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        setIngredientMeasurement(ingredientMeasurement);
        this.tagBox = new TagBox(GenericFunctions.strArrayToSet(tags));
    }

    /**
     * Constructs Ingredient with string array
     * <p>
     * Used in filehandling and ui
     * 
     * @param ingArgs name, amount, measurement, tags...
     * @throws IllegalIngredientFormatException
     * @throws NumberFormatException
     * @throws IllegalMeasurementException
     * @throws IIllegalTagFormatException
     */
    public Ingredient(String[] ingArgs) throws IllegalIngredientFormatException, NumberFormatException,
            IllegalMeasurementException, IllegalTagFormatException {

        if (ingArgs.length < 3)
            throw new IllegalIngredientFormatException(
                    "Too few arguments to create ingredient \n Must be on the format \"name amount measurement\"");

        setIngredientName(ingArgs[0]);
        try {
            setIngredientAmount(Integer.parseInt(ingArgs[1]));
        } catch (NumberFormatException nFormatException) {
            throw new NumberFormatException("Invalid number \nCannot parse ingredient-amount to int");
        }
        setIngredientMeasurement(ingArgs[2]);

        setTags(ingArgs);

    }

    /**
     * Checks if ingreidnetName meets specified requirements
     * 
     * @param ingredientName
     * @throws IllegalNameFormatException
     */
    private void checkIngredientName(String ingredientName) throws IllegalNameFormatException {

        if (ingredientName.length() < 3)
            throw new IllegalNameFormatException("Too short Ingredient-name");

        if (ingredientName.length() > 50)
            throw new IllegalNameFormatException("Too long Ingredient-name");

        boolean iNameMatch = Pattern.matches(INGNAMEREG_STRING, ingredientName);

        if (!iNameMatch)
            throw new IllegalNameFormatException("Ingredient-name can only consist of chars");
    }

    /**
     * Sets new name of Ingredient
     * 
     * @param ingredientName
     * @throws IllegalNameFormatException if unvalid name per
     *                                    {@linkplain #checkIngredientName(String)}
     */
    public void setIngredientName(String ingredientName) throws IllegalNameFormatException {
        checkIngredientName(ingredientName);
        this.ingredientName = ingredientName;
    }

    /**
     * Checks if amount for ingredient meets specified requirements
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

    /**
     * Set tags based on array
     * <p>
     * Use same array as ingredient and will therefore start on index 3
     * 
     * @param ingArgs
     * @throws IllegalTagFormatException
     */
    private void setTags(String[] ingArgs) throws IllegalTagFormatException {
        if (ingArgs.length > 3) {
            Set<String> tagSet = new HashSet<>();
            for (int i = 3; i < ingArgs.length; i++)
                tagSet.add(ingArgs[i]);
            tagBox = new TagBox(tagSet);
        }
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

    public String getIngredientName() {
        return ingredientName;
    }

    public Integer getIngredientAmount() {
        return ingredientAmount;
    }

    public MEASUREMENTS getIngredientMeasurement() {
        return ingredientMeasurement;
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

    /**
     * ingredient equals another if name and measurement is the same
     */
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
