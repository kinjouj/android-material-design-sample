package kinjouj.app.oretter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;

import kinjouj.app.oretter.fragment.SearchFragment;

public class SearchViewManager implements SearchView.OnQueryTextListener {

    Context context;
    SearchView searchView;

    public SearchViewManager(Context context, SearchView searchView) {
        this.context = context;
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
        final MainActivity activity = (MainActivity)context;

        TabLayoutManager tabLayoutManager = activity.getTabLayoutManager();
        final TabLayout.Tab tab = tabLayoutManager.addTab("検索 " + query, R.drawable.ic_search, R.id.tab_menu_search);

        activity.setContentFragment(SearchFragment.newInstance(query));

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
