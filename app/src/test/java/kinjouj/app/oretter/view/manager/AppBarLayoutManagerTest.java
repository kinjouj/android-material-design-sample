package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;

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
public class AppBarLayoutManagerTest {

    @Test
    public void test() {
        AppBarLayout.OnOffsetChangedListener listener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
            }
        };

        MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(activity, notNullValue());

        AppBarLayoutManager manager = activity.getAppBarLayoutManager();
        assertThat(manager, notNullValue());
        assertThat(manager.listener, nullValue());

        manager.addOnOffsetChangedListener(listener);
        assertThat(manager.listener, notNullValue());

        manager.removeOnOffsetChangedListener();
        assertThat(manager.listener, nullValue());

        manager.addOnOffsetChangedListener(listener);
        assertThat(manager.listener, notNullValue());

        manager.unbind();
        assertThat(manager.listener, nullValue());
    }
}
