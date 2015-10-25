package kinjouj.app.oretter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    @Test
    public void test() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(activity, notNullValue());
        assertThat(activity.getTabLayoutManager(), notNullValue());
    }
}
