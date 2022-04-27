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
import matFat.filehandling.*;

public class AddMealController {

    Model model;
    Meal meal;
    IngredientContainer ingredientContainer = new IngredientContainer(); // Use this because of validation
    Recipe recipe; // Use this because of validation
    // TODO implement differently?

    @FXML
    TextField mealNameTextField, difficultyTextField, tagsTextField, ingredientTextField, ingredientTagsTextField,
            recipePointTextField;

    @FXML
    Button addIngredientButton, addRecipePointButton, addMealButton;

    @FXML
    Text mealInfoText;

    @FXML
    private void addIngredient() throws IllegalArgumentException {

        String ingStr = ingredientTextField.getText();

        if (ingStr.isBlank())
            return;

        // TODO move this into an own function?? or use a TagBox to format?
        String ingTagsStr = ingredientTagsTextField.getText().replace(",", ""); // Format tags correctly for
                                                                                // appending to ingredient
        Ingredient ing = Model.strToIng(ingStr + " " + ingTagsStr);

        if (ingredientContainer == null) {
            ingredientContainer = new IngredientContainer(ing);
        } else {
            ingredientContainer.addIngredient(ing);
        }

        ingredientTextField.clear();
        ingredientTagsTextField.clear();
        mealInfoText.setText("Ingredients: \n" + ingredientContainer.toString());
    }

    @FXML
    private void addRecipePoint() throws IllegalArgumentException {

        String recLine = recipePointTextField.getText().strip();

        if (recLine.isBlank())
            return;

        if (recipe == null) {
            recipe = new Recipe(recLine);
        } else {
            recipe.addRecipeLine(recLine);
        }

        recipePointTextField.setText("");
        mealInfoText.setText("Recipe: \n" + recipe.toString());
    }

    @FXML
    private void addMeal() {

        try {

            String mealName = mealNameTextField.getText().strip();
            char difficulty = Model.strToChar(difficultyTextField.getText());
            Set<String> tags = Model.strToStrSet(tagsTextField.getText());

            addIngredient();
            List<Ingredient> ingredients = ingredientContainer.getIngredients();

            addRecipePoint();
            List<String> recipeList = recipe.getRecipe();

            meal = new Meal(mealName, difficulty, ingredients, recipeList, tags);
            mealInfoText.setText(meal.toString());

            // XXX better way to do this??
            mealNameTextField.setText("");
            difficultyTextField.setText("");
            tagsTextField.setText("");

            // TODO how to implement adding meals to allMealsList between different
            // controllers??

            // TODO Change exception catch?
        } catch (Exception e) {
            mealInfoText.setText(e.getMessage());
        }

        ingredientContainer = null;
        recipe = null;
    }

    @FXML
    private void initialize() {
        try {
            FileHandler fHandler = new FileHandler();
            List<Meal> allMeals = fHandler.readFromFile("testfile").getMealList();
            model = new Model(allMeals);
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
