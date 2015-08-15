package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;

import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.oretter.TwitterApi;

public class UserStatusListRecyclerViewFragment extends RecyclerViewFragment {

    public static final String EXTRA_USER = "extra_user";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    public List<Status> fetchTimeline() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getUserTimeline(getActivity(), getUser().getId());
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }

    private User getUser() {
        return (User)getArguments().getSerializable(EXTRA_USER);
    }

    public static UserStatusListRecyclerViewFragment newInstance(User user) {
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_USER, user);

        UserStatusListRecyclerViewFragment fragment = new UserStatusListRecyclerViewFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
