package kinjouj.app.oretter.fragments;

import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import kinjouj.app.oretter.util.ThreadUtil;

import static kinjouj.app.oretter.AppInterfaces.SortedListAdapter;

public abstract class RecyclerViewFragment<T> extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
                AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = RecyclerViewFragment.class.getName();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    RecyclerView.Adapter  adapter;
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
        recyclerView.addOnScrollListener(listener);
        load(1, null);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v(TAG, "onAttach");
        listener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
                load(currentPage, null);
            }
        };
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.v(TAG, "onDetach");
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
    }

    @Override
    public void onPause() {
        Log.v(TAG, "onPause");
        super.onPause();
        ((MainActivity) getActivity()).getAppBarLayoutManager().removeOnOffsetChangedListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.v(TAG, "onDestroyView");
        recyclerView.removeOnScrollListener(listener);
        listener = null;
        adapter = null;
        ButterKnife.unbind(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        RecyclerView.LayoutManager prevLayoutManager = recyclerView.getLayoutManager();
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int pos = LayoutManagerUtil.findFirstVisibleItemPosition(prevLayoutManager);

        if (pos != 0) {
            layoutManager.scrollToPosition(pos);
        }

        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {
        load(1, new Runnable() {
            @Override
            public void run() {
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBar, int verticalOffset) {
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    public void scrollToTop() {
        if (recyclerView.computeVerticalScrollOffset() > 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return LayoutManagerUtil.getOrientationLayoutManager(getActivity());
    }

    public Twitter getTwitter() {
        return TwitterFactory.getSingleton();
    }

    private void load(final int currentPage, final Runnable callback) {
        final Handler handler = new Handler();

        ThreadUtil.run(new Runnable() {
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
        });
    }

    abstract RecyclerView.Adapter getAdapter();
    abstract List<T> fetch(int currentPage);

}
