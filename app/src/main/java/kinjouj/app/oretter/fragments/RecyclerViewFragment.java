package kinjouj.app.oretter.fragments;

import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import kinjouj.app.oretter.EndlessScrollListener;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.util.LayoutManagerUtil;

import static kinjouj.app.oretter.AppInterfaces.SortedListAdapter;

public abstract class RecyclerViewFragment<T> extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
                AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = RecyclerViewFragment.class.getName();
    public static int STAGGERED_GRID_NUM_COLUMNS = 2;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    RecyclerView.Adapter adapter;
    EndlessScrollListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        Log.v(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = getAdapter();

        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        load(1, null);

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
        ((MainActivity) getActivity()).getAppBarLayoutManager().addOnOffsetChangedListener(this);
        listener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                load(currentPage, null);
            }
        };
        recyclerView.addOnScrollListener(listener);
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        ((MainActivity) getActivity()).getAppBarLayoutManager().removeOnOffsetChangedListener();
        recyclerView.removeOnScrollListener(listener);
        listener = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        RecyclerView.LayoutManager previousLayoutManager = recyclerView.getLayoutManager();
        int pos = LayoutManagerUtil.findFirstVisibleItemPosition(previousLayoutManager);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();

        if (pos != 0) {
            layoutManager.scrollToPosition(pos);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.removeOnScrollListener(listener);
        recyclerView.addOnScrollListener(listener);
    }

    @Override
    public void onRefresh() {
        Log.v(TAG, "onRefresh");
        load(1, new Runnable() {
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

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return LayoutManagerUtil.getOrientationLayoutManager(getActivity());
    }

    public Twitter getTwitter() {
        return TwitterFactory.getSingleton();
    }

    private void load(final int currentPage, final Runnable callback) {
        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {
                final List<T> users = fetch(currentPage);

                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        if (users != null && adapter != null) {
                            ((SortedListAdapter<T>)adapter).addAll(users);
                        }

                        if (callback != null) {
                            callback.run();
                        }
                    }
                });
            }
        }.start();
    }

    abstract RecyclerView.Adapter getAdapter();
    abstract List<T> fetch(int currentPage);

}
