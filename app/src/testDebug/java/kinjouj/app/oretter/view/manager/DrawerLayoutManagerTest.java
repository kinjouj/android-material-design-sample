package kinjouj.app.oretter.view.manager;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import butterknife.ButterKnife;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricGradleTestRunner.class)
public class DrawerLayoutManagerTest {

    private DrawerLayoutManager manager;

    @Before
    public void setUp() {
        MainActivity activity = spy(Robolectric.buildActivity(MainActivity.class).create().get());
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
