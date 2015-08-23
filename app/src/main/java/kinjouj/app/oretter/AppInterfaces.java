package kinjouj.app.oretter;

import java.util.List;
import android.support.v4.app.Fragment;

public interface AppInterfaces {

    public static interface ContentFragmentHandler {
        void setContentFragment(Fragment fragment);
    }

    public static interface ReloadableFragment {
        void reload();
    }

    public static interface SortedListAdapter<T> {
        void add(T value);
        void addAll(List<T> values);
    }
}
