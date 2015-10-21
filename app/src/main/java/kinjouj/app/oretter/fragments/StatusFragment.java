package kinjouj.app.oretter.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.Status;

import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.UserIconImageView;
import kinjouj.app.oretter.view.adapter.GridViewAdapter;
import twitter4j.User;

public class StatusFragment extends Fragment {

    private static final String TAG = StatusFragment.class.getName();
    private static final String EXTRA_KEY_STATUS = "extra_key_status";

    @Bind(R.id.detail_user_bg_image)
    ImageView userBgImage;

    @Bind(R.id.detail_user_image)
    UserIconImageView userImage;

    @Bind(R.id.status_per_text)
    TextView statusText;

    @Bind(R.id.status_per_media_grid)
    GridView mediaGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        bindView();
    }

    private void bindView() {
        Status status = getStatus();
        User user = status.getUser();
        userImage.setTag(user);
        statusText.setText(status.getText());
        mediaGrid.setAdapter(new GridViewAdapter(status.getExtendedMediaEntities()));
        Picasso.with(getActivity()).load(user.getProfileImageURL()).fit().into(userImage);
        Picasso.with(getActivity()).load(user.getProfileBackgroundImageURL()).fit().into(userBgImage);
    }

    Status getStatus() {
        Bundle args = getArguments();
        return (Status) args.getSerializable(EXTRA_KEY_STATUS);
    }

    public static StatusFragment build(Status status) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_KEY_STATUS, status);

        StatusFragment fragment = new StatusFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
