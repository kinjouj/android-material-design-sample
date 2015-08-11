package kinjouj.app.oretter.fragment;

import java.util.List;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

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
