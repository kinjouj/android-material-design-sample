package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;

import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class FavoriteListFragment extends RecyclerViewFragment<Status> {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusRecyclerViewAdapter(getActivity());
    }

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            statuses = getTwitter().getFavorites(new Paging(currentPage));
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }
}
