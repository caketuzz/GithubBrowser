package com.caketuzz.ghbrowser.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;

import com.caketuzz.ghbrowser.R;
import com.caketuzz.ghbrowser.fragment.RepoDetailDescriptionFragment;
import com.caketuzz.ghbrowser.model.Repo;
import com.caketuzz.ghbrowser.service.GithubDataSource;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tools.StrTools;

import static com.caketuzz.ghbrowser.activity.RepoDetailActivity.STR_BUNDLE_REPO;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;


@Config(sdk = 21, manifest = "AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class FetchRepoInDetailActivityTest {

    private FragmentScenario<RepoDetailDescriptionFragment> scenario;
    private RepoDetailDescriptionFragment fragment;
    private FragmentActivity activity;

    private Repo intentRepo;

    @Mock
    private GithubDataSource mockGithubService;

    @Captor
    private ArgumentCaptor<GithubDataSource.ReposListCallback> callbackRepoCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // This instance mocks the real retrofit API calls and will be used in API calls
        GithubDataSource.setInstance(mockGithubService);
        // setup Intent Repo
        InputStream ims = getClass().getClassLoader().getResourceAsStream("json/arepo.json");
        String json = StrTools.strFromInputstream(ims);
        intentRepo = new Gson().fromJson(json, Repo.class);
        // setup fragment
        Bundle b = new Bundle();
        b.putParcelable(STR_BUNDLE_REPO, intentRepo);
        scenario = FragmentScenario.launchInContainer(RepoDetailDescriptionFragment.class, b);
        scenario.onFragment(new FragmentScenario.FragmentAction<RepoDetailDescriptionFragment>() {
            @Override
            public void perform(@NonNull RepoDetailDescriptionFragment fragment) {
                activity = fragment.getActivity();
            }
        });
        scenario.moveToState(Lifecycle.State.CREATED); //This should trigger the API call

    }

    @Test
    public void fetchRepoByURLTest() {
        Mockito.verify(mockGithubService).fetchRepo(anyString(), callbackRepoCaptor.capture());

        // create a response from the json resource file arepo.json
        List<Repo> repos = new ArrayList<Repo>();

        repos.add(intentRepo);

        callbackRepoCaptor.getValue().onFetchDataCompleted(repos, 0);

        assertTrue(((TextView)activity.findViewById(R.id.tv_repo_name)).getText().equals(intentRepo.getName()));
        assertTrue(((TextView)activity.findViewById(R.id.tv_description)).getText().equals(intentRepo.getDescription()));
        assertTrue(((TextView)activity.findViewById(R.id.tv_nb_forks)).getText().equals(""+intentRepo.getForksCount()));
        assertTrue(((TextView)activity.findViewById(R.id.tv_stargazers)).getText().equals(""+intentRepo.getStargazersCount()));

        // Configuration changed
        scenario.recreate();
        scenario.onFragment(new FragmentScenario.FragmentAction<RepoDetailDescriptionFragment>() {
            @Override
            public void perform(@NonNull RepoDetailDescriptionFragment fragment) {
                activity = fragment.getActivity();
            }
        });
        assertTrue(((TextView)activity.findViewById(R.id.tv_repo_name)).getText().equals(intentRepo.getName()));
        assertTrue(((TextView)activity.findViewById(R.id.tv_description)).getText().equals(intentRepo.getDescription()));
        assertTrue(((TextView)activity.findViewById(R.id.tv_nb_forks)).getText().equals(""+intentRepo.getForksCount()));
        assertTrue(((TextView)activity.findViewById(R.id.tv_stargazers)).getText().equals(""+intentRepo.getStargazersCount()));

    }
    @Test
    public void fetchRepoByURLError() {
        Mockito.verify(mockGithubService).fetchRepo(anyString(), callbackRepoCaptor.capture());
        String message = "501 - error message";
        callbackRepoCaptor.getValue().onError(message);
        assertTrue(ShadowToast.getTextOfLatestToast().contains(message));
    }

    @Test
    public void fetchRepoByURLNetworkError() {
        Mockito.verify(mockGithubService).fetchRepo(anyString(), callbackRepoCaptor.capture());
        String message = "501 - error message";
        callbackRepoCaptor.getValue().onNetworkError(message);
        assertTrue(ShadowToast.getTextOfLatestToast().contains(message));
    }
}