package kinjouj.app.oretter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.makeramen.roundedimageview.RoundedImageView;
import twitter4j.User;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.list.status.UserFragment;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class UserIconImageView extends RoundedImageView implements View.OnClickListener {

    private static final String TAG = UserIconImageView.class.getName();

    public UserIconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Log.v(TAG, "onClick");
        User user = (User) getTag();

        if (user == null) {
            return;
        }

        TabLayoutManager tm = ((MainActivity) getContext()).getTabLayoutManager();
        tm.select(
            tm.addTab(
                String.format("%s @%s", user.getName(), user.getScreenName()),
                R.drawable.ic_person,
                UserFragment.build(user)
            ),
            300
        );
    }
}
