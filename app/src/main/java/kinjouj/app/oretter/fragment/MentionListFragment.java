package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.StatusListRecyclerViewAdapter;

public class MentionListFragment extends RecyclerViewFragment<Status> {

    @Override
    public List<Status> fetch() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getMentionsTimeline(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusListRecyclerViewAdapter(getActivity());
    }
}
