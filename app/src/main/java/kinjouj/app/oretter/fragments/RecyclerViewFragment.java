package kinjouj.app.oretter.fragments;

import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.util.LayoutManagerUtil;
import kinjouj.app.oretter.util.ThreadUtil;

import static kinjouj.app.oretter.AppInterfaces.SortedListAdapter;

public abstract class RecyclerViewFragment<T> extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = RecyclerViewFragment.class.getName();
    private static final int FIRST_PAGE_NUM = 1;

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    int currentPage = 1;
    RecyclerView.OnScrollListener listener;

    @Override
    public void onAttach(Context context) {
        Log.v(TAG, "onAttach");
        super.onAttach(context);
        listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (ViewCompat.canScrollVertically(recyclerView, 1)) {
                    return;
                }

                onLoadMore();
                currentPage++;
            }
        };
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        Log.v(TAG, "onCreate");
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        Log.v(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(getAdapter());
        recyclerView.addOnScrollListener(listener);
        swipeRefreshLayout.setOnRefreshListener(this);
        load(FIRST_PAGE_NUM, null);

        return view;
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
    public void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.v(TAG, "onDestroyView");
        recyclerView.removeOnScrollListener(listener);
        recyclerView.setAdapter(null);
        listener = null;
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Log.v(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.v(TAG, "onConfugurationChanged");
        super.onConfigurationChanged(newConfig);
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        int pos = LayoutManagerUtil.findFirstVisibleItemPosition(recyclerView.getLayoutManager());

        if (pos > 0) {
            layoutManager.scrollToPosition(pos);
        }

        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onRefresh() {
        Log.v(TAG, "onRefresh");
        load(FIRST_PAGE_NUM, new Runnable() {
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
        Log.v(TAG, "onOffsetChanged: " + verticalOffset);
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    public Twitter getTwitter() {
        return TwitterFactory.getSingleton();
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return LayoutManagerUtil.getOrientationLayoutManager(getActivity());
    }

    public void scrollToTop() {
        if (recyclerView.computeVerticalScrollOffset() > 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    private void load(final int currentPage, final Runnable callback) {
        final Handler handler = new Handler();

        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                final List<T> data = fetch(currentPage);

                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        if (recyclerView != null) {
                            RecyclerView.Adapter adapter = recyclerView.getAdapter();

                            if (data != null && adapter != null) {
                                ((SortedListAdapter<T>) adapter).addAll(data);
                            }
                        }

                        if (callback != null) {
                            callback.run();
                        }
                    }
                });
            }
        });
    }

    private void onLoadMore() {
        load(currentPage + 1, null);
    }

    abstract RecyclerView.Adapter getAdapter();
    abstract List<T> fetch(int currentPage);

}
