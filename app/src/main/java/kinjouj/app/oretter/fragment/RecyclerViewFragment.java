package kinjouj.app.oretter.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.AppBarLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

import kinjouj.app.oretter.ApplicationInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

public abstract class RecyclerViewFragment<T> extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener, AppBarLayout.OnOffsetChangedListener,
                ApplicationInterfaces.ReloadableFragment {

    private static final String TAG = RecyclerViewFragment.class.getName();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    protected RecyclerView.Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_tweet_list, container, false);
        ButterKnife.bind(this, view);

        adapter = getAdapter();
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);

        load(null);

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getAppBarLayoutManager().addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).getAppBarLayoutManager().removeOnOffsetChangedListener(this);
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
        Log.v(TAG, "computeVerticalScrollOffset" + recyclerView.computeVerticalScrollOffset());

        if (recyclerView.computeVerticalScrollOffset() == 0) {
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
        } else {
            recyclerView.scrollToPosition(0);
        }
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    private void load(final Runnable callback) {
        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {
                final List<T> users = fetch();
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void run() {
                        ((ApplicationInterfaces.SortedListAdapter<T>)adapter).addAll(users);

                        if (callback != null) callback.run();
                    }
                });
            }
        }.start();
    }

    abstract RecyclerView.Adapter getAdapter();
    abstract List<T> fetch();

}
