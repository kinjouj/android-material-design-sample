package kinjouj.app.oretter.view.manager;

import android.content.Context;
import android.view.View;

public abstract class ViewManager<T extends View> {

    private View view;

    public ViewManager(View view) {
        this.view = view;
    }

    @SuppressWarnings("unchecked")
    protected T getView() {
        return (T) view;
    }

    protected Context getContext() {
        return getView().getContext();
    }

    protected void destroyView() {
        view = null;
    }

    public void unbind() {
    }
}
