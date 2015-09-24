package kinjouj.app.oretter.view.manager;

import android.content.Context;
import android.view.View;

public abstract class ViewManager<T extends View> {

    private View view;

    public ViewManager(View view) {
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    public T getView() {
        return (T) view;
    }

    public Context getContext() {
        return getView().getContext();
    }

    public void unbind() {
    }
}
