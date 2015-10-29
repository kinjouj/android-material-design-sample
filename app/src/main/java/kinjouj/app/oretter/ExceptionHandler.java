package kinjouj.app.oretter;

import android.util.Log;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = ExceptionHandler.class.getName();

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.e(TAG, String.format("thread(%s)", t.getName()), e);
        throw new RuntimeException(e);
    }
}
