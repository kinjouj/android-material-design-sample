package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class UserStatusListFragment extends RecyclerViewFragment<Status> {

    public static final String EXTRA_USER = "extra_user";

    @Override
    public List<Status> fetch() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getUserTimeline(getActivity(), getUser().getId());
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

    private User getUser() {
        return (User)getArguments().getSerializable(EXTRA_USER);
    }

    public static UserStatusListFragment newInstance(User user) {
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_USER, user);

        UserStatusListFragment fragment = new UserStatusListFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
