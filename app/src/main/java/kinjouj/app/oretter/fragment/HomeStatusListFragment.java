package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import twitter4j.Status;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.StatusListRecyclerViewAdapter;

public class HomeStatusListFragment extends RecyclerViewFragment<Status> {

    private static final String TAG = HomeStatusListFragment.class.getName();

    @Override
    public List<Status> fetch() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getHomeTimeline(getActivity());
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
