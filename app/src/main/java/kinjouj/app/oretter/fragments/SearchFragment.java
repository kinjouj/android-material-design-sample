package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class SearchFragment extends RecyclerViewFragment<Status> {

    private static final String EXTRA_QUERY = "extra_query";
    private QueryResult queryResult;

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusRecyclerViewAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            Query query = new Query(getQuery());

            if (queryResult != null) {
                if (queryResult.hasNext()) {
                    query = queryResult.nextQuery();
                }
            }
            queryResult = getTwitter().search(query);
            statuses = queryResult.getTweets();

        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
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
