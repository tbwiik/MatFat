package matFat;

import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class MatFatController {

    private Menu menu;
    // XXX Many of the same attributes as meal. extend instead??
    private Set<String> tags = new HashSet<>();
    private Integer numberOfMeals;

    @FXML
    private TextField tagsTextField, numbMealsTextField;

    @FXML
    private Text menuInfoText;

    @FXML
    private Button generateMenuButton, addRecipeButton;

    // Fix this
    @FXML
    private void generateMenuButton() {
        // TODO add actual functionality. Current block only for testing
        menu = new Menu(tags, numberOfMeals);
        try {
            menuInfoText.setText("This is your dinnah");
        } catch (Exception e) {
            // TODO change catch here...
            menuInfoText.setText("Shit happened");
        }

    }
}
