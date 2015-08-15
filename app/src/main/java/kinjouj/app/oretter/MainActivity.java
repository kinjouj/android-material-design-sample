package kinjouj.app.oretter;

import java.util.List;

import android.os.Bundle;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

import kinjouj.app.oretter.fragment.HomeStatusListRecyclerViewFragment;
import kinjouj.app.oretter.fragment.SearchRecyclerViewFragment;

public class MainActivity extends AppCompatActivity
    implements Toolbar.OnMenuItemClickListener,
               SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getName();

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        initToolbar();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeStatusListRecyclerViewFragment());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
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
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (searchView != null && !searchView.isIconified()) {
                Log.v(TAG, "onBackPressed: SearchView.onActionViewCollapsed");
                searchView.onActionViewCollapsed();
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
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            default:
                break;
        }

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newString) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        searchView.onActionViewCollapsed();

        FragmentManager manager = getSupportFragmentManager();

        if (manager.findFragmentByTag(SearchRecyclerViewFragment.FRAGMENT_TAG) != null) {
            manager.popBackStack(SearchRecyclerViewFragment.FRAGMENT_TAG, 1);
        }

        SearchRecyclerViewFragment fragment = SearchRecyclerViewFragment.newInstance(query);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(SearchRecyclerViewFragment.FRAGMENT_TAG);
        transaction.replace(R.id.content, fragment, SearchRecyclerViewFragment.FRAGMENT_TAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();

        return false;
    }

    private void initToolbar() {
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.drawable.ic_drawer,
            R.drawable.ic_drawer
        );
        drawerLayout.setDrawerListener(drawerToggle);
        //drawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    public void addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }

    public void removeOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.removeOnOffsetChangedListener(listener);
    }
}
