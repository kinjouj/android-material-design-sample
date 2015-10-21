package kinjouj.app.oretter.fragments.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;

public class LoadingDialogFragment extends DialogFragment {

    private static final String TAG = LoadingDialogFragment.class.getName();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v(TAG, "onCreateDialog");

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Now Loading");
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }

                return false;
            }
        });

        return dialog;
    }

}
