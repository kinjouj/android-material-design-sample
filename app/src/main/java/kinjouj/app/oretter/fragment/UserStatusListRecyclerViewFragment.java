package kinjouj.app.oretter.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import twitter4j.Status;
import kinjouj.app.oretter.TwitterApi;

public class UserStatusListRecyclerViewFragment extends RecyclerViewFragment {

    public static final String EXTRA_USER_ID = "extra_user_id";

    public List<Status> fetchTimeline() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getUserTimeline(getActivity(), getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statuses;
    }

    private long getUserId() {
        return getArguments().getLong(EXTRA_USER_ID);
    }

    public static UserStatusListRecyclerViewFragment newInstance(long id) {
        Bundle extras = new Bundle();
        extras.putLong(EXTRA_USER_ID, id);

        UserStatusListRecyclerViewFragment fragment = new UserStatusListRecyclerViewFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
