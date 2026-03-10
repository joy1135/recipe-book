package com.culinary.app.activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.MediaController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.culinary.app.R;
import com.culinary.app.models.Recipe;
import com.culinary.app.utils.LocaleHelper;
import com.culinary.app.utils.RecipeRepository;
import com.culinary.app.views.TimerView;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private MediaPlayer player;
    private TimerView timer1, timer2, timer3;
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
        Button btnPlay = findViewById(R.id.btn_play_audio);
        VideoView videoView = findViewById(R.id.video_recipe);

        tvName.setText(recipe.getName());
        tvDescription.setText(recipe.getDescription());

        // Category badge
        String catKey = recipe.getCategory();
        int catStringRes;
        switch (catKey) {
            case "breakfast": catStringRes = R.string.breakfast; break;
            case "lunch":     catStringRes = R.string.lunch; break;
            default:          catStringRes = R.string.dinner; break;
        }
        tvCategoryBadge.setText(getString(catStringRes));

        if (recipe.getId() == 1) {
            btnPlay.setVisibility(View.VISIBLE);

            String lang = LocaleHelper.getSavedLanguage(this);
            int audioRes;
            switch (lang) {
                case "ru": audioRes = R.raw.recipe_1_ru; break;
                case "de": audioRes = R.raw.recipe_1_de; break;
                default:   audioRes = R.raw.recipe_1_en; break;
            }

            player = MediaPlayer.create(this, audioRes);

            btnPlay.setOnClickListener(v -> {
                if (player.isPlaying()) {
                    player.pause();
                    btnPlay.setText(R.string.play_audio);
                } else {
                    player.start();
                    btnPlay.setText(R.string.pause_audio);
                }
            });

            player.setOnCompletionListener(mp -> btnPlay.setText(R.string.play_audio));

        } else {
            btnPlay.setVisibility(View.GONE);
        }

        if (recipe.getId() == 1) {
            videoView.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.recipe_1_video);
            videoView.setVideoURI(uri);
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
        } else {
            videoView.setVisibility(View.GONE);
        }

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

        LinearLayout llTimers = findViewById(R.id.ll_timers);
        llTimers.removeAllViews();

        if (recipe.getId() == 1) {
            timer1 = new TimerView(this,
                    getString(R.string.timer_frying),
                    0, 90,
                    R.raw.timer_sound_1,
                    true);

            timer2 = new TimerView(this,
                    getString(R.string.timer_resting),
                    15, 0,
                    R.raw.timer_sound_2,
                    true);

            llTimers.addView(timer1);
            llTimers.addView(timer2);

        } else if (recipe.getId() == 4) {
            timer1 = new TimerView(this,
                    getString(R.string.timer_cooking_beef),
                    60, 0,
                    R.raw.timer_sound_1,
                    false);

            timer2 = new TimerView(this,
                    getString(R.string.timer_borsch_vegetables),
                    15, 0,
                    R.raw.timer_sound_2,
                    true);

            llTimers.addView(timer1);
            llTimers.addView(timer2);

        } else if (recipe.getId() == 8) {
            timer1 = new TimerView(this,
                    getString(R.string.timer_dough_resting),
                    30, 0,
                    R.raw.timer_sound_1,
                    true);

            timer2 = new TimerView(this,
                    getString(R.string.timer_cooking_pelmeni),
                    0, 0,
                    R.raw.timer_sound_2,
                    true);

            llTimers.addView(timer1);
            llTimers.addView(timer2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
        if (timer1 != null) timer1.release();
        if (timer2 != null) timer2.release();
        if (timer3 != null) timer3.release();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}