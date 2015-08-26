package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.SearchFragment;

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
        String title = "検索: " + query;
        SearchFragment fragment = SearchFragment.newInstance(query);
        TabLayoutManager tabManager = getActivity().getTabLayoutManager();
        TabLayout.Tab tab = tabManager.addTab(title, R.drawable.ic_search, fragment);
        tabManager.select(tab, 300);

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
