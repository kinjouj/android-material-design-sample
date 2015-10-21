package kinjouj.app.oretter.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import kinjouj.app.oretter.view.adapter.StatusAdapter;
import twitter4j.TwitterException;

public class SearchFragment extends RecyclerViewFragment<Status> {

    private static final String EXTRA_KEY_QUERY = "extra_key_query";
    private QueryResult queryResult;

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) throws TwitterException {
        Query query = new Query(getQuery());

        if (queryResult != null) {
            if (queryResult.hasNext()) {
                query = queryResult.nextQuery();
            }
        }

        queryResult = getTwitter().search(query);

        return queryResult.getTweets();
    }

    String getQuery() {
        Bundle args = getArguments();
        return args.getString(EXTRA_KEY_QUERY);
    }

    public static SearchFragment build(String query) {
        Bundle args = new Bundle();
        args.putString(EXTRA_KEY_QUERY, query);

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);

        return fragment;
    }
}