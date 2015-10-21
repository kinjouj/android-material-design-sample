package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.StatusAdapter;

public class UserFragment extends RecyclerViewFragment<Status> {

    private static final String EXTRA_KEY_USER = "extra_key_user";

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) throws TwitterException {
        return getTwitter().getUserTimeline(getUser().getId(), new Paging(currentPage));
    }

    User getUser() {
        Bundle args = getArguments();
        return (User) args.getSerializable(EXTRA_KEY_USER);
    }

    public static UserFragment build(User user) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_KEY_USER, user);

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
