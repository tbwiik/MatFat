package matFat;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import matFat.Objects.Ingredient;
import matFat.Objects.IngredientContainer;
import matFat.Objects.Meal;
import matFat.Objects.Recipe;
import matFat.exceptions.IllegalAmountException;
import matFat.exceptions.IllegalMeasurementException;
import matFat.exceptions.IllegalNameFormatException;
import matFat.exceptions.IllegalRecipeFormatException;
import matFat.exceptions.IllegalTagFormatException;

public class AddMealController {

    MealDataBase mealDataBase;
    Meal meal;
    IngredientContainer ingredientContainer = new IngredientContainer();
    Recipe recipe = new Recipe();

    @FXML
    TextField mealNameTextField, difficultyTextField, tagsTextField, ingredientTextField, ingredientTagsTextField,
            recipeStepTextField;

    @FXML
    Button addIngredientButton, addRecipeStepButton, addMealButton, returnToStartPageButton;

    @FXML
    Text mealInfoText;

    /**
     * Add ingredient to meal
     */
    @FXML
    private void addIngredient() {

        String ingStr = ingredientTextField.getText();

        if (ingStr.isBlank())
            return;

        try {
            // TODO fix problem with multiple whitespaces
            String ingTagsStr = ingredientTagsTextField.getText().replace(",", ""); // Format tags correctly for
                                                                                    // appending to ingredient
            Ingredient ing = GenericFunctions.strToIng(ingStr + " " + ingTagsStr);

            ingredientContainer.addIngredient(ing);

            ingredientTextField.clear();
            ingredientTagsTextField.clear();
            mealInfoText.setText(ingredientContainer.toString());

        } catch (IllegalNameFormatException | IllegalAmountException | IllegalMeasurementException
                | IllegalTagFormatException exception) {
            mealInfoText.setText(exception.getMessage());
        }
        // TODO move this into an own function?? or use a TagBox to format?

    }

    /**
     * Add step in recipe to meal
     */
    @FXML
    private void addRecipeStep() {

        String recLine = recipeStepTextField.getText().strip();

        if (recLine.isBlank())
            return;

        try {
            recipe.addRecipeLine(recLine);
            recipeStepTextField.clear();
            mealInfoText.setText(recipe.toString());
        } catch (IllegalRecipeFormatException e) {
            mealInfoText.setText(e.getMessage());
        }

    }

    /**
     * Write meal to file
     */
    @FXML
    private void addMeal() {

        try {

            String mealName = mealNameTextField.getText().strip();
            char difficulty = GenericFunctions.strToChar(difficultyTextField.getText());
            Set<String> tags = GenericFunctions.strToStrSet(tagsTextField.getText());

            addIngredient();
            List<Ingredient> ingredients = ingredientContainer.getIngredients();

            addRecipeStep();
            List<String> recipeList = recipe.getRecipe();

            meal = new Meal(mealName, difficulty, ingredients, recipeList, tags);
            mealInfoText.setText(meal.toString());

            mealNameTextField.clear();
            difficultyTextField.clear();
            tagsTextField.clear();

            ingredientContainer = new IngredientContainer();
            recipe = new Recipe();

            mealDataBase.addMeal(meal); // write meal to database

            // TODO Change exception catch?
        } catch (Exception e) {
            mealInfoText.setText(e.getMessage());
        }

    }

    /**
     * Return to startpage
     * 
     * @param event
     */
    @FXML
    private void returnToStartPage(ActionEvent event) {

        try {
            MainPageController.changeScene(event, "MainPage.fxml", "Start Page");
        } catch (Exception e) {
            mealInfoText.setText("Unable to return to start page");
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
