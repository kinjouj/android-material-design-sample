package kinjouj.app.oretter.fragment;

import java.util.List;

import android.os.Handler;
import android.widget.Toast;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

public class SearchRecyclerViewFragment extends RecyclerViewFragment {

    public static final String FRAGMENT_TAG = "fragment_search";
    public static final String EXTRA_QUERY = "extra_query";
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

    private void fetchTimeline(final Runnable callback) {
        new Thread() {
            @Override
            public void run() {
                try {
                    final List<Status> statuses = TwitterApi.search(
                        getActivity(),
                        getArguments().getString(EXTRA_QUERY)
                    );

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addAll(statuses);

                            if (callback != null) {
                                callback.run();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                    if (callback != null) {
                        callback.run();
                    }
                }
            }
        }.start();
    }
}
