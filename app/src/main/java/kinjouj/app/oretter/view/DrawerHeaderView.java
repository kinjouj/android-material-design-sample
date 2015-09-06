package kinjouj.app.oretter.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.TwitterFactory;
import twitter4j.User;

import kinjouj.app.oretter.R;

public class DrawerHeaderView extends FrameLayout {

    @Bind(R.id.nav_user_bg)
    ImageView userBg;

    @Bind(R.id.nav_user_icon)
    ImageView userIcon;

    @Bind(R.id.nav_user_name)
    TextView userName;

    private User user;

    public DrawerHeaderView(Context context, User user) {
        super(context);
        inflate(context, R.layout.navigation_header, this);
        ButterKnife.bind(this);
        this.user = user;
        init(context);
    }

    public void init(final Context context) {
        userName.setText(user.getName());
        Picasso.with(context).load(user.getProfileBackgroundImageURL()).fit().into(userBg);
        Picasso.with(context).load(user.getProfileImageURL()).into(userIcon);
    }
}
