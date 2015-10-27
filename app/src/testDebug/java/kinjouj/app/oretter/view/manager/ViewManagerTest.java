package kinjouj.app.oretter.view.manager;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.assertj.android.api.Assertions.*;

@RunWith(RobolectricGradleTestRunner.class)
public class ViewManagerTest {

    private TextView textView;
    private ViewManager manager;

    @Before
    public void setUp() {
        textView = new TextView(RuntimeEnvironment.application);
        manager = new ViewManager(textView) {
            @Override
            public void unbind() {}
        };
    }

    @Test
    public void test_getView() {
        assertThat(manager.getView()).isNotNull();
        assertThat(manager.getView()).isSameAs(textView);
    }

    @Test
    public void test_getContext() {
        assertThat(manager.getContext(), notNullValue());
    }

    @Test
    public void test_destroyView() {
        assertThat(manager.getView()).isNotNull();

        manager.destroyView();
        assertThat(manager.getView()).isNull();
    }
}
