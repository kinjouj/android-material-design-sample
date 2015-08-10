package sample.app.view;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.User;

import sample.app.R;
import sample.app.util.TwitterApi;

public class DrawerHeaderView extends FrameLayout {

    @Bind(R.id.nav_user_bg)
    ImageView userBg;

    @Bind(R.id.nav_user_icon)
    ImageView userIcon;

    @Bind(R.id.nav_user_name)
    TextView userName;

    public DrawerHeaderView(Context context) {
        super(context);
        inflate(context, R.layout.navigation_header, this);
        ButterKnife.bind(this);
        init(context);
    }

    public void init(final Context context) {
        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {
                try {
                    final User user = TwitterApi.getCurrentUser((Activity)context);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Picasso pcs = Picasso.with(context);

                            userName.setText(user.getName());

                            pcs.load(user.getProfileBackgroundImageURL())
                                .fit()
                                .into(userBg);

                            pcs.load(user.getProfileImageURL()).into(userIcon);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
