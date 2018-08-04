package com.learning.sami.bakingapp;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecipeClickTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);


    IdlingResource mIdle;
    @Before
    public void registerIdelRes()
    {
        mIdle = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdle);
    }
    @Test
    public void clickRecyclerViewItemTakesToDetailActivity()
    {
        onView(withId(R.id.rvRecipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.ingredient_container))
                .check(matches(isDisplayed()));


    }

    @Test
    public void clickRVRecipeClickRVStep()
    {
        onView(withId(R.id.rvRecipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rvStepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.player_container))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickRVRecipeClickRVStepCheckDescription()
    {
        onView(withId(R.id.rvRecipe))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.rvStepsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.tvDescription))
                .check(matches(withText("Recipe Introduction")));
    }

    @After
    public void unRegister()
    {
        IdlingRegistry.getInstance().unregister(mIdle);
    }
}
