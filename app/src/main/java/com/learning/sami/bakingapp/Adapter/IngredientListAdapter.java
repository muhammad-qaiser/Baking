package com.learning.sami.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.learning.sami.bakingapp.Model.RecipeIngredients;
import com.learning.sami.bakingapp.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientListAdapter extends  RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private int mNumberItems;
    private List<RecipeIngredients> mIngredientsList;

    public void setIngredientsList(List<RecipeIngredients> ingredientsList) {
        mIngredientsList = ingredientsList;
        mNumberItems = mIngredientsList.size();
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.ingredient_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(layoutID, parent, attachToParentImmediately);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(mIngredientsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvIngredient)
        TextView mtvIngredient;
        @BindView(R.id.tvIngredientMeasure)
        TextView mtvIngredientMeasure;
        @BindView(R.id.tvIngredientQty)
        TextView mtvIngredientQty;

        public IngredientViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(RecipeIngredients ingredient) {
            mtvIngredient.setText(ingredient.getmIngredient());
            mtvIngredientMeasure.setText(ingredient.getmMeasure());
            mtvIngredientQty.setText(Float.toString(ingredient.getmQuantity()));
        }


    }

}


