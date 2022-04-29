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
import matFat.filehandling.ManageData;

public class MainPageController {

    private MealDataBase mealDataBase;
    private ManageData manageData;
    private Menu menu;

    @FXML
    private TextField tagsTextField, numbMealsTextField;

    @FXML
    private Text menuInfoText;

    @FXML
    private Button generateMenuButton, addMealToMenuButton, removeMealFromMenuButton, addNewMealButton,
            editExistingMealButton;

    /**
     * Show menu on ui
     */
    private void showMenu() {
        menuInfoText.setText(menu.toString());
    }

    /**
     * If possible generates a menu based on number of meals and usertags
     * <p>
     * Show possible error on ui
     * <p>
     * Keyfunction {@linkplain MealDataBase#getRandomMeals(Set, int)}
     */
    @FXML
    private void generateMenu() {

        try {

            int numberOfMeals = GenericFunctions.strToInt(numbMealsTextField.getText());
            Set<String> tags = GenericFunctions.strToStrSet(tagsTextField.getText().strip());
            List<Meal> meals = mealDataBase.getRandomMeals(tags, numberOfMeals);
            menu = new Menu(meals, tags);
            manageData.writeMenuToFile(menu, "menu");
            showMenu();

        } catch (Exception e) {
            e.printStackTrace();
            menuInfoText.setText(e.getMessage());
        }

    }

    // Not implemented in UI
    @FXML
    private void addRandomMeal() {
        menu.addMeal(mealDataBase.getRandomMeal(menu.getTags()));
        showMenu();
    }

    /**
     * Send to {@linkplain AddMealController} where adding meal to database is
     * handled
     */
    @FXML
    private void addNewMeal(ActionEvent event) {

        try {
            changeScene(event, "AddMeal.fxml", "Add new meal");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            menuInfoText.setText("Could not change page");
        }

    }

    /**
     * Send to {@linkplain EditMealController} where editing or removing meal to
     * database is handled
     */
    @FXML
    private void editExistingMeal(ActionEvent event) {

        try {
            changeScene(event, "EditMeal.fxml", "Edit meal");
        } catch (IOException ioe) {
            ioe.printStackTrace();
            menuInfoText.setText("Could not change page");
        }
    }

    /**
     * Change page (scene) in program
     * <p>
     * Retrieved from studass Arran Gabriel Kostveit 29.04.2022
     * https://github.com/arrangabriel/IT1901-project-gr23/blob/master/get-fit/ui/src/main/java/ui/StartPageController.java
     * 
     * @param event
     * @param scene      changing to
     * @param sceneTitle title of new scene
     * @throws IOException
     */
    public static void changeScene(ActionEvent event, String scene, String sceneTitle) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainPageController.class.getResource(scene));
        Parent p = fxmlLoader.load();
        Scene s = new Scene(p);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle(sceneTitle);
        window.setScene(s);
        window.show();

    }

    @FXML
    private void initialize() {
        try {
            manageData = new ManageData();
            mealDataBase = new MealDataBase();
            menuInfoText.setText("Start succesfull");
        } catch (Exception e) {
            menuInfoText.setText("Error initializing file\n" + e.getMessage());
        }
    }
}
