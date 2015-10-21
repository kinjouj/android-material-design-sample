package kinjouj.app.oretter.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.UserList;

import kinjouj.app.oretter.view.adapter.StatusAdapter;

public class UserListFragment extends RecyclerViewFragment<Status> {

    private static final String EXTRA_KEY_USERLIST = "extra_key_userlist";
    private static final String TAG = UserListFragment.class.getName();

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) throws TwitterException {
        UserList userList = getUserList();
        Log.v(TAG, "userList:" + userList);
        return getTwitter().getUserListStatuses(userList.getId(), new Paging(currentPage));
    }

    UserList getUserList() {
        Bundle args = getArguments();
        return (UserList) args.getSerializable(EXTRA_KEY_USERLIST);
    }

    public static UserListFragment build(UserList userList) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_KEY_USERLIST, userList);

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
