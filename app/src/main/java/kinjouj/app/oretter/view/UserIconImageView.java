package kinjouj.app.oretter.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import twitter4j.User;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.UserFragment;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class UserIconImageView extends RoundedImageView implements View.OnClickListener {

    private static final String TAG = UserIconImageView.class.getName();

    private Context context;
    private User user;

    public UserIconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void setUser(User user) {
        this.user = user;
        setOnClickListener(this);
        Picasso.with(context).load(user.getProfileImageURL()).fit().into(this);
    }

    public void onClick(View view) {
        if (user == null) {
            return;
        }

        String title = String.format("%s @%s", user.getName(), user.getScreenName());
        UserFragment fragment = UserFragment.newInstance(user);
        TabLayoutManager tabManager = ((MainActivity) context).getTabLayoutManager();
        TabLayout.Tab tab = tabManager.addTab(title, R.drawable.ic_person, fragment);
        tabManager.select(tab, 300);
    }
}
