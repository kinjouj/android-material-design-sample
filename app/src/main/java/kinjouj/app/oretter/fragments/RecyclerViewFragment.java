package kinjouj.app.oretter.fragments;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

public abstract class RecyclerViewFragment<T> extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener,
                AppInterfaces.ReloadableFragment {

    private static final String TAG = RecyclerViewFragment.class.getName();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = getAdapter();
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);
        load(null);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");
        ButterKnife.unbind(this);
        adapter = null;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        Log.v(TAG, "onResume");
        super.onResume();
        ((MainActivity)getActivity()).getAppBarLayoutManager().addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        ((MainActivity)getActivity()).getAppBarLayoutManager().removeOnOffsetChangedListener();
    }

    @Override
    public void onRefresh() {
        Log.v(TAG, "onRefresh");
        load(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBar, int verticalOffset) {
        Log.v(TAG, "onOffsetChanged: " + verticalOffset);
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    @Override
    public void reload() {
        if (recyclerView.computeVerticalScrollOffset() == 0) {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        } else {
            recyclerView.scrollToPosition(0);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        Configuration config = getResources().getConfiguration();

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        } else {
            return new LinearLayoutManager(getActivity());
        }
    }

    public Twitter getTwitter() {
        return TwitterFactory.getSingleton();
    }

    private void load(final Runnable callback) {
        new Thread() {
            @Override
            public void run() {
                final List<T> users = fetch();
                Activity activity = getActivity();

                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @SuppressWarnings("unchecked")
                        @Override
                        public void run() {
                            ((AppInterfaces.SortedListAdapter<T>)adapter).addAll(users);

                            if (callback != null) {
                                callback.run();
                            }
                        }
                    });
                }
            }
        }.start();
    }

    abstract RecyclerView.Adapter getAdapter();
    abstract List<T> fetch();

}
