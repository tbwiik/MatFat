package matFat;

import java.util.List;
import java.util.Set;

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
import matFat.filehandling.*;

public class AddMealController {

    GenericFunctions model;
    Meal meal;
    IngredientContainer ingredientContainer = new IngredientContainer();
    Recipe recipe = new Recipe();

    @FXML
    TextField mealNameTextField, difficultyTextField, tagsTextField, ingredientTextField, ingredientTagsTextField,
            recipeStepTextField;

    @FXML
    Button addIngredientButton, addRecipeStepButton, addMealButton;

    @FXML
    Text mealInfoText;

    @FXML
    private void addIngredient() {

        String ingStr = ingredientTextField.getText();

        if (ingStr.isBlank())
            return;

        try {
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

            // XXX better way to do this??
            mealNameTextField.setText("");
            difficultyTextField.setText("");
            tagsTextField.setText("");

            ingredientContainer = new IngredientContainer();
            recipe = new Recipe();

            // TODO how to implement adding meals to allMealsList between different
            // controllers?? - save to file between each

            // TODO Change exception catch?
        } catch (Exception e) {
            mealInfoText.setText(e.getMessage());
        }

    }

    @FXML
    private void initialize() {
        try {
            model = new GenericFunctions();
        } catch (Exception e) {
            mealInfoText.setText("Error initializing file");
        }
    }

    public static void main(String[] args) {
        String str = "This, is, a, test";
        String str2 = str.replaceAll(",", "");
        System.out.println(str2);
    }

}
