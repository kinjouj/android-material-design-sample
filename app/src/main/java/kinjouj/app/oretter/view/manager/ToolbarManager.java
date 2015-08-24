package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

public class ToolbarManager extends ViewManager<MainActivity> implements Toolbar.OnMenuItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public ToolbarManager(Activity activity) {
        super(activity);
        init();
    }

    public void init() {
        toolbar.setOnMenuItemClickListener(this);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}
