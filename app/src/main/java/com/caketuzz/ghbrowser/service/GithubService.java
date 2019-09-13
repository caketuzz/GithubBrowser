package com.caketuzz.ghbrowser.service;

import com.caketuzz.ghbrowser.model.Repo;
import com.caketuzz.ghbrowser.model.RepoSearchResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GithubService {

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);

    @GET("repositories?since=")
    Call<List<Repo>> listAllPublicRepos(@Query("since") int since);

    @GET("search/repositories?q=")
    Call<RepoSearchResponse> searchRepo(@Query("q") String query);

    @GET("repos/{full_name}")
    Call<Repo> fetchRepo(@Path(value="full_name", encoded=true) String full_name);

}