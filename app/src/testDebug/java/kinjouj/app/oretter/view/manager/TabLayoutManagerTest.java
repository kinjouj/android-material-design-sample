package kinjouj.app.oretter.view.manager;

import android.support.design.widget.TabLayout;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLooper;

import kinjouj.app.oretter.R;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

@RunWith(RobolectricGradleTestRunner.class)
public class TabLayoutManagerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private TabLayoutManager manager;

    @Before
    public void setUp() {
        TabLayoutManager.backStackTabs.clear();
        TabLayout view = new TabLayout(RuntimeEnvironment.application);
        manager = new TabLayoutManager(view);
        manager.getView().setOnTabSelectedListener(null);
    }

    @Test
    public void test_addTab() {
        TabLayout.Tab tab1 = manager.addTab("test", R.drawable.ic_person, null);
        assertThat(tab1, notNullValue());
        assertThat(tab1.isSelected(), is(false));
        assertThat(manager.get(tab1.getPosition()).isSelected(), is(false));

        TabLayout.Tab tab2 = manager.addTab("test", R.drawable.ic_person, null, true);
        assertThat(tab2, notNullValue());
        assertThat(tab2.isSelected(), is(true));
        assertThat(manager.get(tab2.getPosition()).isSelected(), is(true));
    }

    @Test
    public void test_get() {
        TabLayout.Tab tab = manager.addTab("test", R.drawable.ic_person, null);
        assertThat(tab, notNullValue());

        assertThat(manager.get(0), notNullValue());
    }

    @Test
    public void test_select() {
        TabLayout.Tab tab = manager.addTab("test", R.drawable.ic_person, null);
        assertThat(manager.get(tab.getPosition()).isSelected(), is(false));
        manager.select(tab, 300);
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
        assertThat(manager.get(tab.getPosition()).isSelected(), is(true));
    }

    @Test
    public void test_addBackStack() {
        assertThat(TabLayoutManager.backStackTabs, hasSize(0));

        TabLayout.Tab tab = manager.addTab("test", R.drawable.ic_person, null);

        manager.addBackStack(tab);
        assertThat(TabLayoutManager.backStackTabs, hasSize(1));

        manager.addBackStack(tab);
        assertThat(TabLayoutManager.backStackTabs, hasSize(2));

        manager.backStackState = true;
        manager.addBackStack(tab);
        assertThat(TabLayoutManager.backStackTabs, hasSize(2));

        exception.expect(IllegalArgumentException.class);
        manager.addBackStack(null);
    }

    @Test
    public void test_hasBackStack() {
        assertThat(manager.hasBackStack(), is(false));

        TabLayout.Tab tab = manager.addTab("test", R.drawable.ic_person, null);
        manager.addBackStack(tab);
        assertThat(manager.hasBackStack(), is(true));
    }

    @Test
    public void test_popBackStack() {
        assertThat(manager.hasBackStack(), is(false));

        TabLayout.Tab tab = manager.addTab("test", R.drawable.ic_person, null);
        manager.addBackStack(tab);
        assertThat(manager.hasBackStack(), is(true));

        manager.popBackStack();
        assertThat(manager.hasBackStack(), is(false));
    }

    @Test
    public void test_clear() {
        TabLayout.Tab tab = manager.addTab("test", R.drawable.ic_person, null);
        manager.addBackStack(tab);
        assertThat(manager.getView().getTabCount(), is(1));
        assertThat(manager.hasBackStack(), is(true));

        manager.clear();
        assertThat(manager.getView().getTabCount(), is(0));
        assertThat(manager.hasBackStack(), is(false));
    }
}
