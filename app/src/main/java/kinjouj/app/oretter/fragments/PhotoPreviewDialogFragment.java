package kinjouj.app.oretter.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.squareup.picasso.Picasso;

import kinjouj.app.oretter.R;

public class PhotoPreviewDialogFragment extends DialogFragment {

    @Bind(R.id.preview_photo)
    ImageView previewPhotoImageView;

    @Arg
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialog_photo_preview);
        dialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);

        ButterKnife.bind(this, dialog);
        Picasso.with(getActivity()).load(url).into(previewPhotoImageView);

        return dialog;
    }

    @OnClick(R.id.preview_photo)
    public void onClick(ImageView imageView) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}
