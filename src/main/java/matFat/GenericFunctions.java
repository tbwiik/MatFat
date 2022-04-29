package matFat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import matFat.Objects.*;
import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalMeasurementException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalTagFormatException;

public class GenericFunctions {

    /**
     * Convert string to Ingredient
     * 
     * @param ingStr with every arguments separated by space
     * @return {@linkplain Ingredient}
     * @throws IllegalNameFormatException
     * @throws IllegalAmountException
     * @throws IllegalMeasurementException
     * @throws IllegalTagFormatException
     */
    public static Ingredient strToIng(String ingStr) throws IllegalNameFormatException, IllegalAmountException,
            IllegalMeasurementException, IllegalTagFormatException {
        String[] ingArgs = ingStr.strip().split(" ");
        // Set<String> tmp = new HashSet<>(Arrays.asList(ingArgs));
        // tmp.stream().forEach((item) -> item.strip());

        // return new Ingredient((String[]) tmp.toArray());
        return new Ingredient(ingArgs);
    }

    /**
     * Convert string to set
     * <p>
     * split on ","
     * 
     * @param str
     * @return set of string
     */
    public static Set<String> strToStrSet(String str) {
        // TODO add more validation here?
        Set<String> tmpSet = new HashSet<>();

        if (!str.strip().isEmpty()) {
            String[] tmpArray = str.strip().split(",");
            for (String item : tmpArray) {
                tmpSet.add(item.strip());
            }
        }

        return tmpSet;
    }

    /**
     * Converts string[] to Set of strings
     * 
     * @param strArray
     * @return Set of Strings
     */
    public static Set<String> strArrayToSet(String[] strArray) {

        Set<String> set = new HashSet<>();
        for (String tag : strArray) {
            set.add(tag);
        }

        return set;
    }

    /**
     * Converts String to Integer
     * 
     * @param str
     * @return Integer
     * @throws NumberFormatException if not able to parse string to int
     */
    public static Integer strToInt(String str) throws NumberFormatException {
        try {
            return Integer.parseInt(str.strip());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid number \nCannot parse ingredient-amount to int");
        }
    }

    /**
     * Converts string to char
     * 
     * @param str
     * @return
     * @throws IllegalDifficultyException if string with length != 1
     */
    public static char strToChar(String str) throws IllegalDifficultyException {
        char[] charArray = str.strip().toCharArray();
        if (charArray.length != 1)
            throw new IllegalDifficultyException("Not accepted character");
        return Character.toUpperCase(charArray[0]);
    }

    // TODO
    // private void showErrorMessage(String errorMessage) {
    // Alert alert = new Alert(AlertType.ERROR);
    // alert.setAlertType(alertType);
    // }

}
