package kinjouj.app.oretter;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

public class DrawerLayoutManager {

    Context context;
    DrawerLayout drawerLayout;

    public DrawerLayoutManager(Context context, DrawerLayout drawerLayout) {
        this.context = context;
        this.drawerLayout = drawerLayout;
        init();
    }

    private void init() {
        MainActivity activity = (MainActivity)context;

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
            activity,
            drawerLayout,
            activity.getToolbar(),
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
