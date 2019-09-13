package com.caketuzz.ghbrowser.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepoSearchResponse {

    @SerializedName("total_count")
    private int totalCount;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    private List<Repo> items;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<Repo> getItems() {
        return items;
    }
}
