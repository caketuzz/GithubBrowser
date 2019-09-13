package com.caketuzz.ghbrowser.fragment;

import android.os.Bundle;
import android.util.Log;

import com.caketuzz.ghbrowser.model.Repo;
import com.caketuzz.ghbrowser.service.GithubDataSource;

import java.util.List;

public class RepoDetailDescriptionPresenterImpl implements RepoDetailDescriptionPresenter, GithubDataSource.ReposListCallback {

    private static String TAG = "RepoDetailDescriptionPresenterImpl";
    private static String SIS_CURRENT_REPO = "SIS.current_repo";
    private RepoDetailDescriptionPresenter.View view;
    private Repo currentRepo;
    // Even though a repo is passed as intent data, that repo object may lack information if it is queried
    // with fetchAllRepos(). It is complete though if it retrieved by fetchRepoByQuery().
    // So we store that information to know if the fragment requires to fetch the complete data.
    private boolean isRepoDataFull = false;

    public RepoDetailDescriptionPresenterImpl(RepoDetailDescriptionPresenter.View view, Repo repo){
        this.view = view;
        this.currentRepo = repo;
    }

    @Override
    public void displayData() {
        if (!isRepoDataFull)
            GithubDataSource.getInstance().fetchRepo(currentRepo.getFullName(), this);
        else showRepo();
    }

    private void showRepo(){
        view.setName(currentRepo.getName());
        view.setDescription(currentRepo.getDescription());
        view.setIsAFork(currentRepo.isFork());
        view.setNbForks(""+currentRepo.getForksCount());
        view.setStargazers(""+currentRepo.getStargazersCount());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SIS_CURRENT_REPO, currentRepo);
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState!=null){
            if ( (currentRepo = savedInstanceState.getParcelable(SIS_CURRENT_REPO)) != null){
                //showRepo();
                isRepoDataFull = true;
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void onFetchDataCompleted(List<Repo> repos, int nextPage) {
        currentRepo = repos.get(0);
        isRepoDataFull = true;
        showRepo();
    }

    @Override
    public void onError(String message) {
        Log.d(TAG, "onError " + message);
        view.toast("HTTP request failed: "+message);
    }

    @Override
    public void onNetworkError(String message) {
        Log.d(TAG, "onNetworkError " + message);
        view.toast("HTTP request failed: "+message);
    }
}
