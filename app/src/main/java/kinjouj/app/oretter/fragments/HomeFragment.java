package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import twitter4j.Paging;
import twitter4j.Status;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.view.adapter.StatusAdapter;

public class HomeFragment extends RecyclerViewFragment<Status> {

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            statuses = getTwitter().getHomeTimeline(new Paging(currentPage));
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }
}
