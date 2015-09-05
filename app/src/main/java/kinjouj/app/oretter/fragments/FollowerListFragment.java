package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.support.v7.widget.RecyclerView;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.UserRecyclerViewAdapter;

public class FollowerListFragment extends RecyclerViewFragment<User> {

    private PagableResponseList<User> users;

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserRecyclerViewAdapter(getActivity());
    }

    @Override
    public List<User> fetch(int currentPage) {
        List<User> findUsers = null;

        try {
            Twitter twitter = getTwitter();
            long cursor = -1;

            if (users != null) {
                if (users.hasNext()) {
                    cursor = users.getNextCursor();
                } else {
                    cursor = 0;
                }
            }

            User user = twitter.verifyCredentials();
            users = twitter.getFollowersList(user.getId(), cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}
