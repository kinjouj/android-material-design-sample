package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class SearchFragment extends RecyclerViewFragment<Status> {

    private static final String EXTRA_QUERY = "extra_query";

    @Override
    public List<Status> fetch() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.search(getActivity(), getQuery());

        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusRecyclerViewAdapter(getActivity());
    }

    private String getQuery() {
        return getArguments().getString(EXTRA_QUERY);
    }

    public static SearchFragment newInstance(String query) {
        Bundle extras = new Bundle();
        extras.putString(EXTRA_QUERY, query);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
