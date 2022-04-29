package matFat;

import java.io.IOException;
import java.util.NoSuchElementException;

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
import matFat.Objects.Ingredient;
import matFat.Objects.Meal;
import matFat.exceptions.IllegalDifficultyException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalRecipeFormatException;
import matFat.exceptions.IllegalTagFormatException;

public class EditMealController {

    MealDataBase mealDataBase;
    Meal meal, oldMeal;

    @FXML
    TextField searchMealTextField, changeMealNameTextField, changeDifficultyTextField, addTagTextField,
            removeTagTextField,
            addIngredientTextField, removeIngredientTextField, addRecipeStepTextField, removeRecipeStepTextField;

    @FXML
    Text mealInfoText;

    @FXML
    Button searchMealButton, changeMealNameButton, changeDifficultyButton, addTagButton, removeTagButton,
            addIngredientButton, removeIngredientButton, addRecipeStepButton, removeRecipeStepButton, submitMealButton,
            removeMealButton,
            returnToStartPageButton;

    /**
     * Search for meal in database
     * 
     * @param mealName
     * @param mealDataBase
     * @return null or {@linkplain Meal}
     */
    public static Meal searchMeal(String mealName, MealDataBase mealDataBase) {

        if (mealName.isBlank())
            return null;

        for (Meal meal : mealDataBase.getMeals()) {
            if (meal.getMealName().equals(mealName)) {
                return meal;
            }
        }

        return null;

    }

    /**
     * Search for Meal in database
     */
    @FXML
    private void searchMeal() {

        String mealName = searchMealTextField.getText().strip();

        Meal tmpMeal = searchMeal(mealName, mealDataBase);

        if (tmpMeal == null) {
            mealInfoText.setText("Could not find meal");
        } else {
            meal = tmpMeal; // Only update meal if it actually exist
            mealInfoText.setText(meal.toString());
        }
    }

    /**
     * Check if meal is asigned
     * 
     * @throws NoSuchElementException
     */
    private void checkMealExist() throws NoSuchElementException {
        if (meal == null)
            throw new NoSuchElementException("No meal is selected");
    }

    /**
     * Change name of meal
     */
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

    /**
     * Change difficulty of meal
     */
    @FXML
    private void changeDifficulty() {

        try {

            checkMealExist();

            char difficulty = GenericFunctions.strToChar(changeDifficultyTextField.getText());

            meal.setDifficulty(difficulty);
            mealInfoText.setText(meal.toString());
            changeDifficultyTextField.clear();
        } catch (IllegalDifficultyException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    /**
     * Add tag to meal
     */
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

    /**
     * Remove tag from meal
     */
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

    /**
     * Add ingredient to meal
     */
    @FXML
    private void addIngredient() {

        try {

            checkMealExist();

            Ingredient ing = GenericFunctions.strToIng(addIngredientTextField.getText());
            meal.addIngredient(ing);
            mealInfoText.setText(meal.toString());
            addIngredientTextField.clear();
        } catch (IllegalArgumentException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    /**
     * Remove ingredient from meal
     */
    @FXML
    private void removeIngredient() {

        try {

            checkMealExist();

            Ingredient ing = GenericFunctions.strToIng(removeIngredientTextField.getText());
            meal.removeIngredient(ing);
            mealInfoText.setText(meal.toString());
            removeIngredientTextField.clear();

        } catch (IllegalArgumentException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    /**
     * Add step in recipe
     */
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

    /**
     * Remove step in recipe based on index
     */
    @FXML
    private void removeRecipeStep() {

        try {

            checkMealExist();

            int index = GenericFunctions.strToInt(removeRecipeStepTextField.getText());

            meal.removeRecipeStep(index);
            mealInfoText.setText(meal.toString());
            removeRecipeStepTextField.clear();
        } catch (IllegalArgumentException | NoSuchElementException e) {
            mealInfoText.setText(e.getMessage());
        }
    }

    /**
     * Write meal to file
     */
    @FXML
    private void submitMeal() {

        checkMealExist();

        mealDataBase.addMeal(meal);
        mealInfoText.setText("Meal submitted");

    }

    /**
     * Remove meal from file
     */
    @FXML
    private void removeMeal() {

        checkMealExist();

        mealDataBase.removeMeal(meal);
        mealInfoText.setText("Meal succesfully removed");

    }

    /**
     * Returns to startpage
     * 
     * @param event
     */
    @FXML
    private void returnToStartPage(ActionEvent event) {

        try {
            MainPageController.changeScene(event, "MainPage.fxml", "Start Page");
        } catch (Exception e) {
            mealInfoText.setText("Could not return to start page");
        }
    }

    @FXML
    private void initialize() {
        try {
            mealDataBase = new MealDataBase();
        } catch (Exception e) {
            mealInfoText.setText("Error initializing file\n" + e.getMessage());
        }
    }

}
