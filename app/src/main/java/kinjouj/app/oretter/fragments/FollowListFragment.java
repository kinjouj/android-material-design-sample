package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.UserAdapter;

public class FollowListFragment extends RecyclerViewFragment<User> {

    long cursor = -1;

    @Override
    public void onPause() {
        super.onPause();
        cursor = -1;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserAdapter();
    }

    @Override
    public List<User> fetch(int currentPage) {
        List<User> users = null;

        try {
            Twitter twitter = getTwitter();
            PagableResponseList<User> _users = twitter.getFriendsList(
                twitter.verifyCredentials().getId(),
                cursor
            );
            cursor = _users.hasNext() ? _users.getNextCursor() : 0;
            users = _users;
        } catch (Exception e) {
            e.printStackTrace();
            users = Collections.<User>emptyList();
        }

        return users;
    }
}
