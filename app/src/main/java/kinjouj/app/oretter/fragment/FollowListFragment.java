package kinjouj.app.oretter.fragment;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import twitter4j.User;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.UserListRecyclerViewAdapter;

public class FollowListFragment extends RecyclerViewFragment<User> {

    public static final String FRAGMENT_TAG = "fragemnt_follow_list";

    @Override
    public List<User> fetch() {
        List<User> users = null;

        try {
            users = TwitterApi.getFollows(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new UserListRecyclerViewAdapter(getActivity());
    }

    /*
    @Override
    public LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
    */
}
