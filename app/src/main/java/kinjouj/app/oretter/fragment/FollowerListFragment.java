package kinjouj.app.oretter.fragment;

import java.util.List;

import android.support.v7.widget.RecyclerView;

import twitter4j.User;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.UserListRecyclerViewAdapter;

public class FollowerListFragment extends RecyclerViewFragment<User> {

    @Override
    public List<User> fetch() {
        List<User> users = null;

        try {
            users = TwitterApi.getFollowers(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserListRecyclerViewAdapter(getActivity());
    }
}
