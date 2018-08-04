package com.learning.sami.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Recipe implements Parcelable{



    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mServings = in.readInt();
        mImage = in.readString();
        mRecipeIngredients = new ArrayList<>();
        mRecipeSteps = new ArrayList<>();
        in.readList(mRecipeIngredients, RecipeIngredients.class.getClassLoader());
        in.readList(mRecipeSteps, RecipeSteps.class.getClassLoader());
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @SerializedName("id")
    protected int mId;
    public int getmId() { return mId; }
    public void setmId(int mId) { this.mId = mId;}

    @SerializedName("name")
    protected String mName;
    public String getmName() { return mName; }
    public void setmName(String mName) { this.mName = mName; }

    @SerializedName("servings")
    protected int mServings;
    public void setmServings(int mServings) { this.mServings = mServings; }
    public int getmServings() { return mServings; }

    @SerializedName("image")
    protected String mImage;
    public String getmImage() { return mImage; }
    public void setmImage(String mImage) { this.mImage = mImage; }

    @SerializedName("ingredients")
    protected ArrayList<RecipeIngredients> mRecipeIngredients;
    public ArrayList<RecipeIngredients> getRecipeIngredients() { return mRecipeIngredients; }
    public void setmRecipeIngredients(ArrayList<RecipeIngredients> mRecipeIngredients) { this.mRecipeIngredients = mRecipeIngredients; }

    @SerializedName("steps")
    protected ArrayList<RecipeSteps> mRecipeSteps;
    public ArrayList<RecipeSteps> getRecipeSteps() { return mRecipeSteps; }
    public void setmRecipeSteps(ArrayList<RecipeSteps> mRecipeSteps) { this.mRecipeSteps = mRecipeSteps; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeInt(mServings);
        dest.writeString(mImage);
        dest.writeList(mRecipeIngredients);
        dest.writeList(mRecipeSteps);
    }
}
