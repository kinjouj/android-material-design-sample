package kinjouj.app.oretter.fragments.list.user;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import kinjouj.app.oretter.fragments.RecyclerViewFragment;
import kinjouj.app.oretter.view.adapter.UserAdapter;

public class FollowerListFragment extends RecyclerViewFragment<User> {

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
    public List<User> fetch(int currentPage) throws TwitterException {
        Twitter twitter = getTwitter();
        long id = twitter.verifyCredentials().getId();
        PagableResponseList<User> users = twitter.getFollowersList(id, cursor);
        cursor = users.hasNext() ? users.getNextCursor() : 0;

        return users;
    }
}
