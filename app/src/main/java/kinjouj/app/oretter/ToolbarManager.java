package kinjouj.app.oretter;

import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ToolbarManager implements Toolbar.OnMenuItemClickListener {

    private AppCompatActivity activity;
    private Toolbar toolbar;

    public ToolbarManager(AppCompatActivity activity, Toolbar toolbar) {
        this.activity = activity;
        this.toolbar = toolbar;
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
