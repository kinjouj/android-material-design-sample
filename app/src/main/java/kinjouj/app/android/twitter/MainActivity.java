package kinjouj.app.android.twitter;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.util.Log;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;

import butterknife.Bind;
import butterknife.ButterKnife;

import kinjouj.app.android.twitter.fragment.StatusListRecyclerViewFragment;
import kinjouj.app.android.twitter.view.DrawerHeaderView;

public class MainActivity extends AppCompatActivity
    implements Toolbar.OnMenuItemClickListener,
               NavigationView.OnNavigationItemSelectedListener,
               SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getName();

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.main);

        ButterKnife.bind(this);

        initNavigationView();
        initToolbar();

        FragmentTransaction transaction = getSupportFragmentManager() .beginTransaction();
        transaction.replace(R.id.content, new StatusListRecyclerViewFragment());
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        Log.v(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.tb_menu_search);
        searchView = (SearchView)MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "onBackPressed");

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (searchView != null && !searchView.isIconified()) {
                searchView.onActionViewCollapsed();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onSearchRequested() {
        Log.v(TAG, "onSearchRequested");

        if (searchView != null && searchView.isIconified()) {
            searchView.onActionViewExpanded();
        }

        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            default:
                showToast("unknown");
                break;
        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            default:
                showToast("unknown");
                break;

        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newString) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showToast("query: " + query);
        return false;
    }

    private void initNavigationView() {
        navigationView.addHeaderView(new DrawerHeaderView(this));
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

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

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }

    public void removeOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.removeOnOffsetChangedListener(listener);
    }
}
