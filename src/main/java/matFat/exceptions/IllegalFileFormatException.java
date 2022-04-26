package matFat.exceptions;

// TODO change super-extension
public class IllegalFileFormatException extends IllegalArgumentException {

    public IllegalFileFormatException(String errorMessage) {
        super(errorMessage);
    }
}
