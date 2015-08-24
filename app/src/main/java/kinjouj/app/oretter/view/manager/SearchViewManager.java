package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.SearchFragment;

public class SearchViewManager implements SearchView.OnQueryTextListener {

    Activity activity;
    SearchView searchView;

    public SearchViewManager(Activity activity, SearchView searchView) {
        this.activity = activity;
        this.searchView = searchView;
        init();
    }

    private void init() {
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        collapse();
        ((MainActivity)activity).setContentFragment(SearchFragment.newInstance(query));

        TabLayoutManager tabLayoutManager = ((MainActivity)activity).getTabLayoutManager();
        final TabLayout.Tab tab = tabLayoutManager.addTab(
            "検索 " + query,
            R.drawable.ic_search,
            R.id.tab_menu_search
        );

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    activity.runOnUiThread(new Runnable() {
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

    public boolean isIconified() {
        return searchView != null && searchView.isIconified();
    }

    public void expand() {
        if (searchView != null && isIconified()) {
            searchView.onActionViewExpanded();
        }
    }

    public void collapse() {
        if (searchView != null && !isIconified()) {
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
    }
}
