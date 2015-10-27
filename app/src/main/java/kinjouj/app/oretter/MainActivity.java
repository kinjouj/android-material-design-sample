package kinjouj.app.oretter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragments.list.status.FavoriteListFragment;
import kinjouj.app.oretter.fragments.list.status.HomeFragment;
import kinjouj.app.oretter.fragments.list.status.MentionListFragment;
import kinjouj.app.oretter.fragments.list.user.FollowListFragment;
import kinjouj.app.oretter.fragments.list.user.FollowerListFragment;
import kinjouj.app.oretter.view.manager.AppBarLayoutManager;
import kinjouj.app.oretter.view.manager.DrawerLayoutManager;
import kinjouj.app.oretter.view.manager.SearchViewManager;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AppBarLayoutManager appBarLayoutManager;
    private DrawerLayoutManager drawerLayoutManager;
    private SearchViewManager searchViewManager;
    private TabLayoutManager tabLayoutManager;

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    void initViewManager() {
        Log.v(TAG, "initViewManager");
        EventManager.register(this);

        if (appBarLayoutManager == null) {
            appBarLayoutManager = new AppBarLayoutManager(appBarLayout);
        }

        if (drawerLayoutManager == null) {
            drawerLayoutManager = new DrawerLayoutManager(drawerLayout, toolbar);
        }

        if (searchViewManager == null) {
            MenuItem menuItem = toolbar.getMenu().findItem(R.id.tb_menu_search);
            searchViewManager = new SearchViewManager(MenuItemCompat.getActionView(menuItem));
        }

        if (tabLayoutManager == null) {
            tabLayoutManager = new TabLayoutManager(tabLayout);
        }
    }

    void initTab() {
        Log.v(TAG, "initTab");

        tabLayoutManager.addTab(
            getString(R.string.nav_menu_home),
            R.drawable.ic_home,
            new HomeFragment(),
            true
        );

        tabLayoutManager.addTab(
            getString(R.string.nav_menu_mention),
            R.drawable.ic_reply,
            new MentionListFragment()
        );

        tabLayoutManager.addTab(
            getString(R.string.nav_menu_favorite),
            R.drawable.ic_star,
            new FavoriteListFragment()
        );

        tabLayoutManager.addTab(
            getString(R.string.nav_menu_follow),
            R.drawable.ic_follow,
            new FollowListFragment()
        );

        tabLayoutManager.addTab(
            getString(R.string.nav_menu_follower),
            R.drawable.ic_follower,
            new FollowerListFragment()
        );
    }

    @Override
    protected void onCreate(Bundle bundle) {
        Log.v(TAG, "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        initViewManager();
        initTab();
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
    }

    @Override
    public void onStop() {
        Log.v(TAG, "onStop");
        super.onStop();

        if (appBarLayoutManager != null) {
            appBarLayoutManager.unbind();
            appBarLayoutManager = null;
        }

        if (drawerLayoutManager != null) {
            drawerLayoutManager.unbind();
            drawerLayoutManager = null;
        }

        if (searchViewManager != null) {
            searchViewManager.unbind();
            searchViewManager = null;
        }

        if (tabLayoutManager != null) {
            tabLayoutManager.unbind();
            tabLayoutManager = null;
        }

        EventManager.unregister(this);
        ButterKnife.unbind(this);
    }

    @Override
    public void onRestart() {
        Log.v(TAG, "onRestart");
        super.onRestart();
        ButterKnife.bind(this);
        initViewManager();
    }

    /*
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tb_menu_compose:
                ComposeDialogFragment.open(getSupportFragmentManager());
                break;

            default:
                break;
        }

        return false;
    }
    */

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.v(TAG, "onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayoutManager.isOpen()) {
            Log.v(TAG, "onBackPressed: closeDrawer");
            drawerLayoutManager.close();
        } else {
            if (!searchViewManager.isIconified()) {
                Log.v(TAG, "onBackPressed: SearchView.onActionViewCollapsed");
                searchViewManager.collapse();
            } else {
                if (tabLayoutManager.hasBackStack()) {
                    tabLayoutManager.popBackStack();
                } else {
                    tabLayoutManager.clear();
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onSearchRequested() {
        Log.v(TAG, "onSearchRequested");

        if (!drawerLayoutManager.isOpen()) {
            searchViewManager.expand();
        }

        return false;
    }

    public AppBarLayoutManager getAppBarLayoutManager() {
        return appBarLayoutManager;
    }

    public DrawerLayoutManager getDrawerLayoutManager() {
        return drawerLayoutManager;
    }

    public SearchViewManager getSearchViewManager() {
        return searchViewManager;
    }

    public TabLayoutManager getTabLayoutManager() {
        return tabLayoutManager;
    }

    public void onEventMainThread(AppInterfaces.AppEvent event) {
        Log.v(TAG, "onEvent: " + event);
        event.run(this);
    }
}
