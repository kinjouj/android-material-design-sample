package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import butterknife.Bind;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

public class DrawerLayoutManager extends ViewManager<MainActivity> {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    public DrawerLayoutManager(Activity activity) {
        super(activity);
        init();
    }

    private void init() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
            getActivity(),
            drawerLayout,
            ((MainActivity)getActivity()).getToolbarManager().getToolbar(),
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
