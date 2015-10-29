package kinjouj.app.oretter.view.manager;

import android.support.v7.widget.SearchView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricGradleTestRunner.class)
public class SearchViewManagerTest {

    private SearchViewManager manager;

    @Before
    public void setUp() {
        SearchView view = new SearchView(RuntimeEnvironment.application);
        manager = new SearchViewManager(view);
        manager.getView().setOnQueryTextListener(null);
    }

    @Test
    public void test_iconified() {
        assertThat(manager.isIconified(), is(true));

        manager.expand();
        assertThat(manager.isIconified(), is(false));

        manager.collapse();
        assertThat(manager.isIconified(), is(true));
    }

    @Test
    public void test_query() {
        SearchViewManager _manager = Mockito.spy(manager);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                assertThat(args[0].toString(), is("A"));
                return null;
            }
        }).when(_manager).search(anyString());
        _manager.registerListener();
        _manager.listener.onQueryTextSubmit("A");
        verify(_manager, times(1)).search(anyString());
    }

    @Test
    public void test_unbind() {
        assertThat(manager.getView(), notNullValue());

        manager.registerListener();
        assertThat(manager.listener, notNullValue());

        manager.unbind();
        assertThat(manager.getView(), nullValue());
        assertThat(manager.listener, nullValue());
    }
}
