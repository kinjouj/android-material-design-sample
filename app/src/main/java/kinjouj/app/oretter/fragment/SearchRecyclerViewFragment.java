package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

public class SearchRecyclerViewFragment extends RecyclerViewFragment {

    public static final String FRAGMENT_TAG = "fragment_search";
    private static final String EXTRA_QUERY = "extra_query";

    public List<Status> fetchTimeline() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.search(getActivity(), getQuery());

        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }

    private String getQuery() {
        return getArguments().getString(EXTRA_QUERY);
    }

    public static SearchRecyclerViewFragment newInstance(String query) {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUERY, query);

        SearchRecyclerViewFragment fragment = new SearchRecyclerViewFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
