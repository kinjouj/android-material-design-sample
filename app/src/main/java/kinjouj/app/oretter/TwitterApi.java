package kinjouj.app.oretter;

import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import butterknife.BindString;
import butterknife.ButterKnife;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;

import kinjouj.app.oretter.R;

public class TwitterApi {

    private static final String TAG = TwitterApi.class.getName();

    @BindString(R.string.consumer_key)
    String consumerKey;

    @BindString(R.string.consumer_secret)
    String consumerSecret;

    @BindString(R.string.access_token)
    String accessToken;

    @BindString(R.string.access_token_secret)
    String accessTokenSecret;

    static Twitter twitter;
    private static User currentUser;

    Twitter getTwitter(Context context) {
        if (twitter == null) {
            Log.v(TAG, "getTwitter: " + context);
            ButterKnife.bind(this, (Activity)context);

            ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey)
                    .setOAuthConsumerSecret(consumerSecret)
                    .setOAuthAccessToken(accessToken)
                    .setOAuthAccessTokenSecret(accessTokenSecret);

            twitter = new TwitterFactory(builder.build()).getInstance();
        }

        return twitter;
    }

    public static List<Status> getHomeTimeline(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(context).getHomeTimeline(new Paging(1, 30));
        }
    }

    public static List<Status> getMentionsTimeline(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(context).getMentionsTimeline();
        }
    }

    public static List<Status> getUserTimeline(Context context, long userId) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(context).getUserTimeline(userId);
        }
    }

    public static List<UserList> getUserLists(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            Twitter twitter = new TwitterApi().getTwitter(context);
            User user = twitter.verifyCredentials();

            return twitter.getUserLists(user.getId());
        }
    }

    public static List<Status> getFavorites(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(context).getFavorites();
        }
    }

    public static List<Status> search(Context context, String query) throws Exception {
        return new TwitterApi().getTwitter(context).search(new Query(query)).getTweets();
    }

    public static List<User> getFollows(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(context).getFriendsList(
                getCurrentUser(context).getId(),
                -1
            );
        }
    }

    public static List<User> getFollowers(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            return new TwitterApi().getTwitter(context).getFollowersList(
                getCurrentUser(context).getId(),
                -1
            );
        }
    }

    public static User getCurrentUser(Context context) throws Exception {
        synchronized (TwitterApi.class) {
            if (currentUser == null) {
                currentUser = new TwitterApi().getTwitter(context).verifyCredentials();
            }

            return currentUser;
        }
    }
}
