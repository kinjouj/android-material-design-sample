package kinjouj.app.oretter;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

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

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        appBarLayoutManager     = new AppBarLayoutManager(this);
        contentFragmentManager  = new ContentFragmentManager(this);
        toolbarManager          = new ToolbarManager(this);
        drawerLayoutManager     = new DrawerLayoutManager(this);
        tabLayoutManager        = new TabLayoutManager(this);
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
        if (!drawerLayoutManager.isOpen()) {
            Log.v(TAG, "onSearchRequested: onActionViewExpanded");
            searchViewManager.expand();
        }

        return false;
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

    public TabLayoutManager getTabLayoutManager() {
        return tabLayoutManager;
    }

    public ToolbarManager getToolbarManager() {
        return toolbarManager;
    }
}
