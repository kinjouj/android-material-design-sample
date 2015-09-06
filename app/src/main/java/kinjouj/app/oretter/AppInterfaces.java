package kinjouj.app.oretter;

import java.util.List;

import android.support.design.widget.TabLayout;

public interface AppInterfaces {

    public static interface TabReselectedListener {
        void onTabReselected();
    }

    public static interface SortedListAdapter<T> {
        void add(T value);
        void addAll(List<T> values);
    }
}
