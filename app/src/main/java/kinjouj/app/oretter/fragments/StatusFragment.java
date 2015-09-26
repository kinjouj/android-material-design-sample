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
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.squareup.picasso.Picasso;
import twitter4j.Status;

import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.UserIconImageView;
import kinjouj.app.oretter.view.adapter.GridViewAdapter;

public class StatusFragment extends Fragment {

    private static final String TAG = StatusFragment.class.getName();

    @Bind(R.id.detail_user_bg_image)
    ImageView userBgImage;

    @Bind(R.id.detail_user_image)
    UserIconImageView userImage;

    @Bind(R.id.status_per_text)
    TextView statusText;

    @Bind(R.id.status_per_media_grid)
    GridView mediaGrid;

    @Arg
    Status status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);
    }

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
        statusText.setText(status.getText());
        userImage.setTag(status.getUser());
        mediaGrid.setAdapter(new GridViewAdapter(status.getExtendedMediaEntities()));

        Picasso.with(getActivity())
                .load(status.getUser().getProfileImageURL())
                .fit()
                .into(userImage);

        Picasso.with(getActivity())
                .load(status.getUser().getProfileBackgroundImageURL())
                .fit()
                .into(userBgImage);
    }
}
