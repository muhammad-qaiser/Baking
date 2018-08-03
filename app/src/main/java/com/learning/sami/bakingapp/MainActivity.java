package com.learning.sami.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import com.learning.sami.bakingapp.Adapter.RecipeAdapter;
import com.learning.sami.bakingapp.Model.Recipe;
import com.learning.sami.bakingapp.Utils.ListItemClickListener;
import com.learning.sami.bakingapp.Utils.RecipeClient;
import com.learning.sami.bakingapp.Utils.RetrofitClient;
import com.learning.sami.bakingapp.Widget.RecipeSharedPreference;
import com.learning.sami.bakingapp.Widget.RecipeWidgetUpdateService;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.learning.sami.bakingapp.Utils.AppConstants.*;

public class MainActivity extends AppCompatActivity implements ListItemClickListener {

    @BindView(R.id.rvRecipe) RecyclerView mRecipesRV;
    private static LinearLayoutManager mLayoutManager;
    private List<Recipe> mRecipeList;
    private RecipeAdapter mRecipeAdapter;
    private static Parcelable mListState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mLayoutManager = new GridLayoutManager(this
                    ,calculateNoOfColumns(this));
        }
        else
        {
            mLayoutManager = new LinearLayoutManager(this);
        }
        mRecipesRV.setLayoutManager(mLayoutManager);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecipesRV.setAdapter(mRecipeAdapter);
        if(savedInstanceState == null)
            loadRecipe();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mListState = mLayoutManager.onSaveInstanceState();
        outState.putParcelable(KEY_RECIPE_LAYOUT_STATE, mListState);
        outState.putParcelableArrayList(KEY_RECIPE_LIST_STATE,(ArrayList<Recipe>) mRecipeList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(mListState!= null)
        {
            mListState = savedInstanceState.getParcelable(KEY_RECIPE_LAYOUT_STATE);
            mRecipeList = savedInstanceState.getParcelableArrayList(KEY_RECIPE_LIST_STATE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mListState!= null)
        {
            mRecipeAdapter.setRecipeList(mRecipeList);
            mLayoutManager.onRestoreInstanceState(mListState);
            mRecipesRV.setLayoutManager(mLayoutManager);
        }
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int numberOfCol =(int) (dpWidth / COLUMN_WIDTH);
        return (numberOfCol>3)?3:numberOfCol;
    }
    public void loadRecipe()
    {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        RecipeClient recipeClient = new RetrofitClient().getClient().create(RecipeClient.class);
        if(isConnected)
        {
            Timber.d("internet Connected");
            Call<ArrayList<Recipe>> arrayListCall = recipeClient.getRecipes();
            arrayListCall.enqueue(new Callback<ArrayList<Recipe>>() {
                @Override
                public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                    mRecipeList = response.body();
                    mRecipeAdapter.setRecipeList(mRecipeList);
                    mRecipeAdapter.notifyDataSetChanged();
                    Timber.d("Recipe Adapter Set");
                }

                @Override
                public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                    Timber.d("Failed to set Recipe Adapter ");
                }
            });
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        Timber.d("Recipe Clicked");
        Intent i = new Intent(this, DetailActivity.class);
        Recipe r = mRecipeList.get(clickedItemIndex);
        i.putExtra(RECIPE_EXTRA, r);
        RecipeWidgetUpdateService.startActionUpdateListView(getApplicationContext(), r);
        startActivity(i);

    }
}
