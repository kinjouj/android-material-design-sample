package kinjouj.app.oretter;

import android.util.Log;

public class Logger {

    private static final String TAG = Logger.class.getName();

    public static void v(String message) {
        Log.v(TAG, message);
    }
}
