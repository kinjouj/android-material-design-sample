package kinjouj.app.oretter.fragments;

import java.util.Collections;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.oretter.view.adapter.StatusAdapter;

public class UserFragment extends RecyclerViewFragment<Status> {

    @Arg
    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return new StatusAdapter();
    }

    @Override
    public List<Status> fetch(int currentPage) {
        List<Status> statuses = null;

        try {
            statuses = getTwitter().getUserTimeline(user.getId(), new Paging(currentPage));
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }
}
