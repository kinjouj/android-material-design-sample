package kinjouj.app.oretter.fragment;

import java.util.List;

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
import butterknife.BindString;
import butterknife.ButterKnife;
import twitter4j.Status;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.adapter.StatusListRecyclerViewAdapter;

public abstract class RecyclerViewFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
               AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = RecyclerViewFragment.class.getName();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    protected StatusListRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.tweet_list, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
        adapter = new StatusListRecyclerViewAdapter(getActivity());
        load(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MainActivity)getActivity()).removeOnOffsetChangedListener(this);
    }

    @Override
    public void onRefresh() {
        load(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBar, int verticalOffset) {
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }

    private void load(final Runnable callback) {
        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {
                final List<Status> statuses = fetchTimeline();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addAll(statuses);

                        if (callback != null) callback.run();
                    }
                });
            }
        }.start();
    }

    abstract List<Status> fetchTimeline();

}
