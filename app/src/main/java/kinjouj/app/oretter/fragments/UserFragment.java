package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class UserFragment extends RecyclerViewFragment<Status> {

    public static final String EXTRA_USER = "extra_user";

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusRecyclerViewAdapter(getActivity());
    }

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            User user = getUser();
            statuses = getTwitter().getUserTimeline(user.getId(), new Paging(currentPage));
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }

    private User getUser() {
        return (User)getArguments().getSerializable(EXTRA_USER);
    }

    public static UserFragment newInstance(User user) {
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_USER, user);

        UserFragment fragment = new UserFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
