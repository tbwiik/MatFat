package matFat;

import java.util.NoSuchElementException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import matFat.Objects.Ingredient;
import matFat.Objects.Meal;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalRecipeFormatException;
import matFat.exceptions.IllegalTagFormatException;

import matFat.filehandling.FileHandler;

public class EditMealController {

    Model model;
    Meal meal;

    @FXML
    TextField searchMealTextField, changeMealNameTextField, changeDifficultyTextField, addTagTextField,
            removeTagTextField,
            addIngredientTextField, removeIngredientTextField, addRecipeStepTextField, removeRecipeStepTextField;

    @FXML
    Text mealInfoText;

    @FXML
    Button searchMealButton, changeMealNameButton, changeDifficultyButton, addTagButton, removeTagButton,
            addIngredientButton, removeIngredientButton, addRecipeStepButton, removeRecipeStepButton, submitMealButton;

    // TODO move this to menu/modell??
    @FXML
    private void searchMeal() {

        boolean updated = false;

        String mealName = searchMealTextField.getText().strip();

        if (mealName.isBlank())
            return;

        for (Meal meal : model.getAllMeals())
            if (meal.getMealName().equals(mealName)) {
                updated = true;
                this.meal = meal;
            }

        // Reset meal to null if none is found
        if (!updated)
            meal = null;

        if (meal == null) {
            mealInfoText.setText("Could not find meal");
        } else {
            mealInfoText.setText(meal.toString());
        }
    }

    private void checkMealExist() throws NoSuchElementException {
        if (meal == null)
            throw new NoSuchElementException("No meal is selected");
    }

    // TODO reuse a lot of code
    @FXML
    private void changeMealName() {

        try {
            checkMealExist();

            String mealName = changeMealNameTextField.getText().strip();

            meal.setMealName(mealName);
            mealInfoText.setText(meal.toString());
            changeMealNameTextField.clear();
        } catch (IllegalNameFormatException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void changeDifficulty() {

        try {

            checkMealExist();

            char difficulty = Model.strToChar(changeDifficultyTextField.getText());

            meal.setDifficulty(difficulty);
            mealInfoText.setText(meal.toString());
            changeDifficultyTextField.clear();
        } catch (IllegalDifficultyException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void addTag() {

        try {
            checkMealExist();

            // TODO implement adding more tags
            String tag = addTagTextField.getText().strip();

            meal.addTag(tag);
            mealInfoText.setText(meal.toString());
            addTagTextField.clear();
        } catch (IllegalTagFormatException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void removeTag() {

        try {

            checkMealExist();

            String tag = removeTagTextField.getText().strip();

            meal.removeTag(tag);
            mealInfoText.setText(meal.toString());
            removeTagTextField.clear();
        } catch (IllegalNameFormatException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void addIngredient() {

        try {

            checkMealExist();

            Ingredient ing = Model.strToIng(addIngredientTextField.getText());
            meal.addIngredient(ing);
            mealInfoText.setText(meal.toString());
            addIngredientTextField.clear();
        } catch (IllegalArgumentException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void removeIngredient() {

        try {

            checkMealExist();

            Ingredient ing = Model.strToIng(removeIngredientTextField.getText());
            meal.removeIngredient(ing);
            mealInfoText.setText(meal.toString());
            removeIngredientTextField.clear();

        } catch (IllegalArgumentException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void addRecipeStep() {

        try {
            checkMealExist();

            String recipeStep = addRecipeStepTextField.getText().strip();

            meal.addRecipeStep(recipeStep);
            mealInfoText.setText(meal.toString());
            addRecipeStepTextField.clear();
        } catch (IllegalRecipeFormatException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void removeRecipeStep() {

        try {

            checkMealExist();

            int index = Model.strToInt(removeRecipeStepTextField.getText());

            meal.removeRecipeStep(index);
            mealInfoText.setText(meal.toString());
            removeRecipeStepTextField.clear();
        } catch (IllegalArgumentException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        try {
            model = new Model();
        } catch (Exception e) {
            mealInfoText.setText("Error initializing file");
        }
    }

    @FXML
    private void submitMeal() {
        // TODO
    }

}
