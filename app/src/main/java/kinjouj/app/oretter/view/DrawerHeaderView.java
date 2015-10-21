package kinjouj.app.oretter.view;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.User;

import kinjouj.app.oretter.R;

public class DrawerHeaderView extends FrameLayout {

    private static final String TAG = DrawerHeaderView.class.getName();

    @Bind(R.id.nav_user_bg)
    ImageView userBg;

    @Bind(R.id.nav_user_icon)
    ImageView userIcon;

    @Bind(R.id.nav_user_name)
    TextView userName;

    public DrawerHeaderView(Context context, User user) {
        super(context);
        inflate(context, R.layout.navigation_header, this);
        ButterKnife.bind(this);
        init(context, user);
    }

    void init(Context context, User user) {
        Log.v(TAG, "init");
        userName.setText(user.getName());
        Picasso.with(context).load(user.getProfileBackgroundImageURL()).fit().into(userBg);
        Picasso.with(context).load(user.getProfileImageURL()).into(userIcon);
    }

    @Override
    public void onDetachedFromWindow() {
        Log.v(TAG, "onDetachedFromWindow");
        ButterKnife.unbind(this);
        super.onDetachedFromWindow();
    }
}
