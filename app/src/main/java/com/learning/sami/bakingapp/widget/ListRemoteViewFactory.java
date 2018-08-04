package com.learning.sami.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.learning.sami.bakingapp.model.Recipe;
import com.learning.sami.bakingapp.model.RecipeIngredients;
import com.learning.sami.bakingapp.R;

import java.util.ArrayList;
import java.util.List;


public class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
    Context mContext;
    Recipe mRecipe ;
    List<RecipeIngredients> mIngredientsList;
    RecipeSharedPreference mSharedPreference;
    public ListRemoteViewFactory(Context appContext)
    {
        mContext = appContext;
        mIngredientsList = new ArrayList<>();
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe = mSharedPreference.getRecipe(mContext);
        mIngredientsList = mRecipe.getRecipeIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view);
        rv.setTextViewText(R.id.tvIngredientName, mIngredientsList.get(position).getmIngredient());
        rv.setTextViewText(R.id.tvIngredientAmount,Float.toString(mIngredientsList.get(position).getmQuantity()));
        return  rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
