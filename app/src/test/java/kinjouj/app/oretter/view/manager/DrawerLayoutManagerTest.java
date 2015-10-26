package kinjouj.app.oretter.view.manager;

import android.support.v4.view.GravityCompat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import kinjouj.app.oretter.BuildConfig;
import kinjouj.app.oretter.MainActivity;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class DrawerLayoutManagerTest {

    @Test
    public void test() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(activity, notNullValue());

        DrawerLayoutManager manager = activity.getDrawerLayoutManager();
        assertThat(manager, notNullValue());
        assertThat(manager.isOpen(), is(false));

        manager.getView().openDrawer(GravityCompat.START);
        assertThat(manager.isOpen(), is(true));

        manager.close();
        assertThat(manager.isOpen(), is(false));
    }
}
