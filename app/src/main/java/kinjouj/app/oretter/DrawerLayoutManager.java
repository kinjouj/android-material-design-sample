package kinjouj.app.oretter;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerLayoutManager {

    Activity activity;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    public DrawerLayoutManager(Activity activity) {
        this.activity = activity;
        ButterKnife.bind(this, activity);
        init();
    }

    private void init() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
            activity,
            drawerLayout,
            ((MainActivity)activity).getToolbarManager().getToolbar(),
            R.drawable.ic_drawer,
            R.drawable.ic_drawer
        );
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    public boolean isOpen() {
        return drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    public void close() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }
}
