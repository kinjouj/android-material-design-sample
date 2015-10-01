package kinjouj.app.oretter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragments.FavoriteListFragment;
import kinjouj.app.oretter.fragments.FollowListFragment;
import kinjouj.app.oretter.fragments.FollowerListFragment;
import kinjouj.app.oretter.fragments.HomeFragment;
import kinjouj.app.oretter.fragments.MentionListFragment;
import kinjouj.app.oretter.fragments.dialog.ComposeDialogFragment;
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

    @BindString(R.string.nav_menu_home)
    String homeTitle;

    @BindString(R.string.nav_menu_mention)
    String mentionTitle;

    @BindString(R.string.nav_menu_favorite)
    String favoriteTitle;

    @BindString(R.string.nav_menu_follow)
    String followTitle;

    @BindString(R.string.nav_menu_follower)
    String followerTitle;

    private void initViewManager() {
        EventManager.register(this);

        if (appBarLayoutManager == null) {
            appBarLayoutManager = new AppBarLayoutManager(appBarLayout);
        }

        if (drawerLayoutManager == null) {
            drawerLayoutManager = new DrawerLayoutManager(drawerLayout, toolbar);
        }

        if (searchViewManager == null) {
            searchViewManager = new SearchViewManager(
                MenuItemCompat.getActionView(toolbar.getMenu().findItem(R.id.tb_menu_search))
            );
        }

        if (tabLayoutManager == null) {
            tabLayoutManager = new TabLayoutManager(tabLayout);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        initViewManager();
        tabLayoutManager.addTab(homeTitle, R.drawable.ic_home, new HomeFragment(), true);
        tabLayoutManager.addTab(mentionTitle, R.drawable.ic_reply, new MentionListFragment());
        tabLayoutManager.addTab(favoriteTitle, R.drawable.ic_star, new FavoriteListFragment());
        tabLayoutManager.addTab(followTitle, R.drawable.ic_follow, new FollowListFragment());
        tabLayoutManager.addTab(followerTitle, R.drawable.ic_follower, new FollowerListFragment());
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

    public void onEvent(AppInterfaces.AppEvent event) {
        event.run(this);
    }
}
