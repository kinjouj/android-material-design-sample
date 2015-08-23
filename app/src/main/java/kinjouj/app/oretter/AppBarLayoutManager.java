package kinjouj.app.oretter;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppBarLayoutManager {

    Activity activity;

    @Bind(R.id.appbar_layout)
    AppBarLayout appBarLayout;

    public AppBarLayoutManager(Activity activity) {
        this.activity = activity;
        ButterKnife.bind(this, activity);
    }

    public void addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }

    public void removeOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.removeOnOffsetChangedListener(listener);
    }
}
