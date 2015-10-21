package kinjouj.app.oretter.util;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

public class FragmentUtil {

    public static void showDialogFragment(Context context, DialogFragment fragment) {
        if (!(context instanceof AppCompatActivity)) {
            throw new IllegalArgumentException("context isn`t AppCompatActivity");
        }

        fragment.show(
            ((AppCompatActivity) context).getSupportFragmentManager(),
            fragment.getClass().getName()
        );
    }
}
