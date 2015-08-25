package kinjouj.app.oretter.fragment;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import twitter4j.User;

import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.view.adapter.UserRecyclerViewAdapter;

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
        return new UserRecyclerViewAdapter(getActivity());
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }
}
