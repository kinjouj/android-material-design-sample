package kinjouj.app.oretter.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import twitter4j.User;

import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.UserStatusListRecyclerViewFragment;

public class UserIconImageView extends RoundedImageView implements View.OnClickListener {

    private static final String TAG = UserIconImageView.class.getName();

    private Context context;
    private User user;

    public UserIconImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
        this.context = context;
    }

    public void setUser(User user) {
        this.user = user;
        Picasso.with(context).load(user.getProfileImageURL()).into(this);
    }

    public void onClick(View view) {
        if (user == null) {
            return;
        }

        String tag = "fragment_user_list_" + user.getId();

        FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
        if(manager.findFragmentByTag(tag) != null) {
            Toast.makeText(view.getContext(), "already exists", Toast.LENGTH_LONG).show();
            return;
        }

        UserStatusListRecyclerViewFragment fragment = UserStatusListRecyclerViewFragment.newInstance(user.getId());

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.content, fragment, tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}
