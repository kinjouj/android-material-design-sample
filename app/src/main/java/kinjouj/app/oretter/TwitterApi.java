package kinjouj.app.oretter;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;

import butterknife.BindString;
import butterknife.ButterKnife;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;

import kinjouj.app.oretter.R;

public class TwitterApi {

    @BindString(R.string.consumer_key)
    String consumerKey;

    @BindString(R.string.consumer_secret)
    String consumerSecret;

    @BindString(R.string.access_token)
    String accessToken;

    @BindString(R.string.access_token_secret)
    String accessTokenSecret;

    private static Twitter twitter;

    private synchronized Twitter getTwitter(Activity activity) {
        if (twitter == null) {
            ButterKnife.bind(this, activity);

            twitter = TwitterFactory.getSingleton();
            twitter.setOAuthConsumer(consumerKey, consumerSecret);
            twitter.setOAuthAccessToken(
                new AccessToken(accessToken, accessTokenSecret)
            );
        }

        return twitter;
    }

    public static List<Status> getHomeTimeline(Activity activity) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(activity).getHomeTimeline(new Paging(1, 30));
        }
    }

    public static List<Status> getMentionsTimeline(Activity activity) throws Exception {
        return new TwitterApi().getTwitter(activity).getMentionsTimeline();
    }

    public static List<Status> getUserTimeline(Activity activity, long userId) throws Exception {
        return new TwitterApi().getTwitter(activity).getUserTimeline(userId);
    }

    public static List<UserList> getUserLists(Activity activity) throws Exception {
        Twitter twitter = new TwitterApi().getTwitter(activity);
        User user = twitter.verifyCredentials();

        return twitter.getUserLists(user.getId());
    }

    public static List<Status> getFavorites(Activity activity) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(activity).getFavorites();
        }
    }

    public static List<Status> search(Activity activity, String query) throws Exception {
        return new TwitterApi().getTwitter(activity).search(new Query(query)).getTweets();
    }

    public static User getCurrentUser(Activity activity) throws Exception {
        synchronized (TwitterApi.class) {
            return new VerifyCredentialsAPI(new TwitterApi().getTwitter(activity)).call();
        }
    }

    public static class VerifyCredentialsAPI {

        private static final String TAG = VerifyCredentialsAPI.class.getName();

        private Twitter twitter;
        private int retryCount = 0;

        public VerifyCredentialsAPI(Twitter twitter) {
            this.twitter = twitter;
        }

        public User call() {
            User user = null;

            try {
                user = twitter.verifyCredentials();
                retryCount = 0;
            } catch (Exception e) {
                e.printStackTrace();

                if (retryCount < 3) {
                    retryCount++;
                    Log.v(TAG, "retry: " + retryCount);
                    user = call();
                }
            }

            return user;
        }
    }
}
