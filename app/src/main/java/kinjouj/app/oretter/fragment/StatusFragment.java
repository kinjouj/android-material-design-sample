package kinjouj.app.oretter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.Status;
import twitter4j.MediaEntity;

import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.StatusFragment;
import kinjouj.app.oretter.view.adapter.MediaGridViewAdapter;

public class StatusFragment extends Fragment {

    private static final String TAG = StatusFragment.class.getName();
    public static final String EXTRA_STATUS = "extra_status";

    @Bind(R.id.detail_user_bg_image)
    ImageView userBgImage;

    @Bind(R.id.detail_user_image)
    ImageView userImage;

    @Bind(R.id.status_per_text)
    TextView statusText;

    @Bind(R.id.status_per_media_grid)
    GridView mediaGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.detail, container, false);
        ButterKnife.bind(this, view);
        bindView();

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    private void bindView() {
        Picasso pcs = Picasso.with(getActivity());
        Status status = getStatus();
        statusText.setText(status.getText());

        pcs.load(status.getUser().getProfileBackgroundImageURL())
            .fit()
            .into(userBgImage);

        pcs.load(status.getUser().getProfileImageURL())
            .into(userImage);

        mediaGrid.setAdapter(
            new MediaGridViewAdapter(
                getActivity(),
                status.getExtendedMediaEntities()
            )
        );
    }

    private Status getStatus() {
        return (Status)getArguments().getSerializable(StatusFragment.EXTRA_STATUS);
    }
}
