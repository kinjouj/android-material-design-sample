package kinjouj.app.oretter.fragments.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.squareup.picasso.Picasso;

import kinjouj.app.oretter.R;

public class PhotoPreviewDialogFragment extends DialogFragment {

    private static final String EXTRA_KEY_URL = "extra_key_url";

    @Bind(R.id.preview_photo)
    ImageView previewPhotoImageView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialog_photo_preview);
        dialog.getWindow().setLayout(WRAP_CONTENT, WRAP_CONTENT);
        ButterKnife.bind(this, dialog);
        Picasso.with(getActivity()).load(getUrl()).into(previewPhotoImageView);

        return dialog;
    }

    @OnClick(R.id.preview_photo)
    public void onClick(ImageView imageView) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }

    String getUrl() {
        Bundle args = getArguments();
        return args.getString(EXTRA_KEY_URL);
    }

    public static PhotoPreviewDialogFragment build(String url) {
        Bundle args = new Bundle();
        args.putString(EXTRA_KEY_URL, url);

        PhotoPreviewDialogFragment fragment = new PhotoPreviewDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
