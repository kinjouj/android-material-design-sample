package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.SearchFragment;

public class SearchViewManager extends ViewManager<MainActivity> implements SearchView.OnQueryTextListener {

    SearchView searchView;

    public SearchViewManager(Activity activity, SearchView searchView) {
        super(activity);
        this.searchView = searchView;
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        collapse();
        TabLayoutManager tabLayoutManager = getActivity().getTabLayoutManager();
        final TabLayout.Tab tab = tabLayoutManager.addTab(
            "検索 " + query,
            R.drawable.ic_search,
            SearchFragment.newInstance(query)
        );
        tabLayoutManager.select(tab);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                    getActivity().runOnUiThread(new Runnable() {
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
