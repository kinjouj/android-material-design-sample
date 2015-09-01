package kinjouj.app.oretter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.support.design.widget.TabLayout;
import butterknife.BindString;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragments.*;

import kinjouj.app.oretter.view.manager.AppBarLayoutManager;
import kinjouj.app.oretter.view.manager.ContentFragmentManager;
import kinjouj.app.oretter.view.manager.DrawerLayoutManager;
import kinjouj.app.oretter.view.manager.SearchViewManager;
import kinjouj.app.oretter.view.manager.TabLayoutManager;
import kinjouj.app.oretter.view.manager.ToolbarManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private AppBarLayoutManager appBarLayoutManager;
    private ContentFragmentManager contentFragmentManager;
    private DrawerLayoutManager drawerLayoutManager;
    private SearchViewManager searchViewManager;
    private TabLayoutManager tabLayoutManager;
    private ToolbarManager toolbarManager;

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

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        super.onResume();
        init();
        tabLayoutManager.addTab(homeTitle, R.drawable.ic_home, new HomeStatusListFragment(), true);
        tabLayoutManager.addTab(mentionTitle, R.drawable.ic_reply, new MentionListFragment());
        tabLayoutManager.addTab(favoriteTitle, R.drawable.ic_grade, new FavoriteListFragment());
        tabLayoutManager.addTab(followTitle, R.drawable.ic_follow, new FollowListFragment());
        tabLayoutManager.addTab(followerTitle, R.drawable.ic_follower, new FollowerListFragment());
    }

    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (appBarLayoutManager != null) {
            appBarLayoutManager.unbind();
            appBarLayoutManager = null;
        }

        if (contentFragmentManager != null) {
            contentFragmentManager.unbind();
            contentFragmentManager = null;
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

        if (toolbarManager != null) {
            toolbarManager.unbind();
            toolbarManager = null;
        }

        ButterKnife.unbind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        searchViewManager = new SearchViewManager(
            this,
            (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.tb_menu_search))
        );

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.v(TAG, "onConfigrationChanged()");
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
                Log.v(TAG, "count: " + tabLayoutManager.getBackStackTabEntryCount());

                if (tabLayoutManager.getBackStackTabEntryCount() > 0) {
                    tabLayoutManager.popBackStackTab();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public boolean onSearchRequested() {
        if (!drawerLayoutManager.isOpen()) {
            Log.v(TAG, "onSearchRequested: onActionViewExpanded");
            searchViewManager.expand();
        }

        return false;
    }

    public void init() {
        if (appBarLayoutManager == null) {
            appBarLayoutManager = new AppBarLayoutManager(this);
        }

        if (contentFragmentManager == null) {
            contentFragmentManager = new ContentFragmentManager(this);
        }

        if (toolbarManager == null) {
            toolbarManager = new ToolbarManager(this);
        }

        if (drawerLayoutManager == null) {
            drawerLayoutManager = new DrawerLayoutManager(this);
        }

        if (tabLayoutManager == null) {
            tabLayoutManager = new TabLayoutManager(this);
        }
    }

    public AppBarLayoutManager getAppBarLayoutManager() {
        return appBarLayoutManager;
    }

    public ContentFragmentManager getContentFragmentManager() {
        return contentFragmentManager;
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

    public ToolbarManager getToolbarManager() {
        return toolbarManager;
    }
}
