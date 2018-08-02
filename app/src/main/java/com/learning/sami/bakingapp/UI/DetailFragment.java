package com.learning.sami.bakingapp.UI;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.learning.sami.bakingapp.Adapter.IngredientListAdapter;
import com.learning.sami.bakingapp.Adapter.StepsAdapter;
import com.learning.sami.bakingapp.Model.Recipe;
import com.learning.sami.bakingapp.R;
import com.learning.sami.bakingapp.Utils.ListItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import static com.learning.sami.bakingapp.AppConstants.KEY_SCROLL_STATE;

public class DetailFragment extends Fragment implements ListItemClickListener {

    @BindView(R.id.rvIngredientList)
    RecyclerView mIngredientRV;
    @BindView(R.id.rvStepsList)
    RecyclerView mStepsRV;
    @BindView(R.id.svDetailFrag)
    ScrollView mSVDetailFragment;
    private IngredientListAdapter mIngredientListAdapter;
    private StepsAdapter mStepsAdapter;
    private static Recipe mRecipe;
    public void setRecipe(Recipe recipe) { mRecipe = recipe; }
    ListItemClickListener mCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mCallback = (ListItemClickListener)context;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        ButterKnife.bind(this, rootView);
        mIngredientRV.setLayoutManager(new LinearLayoutManager(getActivity()){
               @Override
               public boolean canScrollVertically() {
                   return false;
               }
           }
        );

        mIngredientListAdapter = new IngredientListAdapter();
        mIngredientListAdapter.setIngredientsList(mRecipe.getRecipeIngredients());
        mIngredientRV.setAdapter(mIngredientListAdapter);

        mStepsRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        mStepsAdapter = new StepsAdapter(this);
        mStepsAdapter.setStepsList(mRecipe.getRecipeSteps());
        mStepsRV.setAdapter(mStepsAdapter);

        return rootView;
    }

    private static int scrollState;
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        scrollState = mSVDetailFragment.getScrollY();
        outState.putInt(KEY_SCROLL_STATE, scrollState);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!= null)
        scrollState = savedInstanceState.getInt(KEY_SCROLL_STATE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSVDetailFragment.setScrollY(scrollState);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Timber.d("Click inside DetailFragment at index "+clickedItemIndex  );
        mCallback.onListItemClick(clickedItemIndex);
    }
}
