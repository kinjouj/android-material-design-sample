package kinjouj.app.oretter.listeners;

import android.content.Context;
import android.view.MenuItem;

import android.support.v7.widget.Toolbar;

public class ToolbarOnItemClickListener implements Toolbar.OnMenuItemClickListener {

    Context context;

    public ToolbarOnItemClickListener(Context context) {
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}
