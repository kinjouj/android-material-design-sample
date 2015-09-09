package kinjouj.app.oretter;

import java.util.List;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

public interface AppInterfaces {

    public static interface FragmentRendererListener {
        void render(Fragment fragment);
    }

    public static interface SortedListAdapter<T> {
        void add(T value);
        void addAll(List<T> values);
    }
}
