package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;

public class ContentFragmentManager extends ViewManager<MainActivity> {

    private static final String TAG = ContentFragmentManager.class.getName();
    public static final String FRAGMENT_TAG = "current_fragment";

    public ContentFragmentManager(Activity activity) {
        super(activity);
    }

    public void render(Fragment fragment) {
        Log.v(TAG, "pos: " + getActivity().getTabLayoutManager().getCurrentPosition());
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.content, fragment, FRAGMENT_TAG);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        tx.commit();
    }

    /*
    public void detach() {
        Fragment fragment = getCurrentContentFragment();

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().detach(fragment).commit();
        }
    }

    public Fragment getCurrentContentFragment() {
        return getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    }
    */
}
