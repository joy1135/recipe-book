package com.culinary.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.culinary.app.R;
import com.culinary.app.adapters.RecipeAdapter;
import com.culinary.app.models.Recipe;
import com.culinary.app.utils.LocaleHelper;
import com.culinary.app.utils.RecipeRepository;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private RecyclerView recyclerView;
    private RecipeAdapter adapter;
    private Button btnAll, btnBreakfast, btnLunch, btnDinner;
    private Button btnLangEn, btnLangRu, btnLangDe;
    private TextView tvTitle, tvSubtitle;
    private String currentFilter = "all";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupLanguageButtons();
        setupFilterButtons();
        loadRecipes(currentFilter);
        updateActiveLanguageButton();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recycler_recipes);
        btnAll = findViewById(R.id.btn_filter_all);
        btnBreakfast = findViewById(R.id.btn_filter_breakfast);
        btnLunch = findViewById(R.id.btn_filter_lunch);
        btnDinner = findViewById(R.id.btn_filter_dinner);
        btnLangEn = findViewById(R.id.btn_lang_en);
        btnLangRu = findViewById(R.id.btn_lang_ru);
        btnLangDe = findViewById(R.id.btn_lang_de);
        tvTitle = findViewById(R.id.tv_app_title);
        tvSubtitle = findViewById(R.id.tv_app_subtitle);

        int spanCount = getResources().getConfiguration().screenWidthDp >= 600 ? 2 : 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
    }

    private void setupLanguageButtons() {
        btnLangEn.setOnClickListener(v -> changeLanguage("en"));
        btnLangRu.setOnClickListener(v -> changeLanguage("ru"));
        btnLangDe.setOnClickListener(v -> changeLanguage("de"));
    }

    private void changeLanguage(String lang) {
        LocaleHelper.saveLanguage(this, lang);
        recreate();
    }

    private void setupFilterButtons() {
        btnAll.setOnClickListener(v -> {
            currentFilter = "all";
            updateFilterButtons(btnAll);
            loadRecipes("all");
        });
        btnBreakfast.setOnClickListener(v -> {
            currentFilter = "breakfast";
            updateFilterButtons(btnBreakfast);
            loadRecipes("breakfast");
        });
        btnLunch.setOnClickListener(v -> {
            currentFilter = "lunch";
            updateFilterButtons(btnLunch);
            loadRecipes("lunch");
        });
        btnDinner.setOnClickListener(v -> {
            currentFilter = "dinner";
            updateFilterButtons(btnDinner);
            loadRecipes("dinner");
        });
    }

    private void updateFilterButtons(Button active) {
        int activeColor = getResources().getColor(R.color.primary_orange);
        int inactiveColor = getResources().getColor(R.color.button_inactive);
        int whiteColor = getResources().getColor(android.R.color.white);
        int darkColor = getResources().getColor(R.color.text_primary);

        btnAll.setBackgroundColor(inactiveColor);
        btnBreakfast.setBackgroundColor(inactiveColor);
        btnLunch.setBackgroundColor(inactiveColor);
        btnDinner.setBackgroundColor(inactiveColor);

        btnAll.setTextColor(darkColor);
        btnBreakfast.setTextColor(darkColor);
        btnLunch.setTextColor(darkColor);
        btnDinner.setTextColor(darkColor);

        active.setBackgroundColor(activeColor);
        active.setTextColor(whiteColor);
    }

    private void loadRecipes(String filter) {
        List<Recipe> recipes = RecipeRepository.filterByCategory(this, filter);
        if (adapter == null) {
            adapter = new RecipeAdapter(this, recipes, this);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateRecipes(recipes);
        }
        updateFilterButtons(getActiveButton(filter));
    }

    private Button getActiveButton(String filter) {
        switch (filter) {
            case "breakfast": return btnBreakfast;
            case "lunch": return btnLunch;
            case "dinner": return btnDinner;
            default: return btnAll;
        }
    }

    private void updateActiveLanguageButton() {
        String lang = LocaleHelper.getSavedLanguage(this);
        int activeColor = getResources().getColor(R.color.primary_orange);
        int inactiveColor = getResources().getColor(R.color.lang_btn_inactive);
        int whiteColor = getResources().getColor(android.R.color.white);
        int darkColor = getResources().getColor(R.color.text_primary);

        btnLangEn.setBackgroundColor(inactiveColor);
        btnLangRu.setBackgroundColor(inactiveColor);
        btnLangDe.setBackgroundColor(inactiveColor);
        btnLangEn.setTextColor(darkColor);
        btnLangRu.setTextColor(darkColor);
        btnLangDe.setTextColor(darkColor);

        Button active = lang.equals("ru") ? btnLangRu : lang.equals("de") ? btnLangDe : btnLangEn;
        active.setBackgroundColor(activeColor);
        active.setTextColor(whiteColor);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipe_id", recipe.getId());
        intent.putExtra("recipe_category", recipe.getCategory());
        startActivity(intent);
    }
}
