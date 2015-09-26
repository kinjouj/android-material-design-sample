package kinjouj.app.oretter.fragments.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import kinjouj.app.oretter.R;

public class ComposeDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialog_compose);
        dialog.getWindow().setLayout(MATCH_PARENT, MATCH_PARENT);

        return dialog;
    }

    public static void open(FragmentManager fm) {
        ComposeDialogFragment fragment = new ComposeDialogFragment();
        fragment.show(fm, ComposeDialogFragment.class.getSimpleName());
    }
}
