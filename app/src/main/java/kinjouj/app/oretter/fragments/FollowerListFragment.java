package kinjouj.app.oretter.fragments;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.UserRecyclerViewAdapter;

public class FollowerListFragment extends RecyclerViewFragment<User> {

    long cursor = -1;

    @Override
    public void onPause() {
        super.onPause();
        cursor = -1;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserRecyclerViewAdapter();
    }

    @Override
    public List<User> fetch(int currentPage) {
        PagableResponseList<User> users = null;

        try {
            Twitter twitter = getTwitter();
            users = twitter.getFollowersList(twitter.verifyCredentials().getId(), cursor);
            cursor = users.hasNext() ? users.getNextCursor() : 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
