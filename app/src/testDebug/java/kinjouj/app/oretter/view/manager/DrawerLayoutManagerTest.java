package kinjouj.app.oretter.view.manager;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import kinjouj.app.oretter.MainActivity;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
public class DrawerLayoutManagerTest {

    private DrawerLayoutManager manager;

    @Before
    public void setUp() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        manager = activity.getDrawerLayoutManager();
    }

    @Test
    public void test_isOpen_close() {
        assertThat(manager.isOpen(), is(false));

        manager.getView().openDrawer(GravityCompat.START);
        assertThat(manager.isOpen(), is(true));

        manager.close();
        assertThat(manager.isOpen(), is(false));
    }
}
