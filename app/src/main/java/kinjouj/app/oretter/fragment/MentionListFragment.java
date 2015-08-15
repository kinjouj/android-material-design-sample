package kinjouj.app.oretter.fragment;

import java.util.Collections;
import java.util.List;

import twitter4j.Status;

import kinjouj.app.oretter.TwitterApi;

public class MentionListFragment extends RecyclerViewFragment {

    public static final String FRAGMENT_TAG = "fragment_mention_list";

    public List<Status> fetchTimeline() {
        List<Status> statuses = null;

        try {
            statuses = TwitterApi.getMentionsTimeline(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
            statuses = Collections.<Status>emptyList();
        }

        return statuses;
    }
}
