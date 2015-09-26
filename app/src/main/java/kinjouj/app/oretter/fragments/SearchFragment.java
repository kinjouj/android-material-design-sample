package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;

import kinjouj.app.oretter.view.adapter.StatusAdapter;

public class SearchFragment extends RecyclerViewFragment<Status> {

    private QueryResult queryResult;

    @Arg
    String query;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            Query query = new Query(this.query);

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
}
