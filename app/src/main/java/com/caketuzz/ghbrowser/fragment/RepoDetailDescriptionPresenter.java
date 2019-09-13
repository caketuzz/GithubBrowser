package com.caketuzz.ghbrowser.fragment;

import android.os.Bundle;

public interface RepoDetailDescriptionPresenter {

    void displayData();
    void onSaveInstanceState(Bundle outState);
    void restoreInstanceState(Bundle savedInstanceState);
    void onDestroy();

    interface View {
        void setName(String text);
        void setStargazers(String text);
        void setNbForks(String text);
        void setDescription(String text);
        void setIsAFork(boolean isAFork);
        void toast(String message);

    }
}
