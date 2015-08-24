package kinjouj.app.oretter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import butterknife.BindString;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragment.HomeStatusListFragment;
import kinjouj.app.oretter.view.manager.AppBarLayoutManager;
import kinjouj.app.oretter.view.manager.DrawerLayoutManager;
import kinjouj.app.oretter.view.manager.SearchViewManager;
import kinjouj.app.oretter.view.manager.TabLayoutManager;
import kinjouj.app.oretter.view.manager.ToolbarManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    public static final String FRAGMENT_TAG = "current_fragment";

    @BindString(R.string.nav_menu_my)
    String navMyTweetTitle;

    @BindString(R.string.nav_menu_home)
    String navHomeTitle;

    @BindString(R.string.nav_menu_mention)
    String navMentionTitle;

    @BindString(R.string.nav_menu_favorite)
    String navFavoriteTitle;

    @BindString(R.string.nav_menu_follow)
    String navFollowTitle;

    @BindString(R.string.nav_menu_follower)
    String navFollowerTitle;

    private AppBarLayoutManager appBarLayoutManager;
    private DrawerLayoutManager drawerLayoutManager;
    private SearchViewManager searchViewManager;
    private TabLayoutManager tabLayoutManager;
    private ToolbarManager toolbarManager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        appBarLayoutManager = new AppBarLayoutManager(this);
        toolbarManager      = new ToolbarManager(this);
        drawerLayoutManager = new DrawerLayoutManager(this);
        tabLayoutManager    = new TabLayoutManager(this);
        ButterKnife.bind(this);
        initToolbar();
        initTabLayout();
        setContentFragment(new HomeStatusListFragment());
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
    public void onBackPressed() {
        if (drawerLayoutManager.isOpen()) {
            Log.v(TAG, "onBackPressed: closeDrawer");
            drawerLayoutManager.close();
        } else {
            if (!searchViewManager.isIconified()) {
                Log.v(TAG, "onBackPressed: SearchView.onActionViewCollapsed");
                searchViewManager.collapse();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onSearchRequested() {
        Log.v(TAG, "onSearchRequested: onActionViewExpanded");

        if (!drawerLayoutManager.isOpen()) {
            searchViewManager.expand();
        }

        return false;
    }

    public void setContentFragment(Fragment fragment) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content, fragment, FRAGMENT_TAG);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.commit();
    }

    public AppBarLayoutManager getAppBarLayoutManager() {
        return appBarLayoutManager;
    }

    public DrawerLayoutManager getDrawerLayoutManager() {
        return drawerLayoutManager;
    }

    public TabLayoutManager getTabLayoutManager() {
        return tabLayoutManager;
    }

    public ToolbarManager getToolbarManager() {
        return toolbarManager;
    }

    void initToolbar() {
        setSupportActionBar(toolbarManager.getToolbar());
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void initTabLayout() {
        tabLayoutManager.addTab(navMyTweetTitle, R.drawable.ic_home, R.id.tab_menu_my);
        tabLayoutManager.addTab(navHomeTitle, R.drawable.ic_home, R.id.tab_menu_home, true);
        tabLayoutManager.addTab(navMentionTitle, R.drawable.ic_reply, R.id.tab_menu_mention);
        tabLayoutManager.addTab(navFavoriteTitle, R.drawable.ic_grade, R.id.tab_menu_favorite);
        tabLayoutManager.addTab(navFollowTitle, R.drawable.ic_follow, R.id.tab_menu_follow);
        tabLayoutManager.addTab(navFollowerTitle, R.drawable.ic_follower, R.id.tab_menu_follower);
    }
}
