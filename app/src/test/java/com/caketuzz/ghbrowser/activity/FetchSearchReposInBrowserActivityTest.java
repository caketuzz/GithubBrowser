package com.caketuzz.ghbrowser.activity;

import android.os.Bundle;

import com.caketuzz.ghbrowser.model.Repo;
import com.caketuzz.ghbrowser.service.GithubDataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@Config(sdk = 21, manifest = "AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class FetchSearchReposInBrowserActivityTest {

    private BrowserActivity activity;
    private ActivityController<BrowserActivity> controller;

    @Mock
    private GithubDataSource mockGithubService;

    @Captor
    private ArgumentCaptor<GithubDataSource.ReposListCallback> callbackAllReposCaptor;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // This instance mocks the real retrofit API calls and will be used in API calls
        GithubDataSource.setInstance(mockGithubService);
        // setup activity
        controller = Robolectric.buildActivity(BrowserActivity.class);
        activity = controller.get();
        controller.create();
    }

    @Test
    public void fetchReposByQueryTest() {
        activity.presenter.fetchRepoByQuery("abc");
        Mockito.verify(mockGithubService).fetchRepoByQuery(anyString(), anyInt(), callbackAllReposCaptor.capture());
        int objQuantity = 30;
        List<Repo> repos = new ArrayList<Repo>();
        for (int i=0; i<objQuantity; ++i){
            repos.add(new Repo());
        }

        callbackAllReposCaptor.getValue().onFetchDataCompleted(repos, 31);

        assertTrue(activity.rvRepos.getAdapter().getItemCount()==objQuantity);

        // Test configuration change
        Bundle bundle = new Bundle();
        controller.saveInstanceState(bundle).pause().stop().destroy();
        controller = Robolectric.buildActivity(BrowserActivity.class).create(bundle).start().restoreInstanceState(bundle).resume();
        activity = controller.get();
        assertTrue(activity.rvRepos.getAdapter().getItemCount()==objQuantity);
    }

    @Test
    public void fetchReposByQueryErrorTest() {
        activity.presenter.fetchRepoByQuery("abc");
        Mockito.verify(mockGithubService).fetchRepoByQuery(anyString(), anyInt(), callbackAllReposCaptor.capture());
        String message = "501 - error message";
        callbackAllReposCaptor.getValue().onError(message);
        assertTrue(ShadowToast.getTextOfLatestToast().contains(message));

    }

    @Test
    public void fetchReposByQueryNetworkErrorTest() {
        activity.presenter.fetchRepoByQuery("abc");
        Mockito.verify(mockGithubService).fetchRepoByQuery(anyString(), anyInt(), callbackAllReposCaptor.capture());
        String message = "501 - error message";
        callbackAllReposCaptor.getValue().onNetworkError(message);
        assertTrue(ShadowToast.getTextOfLatestToast().contains(message));
    }
}