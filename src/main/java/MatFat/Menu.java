package matFat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    private List<Meal> menu;

    // XXX For Test purposes only. Convert to database
    private Collection<Meal> allMeals = new ArrayList<>();
    private Meal m1, m2, m3;

    private List<Ingredient> il1 = new ArrayList<>();
    private List<Ingredient> il2 = new ArrayList<>();
    private List<Ingredient> il3 = new ArrayList<>();

    private Set<String> ts1 = new HashSet<>();
    private Set<String> ts2 = new HashSet<>();
    private Set<String> ts3 = new HashSet<>();

    private Ingredient i11 = new Ingredient("Burgerbrod", 2, "stk");
    private Ingredient i12 = new Ingredient("Salat", 1, "stk");
    private Ingredient i13 = new Ingredient("Veganburger", 2, "stk");

    private Ingredient i21 = new Ingredient("TomatsuppeToro", 1, "stk");
    private Ingredient i22 = new Ingredient("Polse", 500, "g");
    private Ingredient i23 = new Ingredient("Makaroni", 500, "g");

    private Ingredient i31 = new Ingredient("Pasta", 1, "pk");
    private Ingredient i32 = new Ingredient("Egg", 2, "stk");
    private Ingredient i33 = new Ingredient("Meat", 400, "g");

    private String tagEasy = "easy";
    private String tagVegan = "vegan";
    private String tagQuick = "quick";
    private String tagMeat = "meat";

    private void initialize() {

        il1.add(i11);
        il1.add(i12);
        il1.add(i13);

        ts1.add(tagVegan);
        ts1.add(tagEasy);
        ts1.add(tagQuick);
        m1 = new Meal("Veganburger", 'E', il1, ts1);

        il2.add(i21);
        il2.add(i22);
        il2.add(i23);

        ts2.add(tagEasy);
        ts2.add(tagQuick);
        m2 = new Meal("Tomatsuppe", 'E', il2, ts2);

        il3.add(i31);
        il3.add(i32);
        il3.add(i33);

        ts3.add(tagMeat);
        ts3.add(tagEasy);
        m3 = new Meal("Pasta Carbonara", 'M', il3, ts3);

        allMeals.add(m1);
        allMeals.add(m2);
        allMeals.add(m3);
    }

    public Menu(
            Set<String> tags, Integer numberOfMeals) {
        initialize();

        // TODO not correct, only for test purposes
        menu.addAll(allMeals);
    }

    public Menu() {
        initialize();
        menu.addAll(allMeals);
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        System.out.println(menu.getMenu());
    }
}
