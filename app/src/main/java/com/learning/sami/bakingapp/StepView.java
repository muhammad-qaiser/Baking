package com.learning.sami.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.learning.sami.bakingapp.Model.RecipeSteps;
import com.learning.sami.bakingapp.UI.PlayerFragment;

import java.util.Objects;

import static com.learning.sami.bakingapp.AppConstants.RECIPE_STEP_EXTRA;
import static com.learning.sami.bakingapp.AppConstants.RECIPE_TITLE;

public class StepView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        RecipeSteps step = getIntent().getParcelableExtra(RECIPE_STEP_EXTRA);
        PlayerFragment playerFragment = new PlayerFragment();
        playerFragment.setRecipeStep(step);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.player_container, playerFragment)
                    .commit();
        }
        else
        {
            fragmentManager.beginTransaction()
                    .replace(R.id.player_container, playerFragment)
                    .commit();
        }
        String title = getIntent().getStringExtra(RECIPE_TITLE);
        setTitle(title);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case (android.R.id.home):
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
