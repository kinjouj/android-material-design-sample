package kinjouj.app.oretter.view.manager;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.SearchView;
import android.view.View;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.EventManager;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.SearchFragmentBuilder;

public class SearchViewManager extends ViewManager<SearchView> implements SearchView.OnQueryTextListener {

    public SearchViewManager(View view) {
        super(view);
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

    @Override
    public void unbind() {
        getView().setOnQueryTextListener(null);
        destroyView();
    }

    public void search(final String query) {
        EventManager.getInstance().post(new AppInterfaces.AppEvent() {
            @Override
            public void run(Context context) {
                TabLayoutManager tabManager = ((MainActivity) context).getTabLayoutManager();
                TabLayout.Tab tab = tabManager.addTab(
                    "検索: " + query,
                    R.drawable.ic_search,
                    new SearchFragmentBuilder(query).build()
                );
                tabManager.select(tab, 300);
            }
        });
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
}
