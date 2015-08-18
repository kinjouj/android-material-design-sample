package kinjouj.app.oretter;

import java.util.List;

public interface SortedListAdapter<T> {
    void add(T value);
    void addAll(List<T> values);
}
