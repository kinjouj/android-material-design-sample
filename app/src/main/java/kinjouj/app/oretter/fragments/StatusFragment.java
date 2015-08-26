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

public class StatusFragment extends Fragment {

    private static final String TAG = StatusFragment.class.getName();
    public static final String EXTRA_STATUS = "extra_status";

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindView();
    }

    private void bindView() {
        Status status = getStatus();
        statusText.setText(status.getText());
        userImage.setUser(status.getUser());
        mediaGrid.setAdapter(new GridViewAdapter(getActivity(), status.getExtendedMediaEntities()));
        Picasso.with(getActivity())
                .load(status.getUser()
                .getProfileBackgroundImageURL())
                .fit()
                .into(userBgImage);
    }

    private Status getStatus() {
        return (Status)getArguments().getSerializable(StatusFragment.EXTRA_STATUS);
    }

    public static StatusFragment newInstance(Status status) {
        Bundle extras = new Bundle();
        extras.putSerializable(EXTRA_STATUS, status);

        StatusFragment fragment = new StatusFragment();
        fragment.setArguments(extras);

        return fragment;
    }
}
