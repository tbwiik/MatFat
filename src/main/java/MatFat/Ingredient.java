package matFat;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Ingredient {

    private String ingredientName;
    private Integer ingredientAmount;
    private MEASUREMENTS ingredientMeasurement;
    private Set<String> tags = new HashSet<>();

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

    private final static String NAMEREG_STRING = "[a-zA-ZæøåÆØÅ]*";

    // Constructor for enum measurement
    public Ingredient(String ingredientName, Integer ingredientAmount, MEASUREMENTS ingredientMeasurement,
            String... tags)
            throws IllegalArgumentException {
        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        this.ingredientMeasurement = ingredientMeasurement;
        setTags(tags);
    }

    // Constructor for String measurement
    public Ingredient(String ingredientName, Integer ingredientAmount, String ingredientMeasurement, String... tags)
            throws IllegalArgumentException {

        setIngredientName(ingredientName);
        setIngredientAmount(ingredientAmount);
        setIngredientMeasurement(ingredientMeasurement);
        setTags(tags);
    }

    // Constructor for String arguments
    /**
     * Constructs Ingredient based on String args.
     * <p>
     * Used in filehandling
     * 
     * @param ingArgs name, amount, measurement, tags
     * @throws IllegalArgumentException
     */
    public Ingredient(String[] ingArgs) throws IllegalArgumentException {
        setIngredientName(ingArgs[0]);
        try {
            setIngredientAmount(Integer.parseInt(ingArgs[1]));
        } catch (NumberFormatException nFormatException) {
            throw new IllegalArgumentException("Cannot parse ingredient-amount to int");
        }
        setIngredientMeasurement(ingArgs[2]);

        for (int i = 3; i < ingArgs.length; i++) {
            tags.add(ingArgs[i]);
        }

    }

    public String getIngredientName() {
        return ingredientName;
    }

    private void checkIngredientName(String ingredientName) throws IllegalArgumentException {

        if (ingredientName.length() < 3)
            throw new IllegalArgumentException("Too short Ingredient-name");

        if (ingredientName.length() > 50)
            throw new IllegalArgumentException("Too long Ingredient-name");

        boolean iNameMatch = Pattern.matches(NAMEREG_STRING, ingredientName);

        if (!iNameMatch)
            throw new IllegalArgumentException("Ingredient-name can only consist of chars");
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

        // XXX Change datatype?
        if ((int) ingredientAmount > (int) (Math.pow(10, 6)))
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

    // Basicly same as checkIngredientName
    // Static because of use in Meal
    public static void checkTag(String tag) throws IllegalArgumentException {
        if (tag.length() < 3)
            throw new IllegalArgumentException("Too short tag");

        if (tag.length() > 10)
            throw new IllegalArgumentException("Too long tag");

        boolean iNameMatch = Pattern.matches(NAMEREG_STRING, tag);

        if (!iNameMatch)
            throw new IllegalArgumentException("Tag can only consist of chars");
    }

    public void setTags(String... tags) throws IllegalArgumentException {
        for (String tag : tags) {
            checkTag(tag);
            this.tags.add(tag);
        }
    }

    public Set<String> getTags() {
        return tags;
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
        if (ingredientName == null) {
            if (other.ingredientName != null)
                return false;
        } else if (!ingredientName.toLowerCase().equals(other.ingredientName.toLowerCase()))
            return false;
        return true;
    }

    // TODO Implement String input as measurement?
    public void updateIngredient(Integer ingredientAmount, MEASUREMENTS ingredientMeasurement)
            throws IllegalMeasurementException {
        if (!this.ingredientMeasurement.equals(ingredientMeasurement))
            throw new IllegalMeasurementException("Not similar measurement-types");

        setIngredientAmount(ingredientAmount);
    }

    @Override
    public String toString() {
        // TODO write better using StringBuilder
        return "Ingredient [ingredientAmount=" + ingredientAmount + ", ingredientMeasurement=" + ingredientMeasurement
                + ", ingredientName=" + ingredientName + "]";
    }

}
