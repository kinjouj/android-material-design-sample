package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import kinjouj.app.oretter.R;

public class DrawerLayoutManager extends ViewManager<DrawerLayout> {

    private Toolbar toolbar;

    public DrawerLayoutManager(View view, Toolbar toolbar) {
        super(view);
        this.toolbar = toolbar;
        init();
    }

    private void init() {
        DrawerLayout view = getView();
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
            (Activity)view.getContext(),
            view,
            toolbar,
            R.drawable.ic_drawer,
            R.drawable.ic_drawer
        );
        getView().setDrawerListener(drawerToggle);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
    }

    public boolean isOpen() {
        return getView().isDrawerOpen(GravityCompat.START);
    }

    public void close() {
        getView().closeDrawer(GravityCompat.START);
    }
}
