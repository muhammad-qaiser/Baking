package com.learning.sami.bakingapp.ui;


import android.support.annotation.NonNull;
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
import com.learning.sami.bakingapp.model.RecipeSteps;
import com.learning.sami.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.learning.sami.bakingapp.utils.AppConstants.PLAYER_POSITION_KEY;
import static com.learning.sami.bakingapp.utils.AppConstants.PLAY_WHEN_READY;

public class PlayerFragment extends Fragment {

    @BindView(R.id.pvRecipeStep)
    PlayerView mpvStep;
    @BindView(R.id.tvDescription)
    TextView mtvDescription;
    private SimpleExoPlayer mExoPlayer;
    private  static RecipeSteps mRecipeStep;
    private static long mPlayerPosition;
    private static boolean mPlayerRunning;

    public PlayerFragment()
    {

    }

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
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mPlayerPosition = mExoPlayer.getCurrentPosition();
        mPlayerRunning = mExoPlayer.getPlayWhenReady();
        outState.putLong(PLAYER_POSITION_KEY, mPlayerPosition);
        outState.putBoolean(PLAY_WHEN_READY, mPlayerRunning);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!= null)
        {
            mPlayerPosition = savedInstanceState.getLong(PLAYER_POSITION_KEY);
            mPlayerRunning = savedInstanceState.getBoolean(PLAY_WHEN_READY);
        }
    }

    public void resetPlayerPos()
    {
        mPlayerRunning = true;
        mPlayerPosition = 0;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(android.os.Build.VERSION.SDK_INT <= 23 || mExoPlayer == null)
            initializePlayer(mRecipeStep.getmVideoUrl());
        if(mPlayerPosition>0) {
            mExoPlayer.seekTo(mPlayerPosition);
            mExoPlayer.setPlayWhenReady(mPlayerRunning);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(android.os.Build.VERSION.SDK_INT > 23)
            initializePlayer(mRecipeStep.getmVideoUrl());
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
        //releasePlayer();
    }



    @Override
    public void onStop() {
        super.onStop();
        if(android.os.Build.VERSION.SDK_INT > 23)
            releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(android.os.Build.VERSION.SDK_INT <=23)
            releasePlayer();
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
