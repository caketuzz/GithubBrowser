package com.caketuzz.ghbrowser.service;

import android.util.Log;

import com.caketuzz.ghbrowser.model.Repo;
import com.caketuzz.ghbrowser.model.RepoSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubDataSource {

    private static String TAG = "GithubDataSource";

    private static GithubService service;
    private static GithubDataSource instance;

    private GithubDataSource(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create()) //for entity

                .build();

        service =retrofit.create(GithubService.class);
    }

    public static GithubDataSource getInstance(){
        if (instance == null) {
            return (instance = new GithubDataSource());
        }
        else return instance;
    }

    // Used for mocking/test purpose
    public static void setInstance(GithubDataSource mock){
        instance = mock;
    }

    public interface ReposListCallback {
        void onFetchDataCompleted(List<Repo> repos, int nextPage);
        void onError(String message);
        void onNetworkError(String message);
    }


    public void fetchAllPublicRepos(int page, final ReposListCallback callback){

        Call<List<Repo>> call = service.listAllPublicRepos(page);
        call.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {

                if (response.isSuccessful()) {
                    // TODO extract the next page value in the "link" parameter of the response's header
                    //response.headers().get("link");
                    callback.onFetchDataCompleted(response.body(), 0);
                } else {
                    Log.d(TAG, "fetchAllPublicRepos  " + response.errorBody().toString());
                    callback.onError("fetchAllPublicRepos: "+response.code()+" "+response.raw()+" "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                callback.onNetworkError(t.getMessage());
            }
        });
    }

    public void fetchRepoByQuery(String query, int page, final ReposListCallback callback){
        Call<RepoSearchResponse> call = service.searchRepo(query);
        call.enqueue(new Callback<RepoSearchResponse>() {
            @Override
            public void onResponse(Call<RepoSearchResponse> call, Response<RepoSearchResponse> response) {

                if (response.isSuccessful()) {
                    // TODO extract the next page value in the "link" parameter of the response's header
                    //response.headers().get("link");
                    callback.onFetchDataCompleted(response.body().getItems(), 0);
                } else {
                    Log.d(TAG, "fetchRepoByQuery  " + response.errorBody().toString());
                    callback.onError("fetchRepoByQuery"+response.code()+" "+response.raw()+" "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<RepoSearchResponse> call, Throwable t) {
                Log.d(TAG, call.request().headers().toString());
                callback.onNetworkError(t.getMessage());
            }
        });
    }

    public void fetchRepo(String full_name, final ReposListCallback callback){
        Call<Repo> call = service.fetchRepo(full_name);
        call.enqueue(new Callback<Repo>() {
            @Override
            public void onResponse(Call<Repo> call, Response<Repo> response) {

                if (response.isSuccessful()) {
                    Repo repo = response.body();
                    List<Repo> res = new ArrayList<>();
                    res.add(repo);
                    callback.onFetchDataCompleted(res, 0);
                } else {
                    Log.d(TAG, "fetchRepo  " + response.errorBody().toString());
                    callback.onError("fetchRepo"+response.code()+" "+response.raw()+" "+response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<Repo> call, Throwable t) {
                Log.d(TAG, call.request().headers().toString());
                callback.onNetworkError(t.getMessage());
            }
        });
    }
}
