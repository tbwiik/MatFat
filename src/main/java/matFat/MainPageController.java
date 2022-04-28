package matFat;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import matFat.Objects.Meal;
import matFat.Objects.Menu;

public class MainPageController {

    private MealDataBase mealDataBase;
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
            int numberOfMeals = GenericFunctions.strToInt(numbMealsTextField.getText());
            Set<String> tags = GenericFunctions.strToStrSet(tagsTextField.getText().strip());
            // TODO fix predicate
            Predicate predicate = null;
            List<Meal> meals = mealDataBase.getRandomMeals(predicate, numberOfMeals);
            menu = new Menu(meals, tags);

            menuInfoText.setText(menu.toString());

        } catch (Exception e) {
            menuInfoText.setText(e.getMessage());
        }

    }

    @FXML
    private void initialize() {
        try {
            mealDataBase = new MealDataBase();
        } catch (Exception e) {
            menuInfoText.setText("Error initializing file");
        }
    }
}
