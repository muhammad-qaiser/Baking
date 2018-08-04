package com.learning.sami.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.learning.sami.bakingapp.DetailActivity;
import com.learning.sami.bakingapp.model.Recipe;
import com.learning.sami.bakingapp.R;
import static com.learning.sami.bakingapp.utils.AppConstants.RECIPE_EXTRA;

public class RecipeWidgetProvider extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Recipe  recipe = RecipeSharedPreference.getRecipe(context);
        // Construct the RemoteViews object
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        if(recipe!= null) {
            views.setTextViewText(R.id.tvRecipeName, recipe.getmName());
            Intent intentService = new Intent(context, ListViewWidgetService.class);
            views.setRemoteAdapter(R.id.ingredientsList, intentService);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(RECIPE_EXTRA, recipe);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            views.setOnClickPendingIntent(R.id.tvRecipeName, pendingIntent);
        }
        views.setEmptyView(R.id.ingredientsList, R.id.emptyView);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgets (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

