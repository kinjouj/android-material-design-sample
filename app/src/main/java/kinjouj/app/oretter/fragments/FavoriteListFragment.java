package kinjouj.app.oretter.fragments;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.Paging;
import twitter4j.Status;

import kinjouj.app.oretter.view.adapter.StatusAdapter;
import twitter4j.TwitterException;

public class FavoriteListFragment extends RecyclerViewFragment<Status> {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) throws TwitterException {
        return getTwitter().getFavorites(new Paging(currentPage));
    }
}
