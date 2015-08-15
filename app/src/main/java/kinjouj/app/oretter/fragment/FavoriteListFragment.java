package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

public class FavoriteListFragment extends RecyclerViewFragment {

    public static final String FRAGMENT_TAG = "fragment_favorite_list";

    @Override
    public List<Status> fetchTimeline() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getFavorites(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return statuses;
    }
}
