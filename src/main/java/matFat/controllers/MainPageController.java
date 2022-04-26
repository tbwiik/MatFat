package matFat.controllers;

import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import matFat.Menu;

public class MainPageController {

    private Menu menu;
    private Set<String> tags = new HashSet<>();
    private Integer numberOfMeals;

    @FXML
    private TextField tagsTextField, numbMealsTextField;

    @FXML
    private Text menuInfoText;

    @FXML
    private Button generateMenuButton, addRecipeButton;

    // Fix this asdjb
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
