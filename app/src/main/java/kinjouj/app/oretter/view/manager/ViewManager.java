package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;

public abstract class ViewManager<T extends AppCompatActivity> {

    private Activity activity;

    public ViewManager(Activity activity) {
        this.activity = activity;
        ButterKnife.bind(this, activity);
    }

    public void unbind() {
        ButterKnife.unbind(this);
        activity = null;
    }

    @SuppressWarnings("unchecked")
    protected T getActivity() {
        return (T)activity;
    }

    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }
}
