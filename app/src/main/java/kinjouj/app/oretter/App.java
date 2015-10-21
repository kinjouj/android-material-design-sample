package kinjouj.app.oretter;

import android.app.Application;
import android.util.Log;

public class App extends Application {

    private static final String TAG = App.class.getName();

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
    }
}
