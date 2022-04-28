package matFat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        return new Ingredient(ingArgs);
    }

    /**
     * split on ","
     * 
     * @param str
     * @return
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

    public static int strToInt(String str) throws IllegalArgumentException {
        try {
            return (int) Integer.parseInt(str.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Not valid number");
        }
    }

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
