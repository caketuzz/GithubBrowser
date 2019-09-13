package com.caketuzz.ghbrowser.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.caketuzz.ghbrowser.R;
import com.caketuzz.ghbrowser.adapter.ReposListAdapter;

/*
 *  The Browser activity is the activity in charge of displaying the resutls of a search for a repo.
 *  When no search is performed, it displays all the public repos available.
 *  It should use the infinite loading pattern to display repos further than the first result page.
 */
public class BrowserActivity extends AppCompatActivity implements BrowserActivityPresenter.View {

    private static int RV_COLUMNS = 1;
    private static String TAG = "BrowserActivity";

    BrowserActivityPresenter presenter = null;
    RecyclerView rvRepos = null;
    ProgressBar progressBar = null;
    SearchView searchView = null;
    TextView noResultView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new BrowserActivityPresenterImpl(this);
        setContentView(R.layout.activity_browser);
        rvRepos = findViewById(R.id.rv_repos_list);
        progressBar = findViewById(R.id.pb_search_repo);
        searchView = findViewById(R.id.sv_browse_repo);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                presenter.fetchRepoByQuery(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        noResultView = findViewById(R.id.tv_noresult);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), RV_COLUMNS);
        rvRepos.setLayoutManager(mLayoutManager);

        if (savedInstanceState!= null)
            presenter.restoreInstanceState(savedInstanceState);
        else presenter.fetchAllPublicRepos();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);

    }

    ////////////////////////////////////////////////////////////
    //// Presenter Contract                                 ////
    @Override
    public void setReposAdapter(ReposListAdapter adapter) {
        if (rvRepos == null)
            return;
        rvRepos.setAdapter(adapter);
        rvRepos.invalidate();
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFetchDataStarted() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFetchDataCompleted() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNoResult(boolean show) {
        if (show) {
            noResultView.setVisibility(View.VISIBLE);
            rvRepos.setVisibility(View.GONE);
        }
        else {
            noResultView.setVisibility(View.GONE);
            rvRepos.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // destroy presenter in order to avoid memory leak
        presenter.onDestroy();
    }
}
