package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class FavoriteListFragment extends RecyclerViewFragment<Status> {

    @Override
    public List<Status> fetch() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getFavorites(getActivity());
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
}
