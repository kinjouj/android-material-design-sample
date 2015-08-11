package kinjouj.app.oretter.fragment;

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

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.adapter.SampleAdapter;

public class StatusListRecyclerViewFragment extends RecyclerViewFragment {

    private static final String TAG = StatusListRecyclerViewFragment.class.getName();

    private Handler handler = new Handler();

    @Override
    public void onResume() {
        super.onResume();
        fetchTimeline(null);
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
