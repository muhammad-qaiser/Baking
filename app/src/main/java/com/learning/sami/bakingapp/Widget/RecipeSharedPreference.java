package com.learning.sami.bakingapp.Widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.learning.sami.bakingapp.Model.Recipe;

import static com.learning.sami.bakingapp.Utils.AppConstants.KEY_INGREDIENT_SP;

public class RecipeSharedPreference {


    public static void setRecipeSharedPref(Context context, Recipe recipe) {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        editor.putString(KEY_INGREDIENT_SP, json);
        editor.commit();
    }

    public static Recipe getRecipe(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(KEY_INGREDIENT_SP, null);
        return gson.fromJson(json, Recipe.class);
    }

}
