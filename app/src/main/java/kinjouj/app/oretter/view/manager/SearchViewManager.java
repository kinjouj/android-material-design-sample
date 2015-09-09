package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.view.View;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.SearchFragment;

public class SearchViewManager extends ViewManager<SearchView> implements SearchView.OnQueryTextListener {

    private Context context;

    public SearchViewManager(Context context, View view) {
        super(view);
        this.context = context;
        getView().setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        collapse();
        search(query);
        return false;
    }

    public void search(String query) {
        String title = "検索: " + query;
        SearchFragment fragment = SearchFragment.newInstance(query);
        TabLayoutManager tabManager = ((MainActivity) context).getTabLayoutManager();
        TabLayout.Tab tab = tabManager.addTab(title, R.drawable.ic_search, fragment);
        tabManager.select(tab, 300);
    }

    public boolean isIconified() {
        return getView().isIconified();
    }

    public void expand() {
        if (isIconified()) {
            getView().onActionViewExpanded();
        }
    }

    public void collapse() {
        if (!isIconified()) {
            getView().clearFocus();
            getView().onActionViewCollapsed();
        }
    }

    @Override
    public void unbind() {
        super.unbind();
        context = null;
    }
}
