package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.Twitter;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.UserRecyclerViewAdapter;

public class FollowerListFragment extends RecyclerViewFragment<User> {

    @Override
    public List<User> fetch() {
        List<User> users = null;

        try {
            Twitter twitter = getTwitter();
            users = twitter.getFollowersList(twitter.verifyCredentials().getId(), -1);
        } catch (Exception e) {
            e.printStackTrace();
            users = Collections.<User>emptyList();
        }

        return users;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserRecyclerViewAdapter(getActivity());
    }
}
