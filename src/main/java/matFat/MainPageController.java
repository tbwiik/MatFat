package matFat;

import java.util.List;
import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import matFat.Objects.Meal;
import matFat.Objects.Menu;
import matFat.filehandling.FileHandler;

public class MainPageController {

    private Model model;
    private Menu menu;

    @FXML
    private TextField tagsTextField, numbMealsTextField;

    @FXML
    private Text menuInfoText;

    @FXML
    private Button generateMenuButton;

    @FXML
    private void generateMenu() {

        try {
            int numberOfMeals = Model.strToInt(numbMealsTextField.getText());
            Set<String> tags = Model.strToStrSet(tagsTextField.getText().strip());

            menu = model.generateMenu(numberOfMeals, tags);

            menuInfoText.setText(menu.toString());

        } catch (Exception e) {
            menuInfoText.setText(e.getMessage());
        }

    }

    @FXML
    private void initialize() {
        try {
            FileHandler fHandler = new FileHandler();
            List<Meal> allMeals = fHandler.readFromFile("testfile").getMealList();
            model = new Model(allMeals);
        } catch (Exception e) {
            menuInfoText.setText("Error initializing file");
        }
    }
}
