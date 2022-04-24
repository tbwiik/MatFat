package matFat;

//TODO change name? Kiiind of not used for illegal measurement. See checks in @Ingredient
public class IllegalMeasurementException extends IllegalArgumentException {

    public IllegalMeasurementException(String errorMessage) {
        super(errorMessage);
    }

}