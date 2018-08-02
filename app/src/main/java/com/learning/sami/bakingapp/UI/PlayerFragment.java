package com.learning.sami.bakingapp.UI;


import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.learning.sami.bakingapp.DetailActivity;
import com.learning.sami.bakingapp.Model.RecipeSteps;
import com.learning.sami.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerFragment extends Fragment {

    @BindView(R.id.pvRecipeStep)
    PlayerView mpvStep;
    @BindView(R.id.tvDescription)
    TextView mtvDescription;
    private SimpleExoPlayer mExoPlayer;
    private  static RecipeSteps mRecipeStep;

    public PlayerFragment()
    {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.player_fragment, container, false);

        ButterKnife.bind(this, rootView);

        ConstraintLayout.LayoutParams params;
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE && !DetailActivity.isTwoPaneMode()) {
            params = (ConstraintLayout.LayoutParams)
                    mpvStep.getLayoutParams();
            params.width = params.MATCH_PARENT;
            params.height = params.MATCH_PARENT;
            mpvStep.setLayoutParams(params);
            mtvDescription.setVisibility(View.INVISIBLE);

        }
        else{
            mtvDescription.setText(mRecipeStep.getmDescription());
        }
        initializePlayer(mRecipeStep.getmVideoUrl());



        return rootView;
    }

    public void setRecipeStep(RecipeSteps recipeStep) {
        this.mRecipeStep = recipeStep;
    }

    private void releasePlayer()
    {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        mExoPlayer.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayer.stop();
    }

    private void initializePlayer(String videoUrl)
    {
        if(mpvStep !=null /*&& !(videoUrl.equals("") || videoUrl.equals(" ")  )*/)
        {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(),trackSelector,loadControl);
            mpvStep.setPlayer(mExoPlayer);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(new
                    DefaultHttpDataSourceFactory("bakingApp"))
                    .createMediaSource(Uri.parse(videoUrl));
            mExoPlayer.prepare(mediaSource, false, false);
            mExoPlayer.setPlayWhenReady(true);
        }
    }
}
