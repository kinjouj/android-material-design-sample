package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

public class ContentFragmentManager extends ViewManager<MainActivity> {

    public static final String FRAGMENT_TAG = "current_fragment";

    public ContentFragmentManager(Activity activity) {
        super(activity);
    }

    public void render(Fragment fragment) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content, fragment, FRAGMENT_TAG);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.commit();
    }
}
