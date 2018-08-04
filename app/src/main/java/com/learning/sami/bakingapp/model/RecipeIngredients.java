package com.learning.sami.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class RecipeIngredients implements Parcelable {

    protected RecipeIngredients(Parcel in) {
        mQuantity = in.readFloat();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    public static final Creator<RecipeIngredients> CREATOR = new Creator<RecipeIngredients>() {
        @Override
        public RecipeIngredients createFromParcel(Parcel in) {
            return new RecipeIngredients(in);
        }

        @Override
        public RecipeIngredients[] newArray(int size) {
            return new RecipeIngredients[size];
        }
    };

    @SerializedName("quantity")
    protected float mQuantity;
    public float getmQuantity() { return mQuantity; }
    public void setmQuantity(float mQuantity) { this.mQuantity = mQuantity; }

    @SerializedName("measure")
    protected String mMeasure;
    public String getmMeasure() { return mMeasure; }
    public void setmMeasure(String mMeasure) { this.mMeasure = mMeasure; }

    @SerializedName("ingredient")
    protected String mIngredient;
    public String getmIngredient() { return mIngredient; }
    public void setmIngredient(String mIngredient) { this.mIngredient = mIngredient; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }
}
