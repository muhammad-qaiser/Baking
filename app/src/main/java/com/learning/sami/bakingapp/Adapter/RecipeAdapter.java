package com.learning.sami.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.learning.sami.bakingapp.model.Recipe;
import com.learning.sami.bakingapp.R;
import com.learning.sami.bakingapp.utils.ListItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;
    private List<Recipe> mRecipeList;

    public RecipeAdapter(ListItemClickListener listener)
    {
        mOnClickListener = listener;
    }
    public void setRecipeList(List<Recipe> recipe)
    {
        if(recipe!= null) {
            mRecipeList = recipe;
            mNumberItems = recipe.size();
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.recipe_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(layoutID, parent, attachToParentImmediately);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(mRecipeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvRecipeTitle) TextView mRecipeTitle;
        @BindView(R.id.ivBaking) ImageView mBakingCover;

        private RecipeViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            Timber.d("Recipe Viewholder created ");
        }

        void bind(Recipe recipe) {
            mRecipeTitle.setText(recipe.getmName());
            if(recipe.getmImage().equals(""))
            {
                Picasso.get().load(R.drawable.baking).fit()
                        .error(R.drawable.error)
                        .into(mBakingCover);
            }
            else
            {
                Picasso.get().load(recipe.getmImage()).fit()
                        .error(R.drawable.error)
                        .placeholder(R.drawable.baking)
                        .into(mBakingCover);
            }

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}