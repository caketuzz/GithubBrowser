package com.caketuzz.ghbrowser.activity;

import android.os.Bundle;

import com.caketuzz.ghbrowser.adapter.ReposListAdapter;

public interface BrowserActivityPresenter {

    void onSaveInstanceState(Bundle outState);
    void restoreInstanceState(Bundle savedInstanceState);
    void fetchAllPublicRepos();
    void fetchRepoByQuery(String query);
    void onDestroy();

    interface View{
        void setReposAdapter(ReposListAdapter adapter);
        void toast(String message);
        void onFetchDataStarted();
        void onFetchDataCompleted();
        void showNoResult(boolean show);
    }

}
