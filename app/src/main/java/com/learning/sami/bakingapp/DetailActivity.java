package com.learning.sami.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.learning.sami.bakingapp.Model.Recipe;
import com.learning.sami.bakingapp.Model.RecipeSteps;
import com.learning.sami.bakingapp.UI.DetailFragment;
import com.learning.sami.bakingapp.UI.PlayerFragment;
import com.learning.sami.bakingapp.Utils.ListItemClickListener;

import timber.log.Timber;

import static com.learning.sami.bakingapp.AppConstants.RECIPE_EXTRA;
import static com.learning.sami.bakingapp.AppConstants.RECIPE_STEP_EXTRA;
import static com.learning.sami.bakingapp.AppConstants.RECIPE_TITLE;

public class DetailActivity extends AppCompatActivity implements ListItemClickListener {

    private static Recipe mRecipe;
    private static boolean mTwoPaneMode;
    public static boolean isTwoPaneMode()
    {return mTwoPaneMode;}
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(savedInstanceState== null) {
           mRecipe = getIntent().getParcelableExtra(RECIPE_EXTRA);
           setTitle(mRecipe.getmName());
        }
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setRecipe(mRecipe);
        mFragmentManager = getSupportFragmentManager();
        if(findViewById(R.id.player_container) != null) {
            mTwoPaneMode = true;
            if(savedInstanceState == null) {
                PlayerFragment playerFragment =  new PlayerFragment();
                RecipeSteps recipeStep = mRecipe.getRecipeSteps().get(0);
                playerFragment.setRecipeStep(recipeStep);
                mFragmentManager.beginTransaction()
                        .add(R.id.ingredient_container, detailFragment)
                        .add(R.id.player_container, playerFragment)
                        .commit();
            }
        }
        else {
            mTwoPaneMode = false;

            if(savedInstanceState == null) {
                mFragmentManager.beginTransaction()
                        .add(R.id.ingredient_container, detailFragment)
                        .commit();
            }
        }
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

    @Override
    public void onListItemClick(int clickedItemIndex) {

        RecipeSteps step = mRecipe.getRecipeSteps().get(clickedItemIndex);
        if(mTwoPaneMode)
        {
            PlayerFragment fragment= new PlayerFragment();
            fragment.setRecipeStep(step);
            mFragmentManager.beginTransaction()
                    .replace(R.id.player_container, fragment)
                    .commit();
        }
        else {
            Intent i = new Intent(this, StepView.class);
            i.putExtra(RECIPE_STEP_EXTRA,step);
            i.putExtra(RECIPE_TITLE, mRecipe.getmName());
            startActivity(i);
            Timber.d("Inside DetailActivity at index " + clickedItemIndex);
        }
    }
}
