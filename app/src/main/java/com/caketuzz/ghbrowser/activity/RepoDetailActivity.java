package com.caketuzz.ghbrowser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.caketuzz.ghbrowser.R;
import com.caketuzz.ghbrowser.model.Repo;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.caketuzz.ghbrowser.adapter.RepoDetailSectionsPagerAdapter;

public class RepoDetailActivity extends AppCompatActivity {

    public static String STR_BUNDLE_REPO = "repo";

    public static Intent getIntent(Context c, Repo repo){
        Intent intent = new Intent(c, RepoDetailActivity.class);
        intent.putExtra(STR_BUNDLE_REPO, repo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repo repo = getIntent().getExtras().getParcelable(STR_BUNDLE_REPO);
        // Layout initialization
        setContentView(R.layout.activity_repo_detail);
        RepoDetailSectionsPagerAdapter sectionsPagerAdapter = new RepoDetailSectionsPagerAdapter(this, getSupportFragmentManager(), getIntent().getExtras());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }






}