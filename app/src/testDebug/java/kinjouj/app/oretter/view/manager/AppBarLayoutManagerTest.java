package kinjouj.app.oretter.view.manager;

import android.support.design.widget.AppBarLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
public class AppBarLayoutManagerTest {

    private AppBarLayoutManager manager;

    @Before
    public void setUp() {
        AppBarLayout view = new AppBarLayout(RuntimeEnvironment.application);
        manager = new AppBarLayoutManager(view);
    }

    @Test
    public void test_listener() {
        AppBarLayout.OnOffsetChangedListener listener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            }
        };

        assertThat(manager, notNullValue());
        assertThat(manager.listener, nullValue());

        manager.addOnOffsetChangedListener(listener);
        assertThat(manager.listener, notNullValue());

        manager.removeOnOffsetChangedListener();
        assertThat(manager.listener, nullValue());

        manager.addOnOffsetChangedListener(listener);
        assertThat(manager.listener, notNullValue());
    }

    @Test
    public void test_unbind() {
        assertThat(manager.listener, nullValue());

        manager.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            }
        });
        assertThat(manager.listener, notNullValue());

        manager.unbind();
        assertThat(manager.listener, nullValue());
    }
}
