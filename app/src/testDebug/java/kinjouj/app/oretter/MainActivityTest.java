package kinjouj.app.oretter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    @Test
    public void test() {
        //MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        assertThat(activity, notNullValue());
        assertThat(activity.getAppBarLayoutManager(), notNullValue());
        assertThat(activity.getDrawerLayoutManager(), notNullValue());
        assertThat(activity.getTabLayoutManager(), notNullValue());
    }
}
