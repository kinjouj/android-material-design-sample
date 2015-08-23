package kinjouj.app.oretter;

import java.util.List;
import java.util.concurrent.*;

import android.app.Activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.rule.MockWebServerRule;

import static org.assertj.core.api.Assertions.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class TwitterApiTest {

    @Rule
    public final MockWebServerRule server = new MockWebServerRule();

    @Before
    public void setUp() {
        System.setProperty("twitter4j.restBaseURL", server.getUrl("/").toString());
        TwitterApi.twitter = null;
    }

    @After
    public void tearDown() {
        System.setProperty("twitetr4j.restBaseURL", "https://api.twitter.com/1.1/");
    }

    @Test
    public void test_getTwitter() throws Exception {
        Activity activity = Robolectric.setupActivity(Activity.class);
        assertThat(activity).isNotNull();

        Twitter twitter = new TwitterApi().getTwitter(activity);
        assertThat(twitter).isNotNull();
        assertThat(new TwitterApi().getTwitter(activity)).isSameAs(twitter);
    }

    @Test
    public void test_getHomeTimeline() throws Exception {
        server.enqueue(new MockResponse().setBody("[{ text: 'hoge'}, { text: 'fuga' }]"));

        Activity activity = Robolectric.setupActivity(Activity.class);
        List<Status> statuses = TwitterApi.getHomeTimeline(activity);
        assertThat(statuses).isNotEmpty();
        assertThat(statuses).hasSize(2);
    }


    @Test
    public void test_getUserTimeline() throws Exception {
        server.enqueue(new MockResponse().setBody("[{ text: 'hoge'}, { text: 'fuga' }]"));

        Activity activity = Robolectric.setupActivity(Activity.class);
        List<Status> statuses = TwitterApi.getUserTimeline(activity, 123);
        assertThat(statuses).isNotEmpty();
        assertThat(statuses).hasSize(2);
    }


    @Test
    public void test_getCurrentUser() throws Exception {
        server.enqueue(new MockResponse().setBody("{ id: 1, name: 'hoge', screen_name: 'hoge' }"));

        Activity activity = Robolectric.setupActivity(Activity.class);
        assertThat(activity).isNotNull();

        User user = TwitterApi.getCurrentUser(activity);
        assertThat(user).isNotNull();
        assertThat(user.getName()).isEqualTo("hoge");
        assertThat(TwitterApi.getCurrentUser(activity)).isSameAs(user);
    }
}
