package matFat;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private Button generateMenuButton, addMealToMenuButton, removeMealFromMenuButton, addNewMealButton,
            editExistingMealButton;

    private void showMenu() {
        menuInfoText.setText(menu.toString());
    }

    @FXML
    private void generateMenu() {

        try {
            int numberOfMeals = GenericFunctions.strToInt(numbMealsTextField.getText());
            Set<String> tags = GenericFunctions.strToStrSet(tagsTextField.getText().strip());
            List<Meal> meals = mealDataBase.getRandomMeals(tags, numberOfMeals);
            menu = new Menu(meals, tags);

            showMenu();

        } catch (Exception e) {
            menuInfoText.setText(e.getMessage());
        }

    }

    @FXML
    private void addMealToMenu() {
        // TODO
    }

    @FXML
    private void removeMealFromMenu() {
        // TODO
    }

    @FXML
    private void addRandomMeal() {
        menu.addMeal(mealDataBase.getRandomMeal(menu.getTags()));
        showMenu();
    }

    // XXX This code is cooked from Arran
    @FXML
    private void addNewMeal(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AddMeal.fxml"));
        Parent p = fxmlLoader.load();
        Scene s = new Scene(p);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Add new meal");
        window.setScene(s);
        window.show();

    }

    @FXML
    private void editExistingMeal(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("EditMeal.fxml"));
        Parent p = fxmlLoader.load();
        Scene s = new Scene(p);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Edit meal");
        window.setScene(s);
        window.show();

    }

    private void removeMeal() {
        // TODO
        String mealName = null;
        EditMealController.searchMeal(mealName, mealDataBase);
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
