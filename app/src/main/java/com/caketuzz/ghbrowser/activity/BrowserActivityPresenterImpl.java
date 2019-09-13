package com.caketuzz.ghbrowser.activity;

import android.os.Bundle;
import android.util.Log;

import com.caketuzz.ghbrowser.adapter.ReposListAdapter;
import com.caketuzz.ghbrowser.model.Repo;
import com.caketuzz.ghbrowser.service.GithubDataSource;

import java.util.ArrayList;
import java.util.List;

public class BrowserActivityPresenterImpl implements BrowserActivityPresenter, GithubDataSource.ReposListCallback {

    private static String TAG = "BrowserActivityPresenterImpl";
    private static String SIS_REPOSLIST = "sis.reposlist";

    private List<Repo> reposList = null;

    // Activity stuff
    private BrowserActivityPresenter.View view;
    private ReposListAdapter reposListAdapter = null;

    // Internal data
    private int nextPageIndex = 0;

    public BrowserActivityPresenterImpl(BrowserActivityPresenter.View view) {
        this.view = view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (reposList!=null){
            outState.putParcelableArrayList(SIS_REPOSLIST, (ArrayList)reposList);
        }
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return;
        if ( // List of repos was already fetched, just restore them
                ((reposList = savedInstanceState.getParcelableArrayList(SIS_REPOSLIST) ) != null)
            && (reposList.size() != 0) )
            onFetchDataCompleted(reposList, 0);
        else // fetch a repo list
            fetchAllPublicRepos();

    }

    @Override
    public void fetchAllPublicRepos() {
        view.onFetchDataStarted();
        GithubDataSource.getInstance().fetchAllPublicRepos(nextPageIndex, this);
    }

    @Override
    public void fetchRepoByQuery(String query) {
        view.onFetchDataStarted();
        GithubDataSource.getInstance().fetchRepoByQuery(query.replace(' ', '+'), nextPageIndex, this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    private void processReposList(){
        if (reposList == null)
            return;
        reposListAdapter = new ReposListAdapter(reposList);
        if (view !=null)
            view.setReposAdapter(reposListAdapter);
    }

    @Override
    public void onFetchDataCompleted(List<Repo> repos, int nextPage) {
        nextPageIndex = nextPage;
        if (view ==null)    // ConfigurationChanged
            return;
        reposList = repos;
        processReposList();
        view.onFetchDataCompleted();
        view.showNoResult(reposListAdapter.getItemCount()==0);
    }

    @Override
    public void onError(String message){
        Log.d(TAG, "onError " + message);
        if (view ==null)    // ConfigurationChanged
            return;
        view.toast("HTTP request failed: "+message);
        view.onFetchDataCompleted();
    }

    @Override
    public void onNetworkError(String message){
        if (view ==null)    // ConfigurationChanged
            return;
        Log.d(TAG, "onNetworkError " + message);
        view.toast("HTTP request failed: "+message);
        view.onFetchDataCompleted();
    }
}

