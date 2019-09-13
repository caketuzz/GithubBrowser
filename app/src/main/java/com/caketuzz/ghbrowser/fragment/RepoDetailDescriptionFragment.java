package com.caketuzz.ghbrowser.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.caketuzz.ghbrowser.R;
import com.caketuzz.ghbrowser.model.Repo;

import static com.caketuzz.ghbrowser.activity.RepoDetailActivity.STR_BUNDLE_REPO;

public class RepoDetailDescriptionFragment extends Fragment  implements RepoDetailDescriptionPresenter.View {

    private RepoDetailDescriptionPresenter presenter;
    private Repo repo;

    private TextView repoNameView;
    private TextView stargazerView;
    private TextView nbForksView;
    private TextView descriptionView;
    private ImageView isAForkView;

    public static RepoDetailDescriptionFragment newInstance(Bundle intent) {
        RepoDetailDescriptionFragment fragment = new RepoDetailDescriptionFragment();
        fragment.setArguments(intent);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = getArguments().getParcelable(STR_BUNDLE_REPO);
        presenter = new RepoDetailDescriptionPresenterImpl(this, repo);
        presenter.restoreInstanceState(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_repo_description, container, false);
        repoNameView = root.findViewById(R.id.tv_repo_name);
        stargazerView = root.findViewById(R.id.tv_stargazers);
        nbForksView = root.findViewById(R.id.tv_nb_forks);
        descriptionView = root.findViewById(R.id.tv_description);
        isAForkView = root.findViewById(R.id.iv_is_fork);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.displayData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void setName(String text) {
        repoNameView.setText(text);
    }

    @Override
    public void setStargazers(String text) {
        stargazerView.setText(text);
    }

    @Override
    public void setNbForks(String text) {
        nbForksView.setText(text);
    }

    @Override
    public void setDescription(String text) {
        descriptionView.setText(text);
    }

    @Override
    public void setIsAFork(boolean isAFork) {
        if (isAFork)
            isAForkView.setVisibility(View.VISIBLE);
        else
            isAForkView.setVisibility(View.GONE);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
