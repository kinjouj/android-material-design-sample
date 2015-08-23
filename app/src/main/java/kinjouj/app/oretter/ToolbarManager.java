package kinjouj.app.oretter;

import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ToolbarManager implements Toolbar.OnMenuItemClickListener {

    AppCompatActivity activity;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public ToolbarManager(AppCompatActivity activity) {
        this.activity = activity;
        ButterKnife.bind(this, activity);
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
