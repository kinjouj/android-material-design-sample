package kinjouj.app.oretter.fragment;

import java.util.List;

import android.os.Handler;
import android.widget.Toast;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

public class SearchRecyclerViewFragment extends RecyclerViewFragment {

    public static final String EXTRA_QUERY = "extra_query";
    private Handler handler = new Handler();

    @Override
    public void onResume() {
        super.onResume();
        fetchTimeline();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void fetchTimeline() {
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
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
