package kinjouj.app.oretter;

import java.util.List;

public interface AppInterfaces {

    public static interface NavigateTabListener {
        public void navigateTab(int position);
    }

    public static interface SortedListAdapter<T> {
        void add(T value);
        void addAll(List<T> values);
    }
}
