package kinjouj.app.oretter.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.UserList;

import kinjouj.app.oretter.view.adapter.StatusRecyclerViewAdapter;

public class UserListFragment extends RecyclerViewFragment<Status> {

    private static final String EXTRA_USER_LIST = "extra_userlist";

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            statuses = getTwitter().getUserListStatuses(
                getUserList().getId(),
                new Paging(currentPage)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statuses;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusRecyclerViewAdapter();
    }


    private UserList getUserList() {
        return (UserList)getArguments().getSerializable(EXTRA_USER_LIST);
    }

    public static UserListFragment newInstance(UserList userList) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER_LIST, userList);

        UserListFragment fragment = new UserListFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
