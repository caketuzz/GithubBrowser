package com.caketuzz.ghbrowser.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.caketuzz.ghbrowser.R;
import com.caketuzz.ghbrowser.activity.ui.main.PlaceholderFragment;
import com.caketuzz.ghbrowser.fragment.RepoDetailDescriptionFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class RepoDetailSectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_frag_1, R.string.tab_frag_2, R.string.tab_frag_3};
    private final Context mContext;
    Bundle extras;

    public RepoDetailSectionsPagerAdapter(Context context, FragmentManager fm, Bundle extras) {
        super(fm);
        mContext = context;
        this.extras = extras;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch(position){
            case 0: return RepoDetailDescriptionFragment.newInstance(extras);
            default: return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}