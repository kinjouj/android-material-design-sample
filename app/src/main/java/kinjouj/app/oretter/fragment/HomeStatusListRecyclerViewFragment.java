package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import android.util.Log;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

public class HomeStatusListRecyclerViewFragment extends RecyclerViewFragment {

    private static final String TAG = HomeStatusListRecyclerViewFragment.class.getName();

    @Override
    public List<Status> fetchTimeline() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getHomeTimeline(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }
}
