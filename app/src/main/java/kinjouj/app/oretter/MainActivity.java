package kinjouj.app.oretter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Menu;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragment.HomeStatusListFragment;
import kinjouj.app.oretter.listeners.ToolbarOnItemClickListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String FRAGMENT_TAG = "current_fragment";

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

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

    private DrawerLayoutManager drawerLayoutManager;
    private SearchViewManager searchViewManager;
    private TabLayoutManager tabLayoutManager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        drawerLayoutManager = new DrawerLayoutManager(this, (DrawerLayout)findViewById(R.id.drawer_layout));
        tabLayoutManager = new TabLayoutManager(this, (TabLayout)findViewById(R.id.tab_layout));
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
                FragmentManager fm = getSupportFragmentManager();
                System.out.println(fm.findFragmentByTag(FRAGMENT_TAG));
            }
        }
    }

    @Override
    public boolean onSearchRequested() {
        Log.v(TAG, "onSearchRequested: onActionViewExpanded");
        searchViewManager.expand();

        return false;
    }

    public void setContentFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.addToBackStack(null);
        tx.replace(R.id.content, fragment, FRAGMENT_TAG);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.commit();
    }

    public void addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }

    public void removeOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.removeOnOffsetChangedListener(listener);
    }

    public DrawerLayoutManager getDrawerLayoutManager() {
        return drawerLayoutManager;
    }

    public TabLayoutManager getTabLayoutManager() {
        return tabLayoutManager;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(new ToolbarOnItemClickListener(this));
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
