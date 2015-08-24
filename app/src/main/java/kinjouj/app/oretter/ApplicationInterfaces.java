package kinjouj.app.oretter;

import java.util.List;

public interface ApplicationInterfaces {

    public static interface ReloadableFragment {
        void reload();
    }

    public static interface SortedListAdapter<T> {
        void add(T value);
        void addAll(List<T> values);
    }

}
