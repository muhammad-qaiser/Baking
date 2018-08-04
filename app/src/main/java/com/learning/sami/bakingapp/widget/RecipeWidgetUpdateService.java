package com.learning.sami.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.learning.sami.bakingapp.model.Recipe;
import com.learning.sami.bakingapp.R;

import static com.learning.sami.bakingapp.utils.AppConstants.RECIPE_EXTRA;
import static com.learning.sami.bakingapp.utils.AppConstants.UPDATE_LIST_VIEW;

public class RecipeWidgetUpdateService extends IntentService {

    public RecipeWidgetUpdateService() {
        super("updateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent !=null){
            final String action = intent.getAction();
            if (UPDATE_LIST_VIEW.equals(action)){
                Recipe recipe = intent.getParcelableExtra(RECIPE_EXTRA);
                handleActionUpdateIngredientList(recipe);
            }
        }
    }

    public void handleActionUpdateIngredientList(Recipe recipe)
    {
        if(recipe!=null)
        {
            RecipeSharedPreference.setRecipeSharedPref(this,recipe);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetsIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,RecipeWidgetProvider.class));

        RecipeWidgetProvider.updateAppWidgets(this,appWidgetManager,appWidgetsIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetsIds, R.id.ingredientsList);
    }

    public static void startActionUpdateListView(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeWidgetUpdateService.class);
        intent.setAction(UPDATE_LIST_VIEW);
        intent.putExtra(RECIPE_EXTRA, recipe);
        context.startService(intent);
    }
}
