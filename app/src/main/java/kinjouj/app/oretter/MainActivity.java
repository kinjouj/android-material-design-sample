package kinjouj.app.oretter;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.Menu;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragment.FavoriteListFragment;
import kinjouj.app.oretter.fragment.FollowListFragment;
import kinjouj.app.oretter.fragment.FollowerListFragment;
import kinjouj.app.oretter.fragment.HomeStatusListFragment;
import kinjouj.app.oretter.fragment.MentionListFragment;
import kinjouj.app.oretter.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity
    implements  AppInterfaces.NavigateTabListener,
                SearchView.OnQueryTextListener,
                TabLayout.OnTabSelectedListener {

    private static final String TAG = MainActivity.class.getName();
    private static final String FRAGMENT_TAG = "current_fragment";

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

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

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolbar();
        initTabLayout();
        setContentFragment(new HomeStatusListFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        searchView = (SearchView)MenuItemCompat.getActionView(menu.findItem(R.id.tb_menu_search));
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            Log.v(TAG, "onBackPressed: closeDrawer");
            closeDrawer();
        } else {
            if (searchView != null && !searchView.isIconified()) {
                Log.v(TAG, "onBackPressed: SearchView.onActionViewCollapsed");
                collapseSearchView();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onSearchRequested() {
        if (searchView != null && searchView.isIconified()) {
            Log.v(TAG, "onSearchRequested: onActionViewExpanded");
            searchView.onActionViewExpanded();
        }

        return false;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Object tag = tab.getTag();
        int tagId = tag != null ? (int)tag : -1;

        switch (tagId) {
            case R.id.tab_menu_home:
                setContentFragment(new HomeStatusListFragment());
                break;

            case R.id.tab_menu_mention:
                setContentFragment(new MentionListFragment());
                break;

            case R.id.tab_menu_favorite:
                setContentFragment(new FavoriteListFragment());
                break;

            case R.id.tab_menu_follow:
                setContentFragment(new FollowListFragment());
                break;

            case R.id.tab_menu_follower:
                setContentFragment(new FollowerListFragment());
                break;

            default:
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
    }

    @Override
    public boolean onQueryTextChange(String newString) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        collapseSearchView();
        final TabLayout.Tab tab = createTab(
            "検索 " + query,
            R.drawable.ic_search,
            R.id.tab_menu_search
        );
        tabLayout.addTab(tab);
        setContentFragment(SearchFragment.newInstance(query));

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tab.select();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return false;
    }

    @Override
    public void navigateTab(int position) {
        tabLayout.getTabAt(position).select();
    }

    void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setOnMenuItemClickListener(new ToolbarOnItemClickListener(this));

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.drawable.ic_drawer,
            R.drawable.ic_drawer
        );
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    void initTabLayout() {
        tabLayout.setOnTabSelectedListener(this);

        tabLayout.addTab(
            createTab("マイツイ", R.drawable.ic_home, R.id.tab_menu_my)
        );

        tabLayout.addTab(
            createTab(navHomeTitle, R.drawable.ic_home, R.id.tab_menu_home),
            true
        );

        tabLayout.addTab(
            createTab(navMentionTitle, R.drawable.ic_reply, R.id.tab_menu_mention)
        );

        tabLayout.addTab(
            createTab(navFavoriteTitle, R.drawable.ic_grade, R.id.tab_menu_favorite)
        );

        tabLayout.addTab(
            createTab(navFollowTitle, R.drawable.ic_follow, R.id.tab_menu_follow)
        );

        tabLayout.addTab(
            createTab(navFollowerTitle, R.drawable.ic_follower, R.id.tab_menu_follower)
        );
    }

    TabLayout.Tab createTab(String text, int drawableResId, int tagResId) {
        return tabLayout.newTab().setText(text).setIcon(drawableResId).setTag(tagResId);
    }

    void collapseSearchView() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
    }

    void setContentFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction tx = fm.beginTransaction();
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

    public void closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
