package com.culinary.app.utils;

import android.content.Context;
import com.culinary.app.R;
import com.culinary.app.models.Recipe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeRepository {

    public static List<Recipe> getAllRecipes(Context context) {
        List<Recipe> recipes = new ArrayList<>();

        // ---- BREAKFAST ----
        recipes.add(new Recipe(
                1,
                context.getString(R.string.recipe_pancakes_name),
                context.getString(R.string.recipe_pancakes_desc),
                "breakfast",
                "russian",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pancakes_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pancakes_steps)),
                30, 4,0, 1
        ));

        recipes.add(new Recipe(
                2,
                context.getString(R.string.recipe_omelette_name),
                context.getString(R.string.recipe_omelette_desc),
                "breakfast",
                "french",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_omelette_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_omelette_steps)),
                15, 2, 0, 1
        ));

        recipes.add(new Recipe(
                3,
                context.getString(R.string.recipe_granola_name),
                context.getString(R.string.recipe_granola_desc),
                "breakfast",
                "american",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_granola_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_granola_steps)),
                25, 3, 0, 1
        ));

        // ---- LUNCH ----
        recipes.add(new Recipe(
                4,
                context.getString(R.string.recipe_borsch_name),
                context.getString(R.string.recipe_borsch_desc),
                "lunch",
                "russian",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_borsch_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_borsch_steps)),
                90, 6, 0, 2
        ));

        recipes.add(new Recipe(
                5,
                context.getString(R.string.recipe_pasta_name),
                context.getString(R.string.recipe_pasta_desc),
                "lunch",
                "italian",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pasta_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pasta_steps)),
                40, 4, 0, 2
        ));

        recipes.add(new Recipe(
                6,
                context.getString(R.string.recipe_schnitzel_name),
                context.getString(R.string.recipe_schnitzel_desc),
                "lunch",
                "german",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_schnitzel_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_schnitzel_steps)),
                35, 2, 0, 2
        ));

        // ---- DINNER ----
        recipes.add(new Recipe(
                7,
                context.getString(R.string.recipe_salmon_name),
                context.getString(R.string.recipe_salmon_desc),
                "dinner",
                "french",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_salmon_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_salmon_steps)),
                30, 2, 0, 2
        ));

        recipes.add(new Recipe(
                8,
                context.getString(R.string.recipe_pelmeni_name),
                context.getString(R.string.recipe_pelmeni_desc),
                "dinner",
                "russian",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pelmeni_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pelmeni_steps)),
                60, 4, 0, 3
        ));

        recipes.add(new Recipe(
                9,
                context.getString(R.string.recipe_pizza_name),
                context.getString(R.string.recipe_pizza_desc),
                "dinner",
                "italian",
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pizza_ingredients)),
                Arrays.asList(context.getResources().getStringArray(R.array.recipe_pizza_steps)),
                75, 4, 0, 3
        ));

        return recipes;
    }

    public static List<Recipe> filterByCategory(Context context, String category) {
        List<Recipe> all = getAllRecipes(context);
        if (category == null || category.equals("all")) return all;
        List<Recipe> filtered = new ArrayList<>();
        for (Recipe r : all) {
            if (r.getCategory().equals(category)) filtered.add(r);
        }
        return filtered;
    }
}
