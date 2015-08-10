package sample.app.fragment;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.AppBarLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

import twitter4j.Status;

import sample.app.MainActivity;
import sample.app.R;
import sample.app.util.TwitterApi;
import sample.app.view.adapter.SampleAdapter;

public class StatusListRecyclerViewFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
               AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = StatusListRecyclerViewFragment.class.getName();

    private Handler handler = new Handler();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private SampleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        Log.v(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.tweet_list, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Log.v(TAG, "onCreate");
        adapter = new SampleAdapter(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        ((MainActivity)getActivity()).addOnOffsetChangedListener(this);
        fetchTimeline(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
        ((MainActivity)getActivity()).removeOnOffsetChangedListener(this);
    }

    @Override
    public void onRefresh() {
        fetchTimeline(new Runnable() {
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

    private void fetchTimeline(final Runnable runOnUiThreadRunnable) {
        new Thread() {
            @Override
            public void run() {
                try {
                    final List<Status> statuses = TwitterApi.getHomeTimeline(getActivity());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addAll(statuses);

                            if (runOnUiThreadRunnable != null) {
                                runOnUiThreadRunnable.run();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                    final String errorMessage = e.getMessage();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();

                            if (runOnUiThreadRunnable != null) {
                                runOnUiThreadRunnable.run();
                            }
                        }
                    });
                }
            }
        }.start();
    }
}
