package com.culinary.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.culinary.app.R;
import com.culinary.app.models.Recipe;
import com.culinary.app.utils.LocaleHelper;
import com.culinary.app.utils.RecipeRepository;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        int recipeId = getIntent().getIntExtra("recipe_id", 1);
        Recipe recipe = findRecipeById(recipeId);

        if (recipe != null) {
            bindRecipe(recipe);
        }
    }

    private Recipe findRecipeById(int id) {
        List<Recipe> all = RecipeRepository.getAllRecipes(this);
        for (Recipe r : all) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    private void bindRecipe(Recipe recipe) {
        TextView tvName = findViewById(R.id.tv_detail_name);
        TextView tvDescription = findViewById(R.id.tv_detail_description);
        LinearLayout llIngredients = findViewById(R.id.ll_ingredients);
        LinearLayout llSteps = findViewById(R.id.ll_steps);
        TextView tvCategoryBadge = findViewById(R.id.tv_category_badge);

        tvName.setText(recipe.getName());
        tvDescription.setText(recipe.getDescription());


        // Category badge
        String catKey = recipe.getCategory();
        int catStringRes;
        switch (catKey) {
            case "breakfast": catStringRes = R.string.breakfast; break;
            case "lunch": catStringRes = R.string.lunch; break;
            default: catStringRes = R.string.dinner; break;
        }
        tvCategoryBadge.setText(getString(catStringRes));

        // Ingredients
        llIngredients.removeAllViews();
        for (String ingredient : recipe.getIngredients()) {
            TextView tv = new TextView(this);
            tv.setText("• " + ingredient);
            tv.setTextSize(15);
            tv.setPadding(0, 6, 0, 6);
            tv.setTextColor(getResources().getColor(R.color.text_primary));
            llIngredients.addView(tv);
        }

        // Steps
        llSteps.removeAllViews();
        List<String> steps = recipe.getSteps();
        for (int i = 0; i < steps.size(); i++) {
            // Step number label
            TextView stepNum = new TextView(this);
            stepNum.setText(getString(R.string.step) + " " + (i + 1));
            stepNum.setTextSize(13);
            stepNum.setTextColor(getResources().getColor(R.color.primary_orange));
            stepNum.setTypeface(null, android.graphics.Typeface.BOLD);
            stepNum.setPadding(0, 12, 0, 2);
            llSteps.addView(stepNum);

            TextView stepText = new TextView(this);
            stepText.setText(steps.get(i));
            stepText.setTextSize(15);
            stepText.setPadding(0, 0, 0, 6);
            stepText.setTextColor(getResources().getColor(R.color.text_primary));
            llSteps.addView(stepText);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
