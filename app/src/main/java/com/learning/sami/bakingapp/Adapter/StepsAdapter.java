package com.learning.sami.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.learning.sami.bakingapp.model.RecipeSteps;
import com.learning.sami.bakingapp.R;
import com.learning.sami.bakingapp.utils.ListItemClickListener;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepViewHolder>{

    final private ListItemClickListener mOnClickListener;
    private int mNumberItems;
    private List<RecipeSteps> mStepsList;

    public StepsAdapter(ListItemClickListener listener){
        mOnClickListener = listener;
    }
    public void setStepsList(List<RecipeSteps> stepsList) {
        mStepsList = stepsList;
        mNumberItems = stepsList.size();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutID = R.layout.steps_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(layoutID, parent, attachToParentImmediately);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(mStepsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tvStepDescription)
        TextView mtvStepDescription;

        public StepViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        void bind(RecipeSteps steps) {
            mtvStepDescription.setText(steps.getmShortDescription());
        }

        @Override
        public void onClick(View v) {
            int clickedItem = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedItem);
        }
    }
}
