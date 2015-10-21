package kinjouj.app.oretter.fragments.list.status;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;

import kinjouj.app.oretter.fragments.RecyclerViewFragment;
import kinjouj.app.oretter.view.adapter.StatusAdapter;

public class HomeFragment extends RecyclerViewFragment<Status> {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) throws TwitterException {
        return getTwitter().getHomeTimeline(new Paging(currentPage));
    }
}
