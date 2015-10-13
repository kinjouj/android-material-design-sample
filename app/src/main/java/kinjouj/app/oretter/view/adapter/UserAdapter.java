package kinjouj.app.oretter.view.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.User;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.TweetTextView;
import kinjouj.app.oretter.view.UserIconImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
    implements AppInterfaces.SortedListAdapter<User> {

    private static final String TAG = UserAdapter.class.getName();

    private SortedList<User> users = new SortedList<>(User.class, new SortedListCallback());

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.list_item_user, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        User user = users.get(i);

        Picasso.with(viewHolder.getContext())
                .load(user.getProfileBackgroundImageURL())
                .fit()
                .into(viewHolder.userBg);

        Picasso.with(viewHolder.getContext())
                .load(user.getProfileImageURL())
                .fit()
                .into(viewHolder.userIcon);

        viewHolder.userIcon.setTag(user);
        viewHolder.content.linkify(user.getDescription());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void add(User user) {
        users.add(user);
    }

    public void addAll(List<User> users) {
        if (users.size() <= 0) {
            return;
        }

        this.users.beginBatchedUpdates();

        for (User user : users) {
            add(user);
        }

        this.users.endBatchedUpdates();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @Bind(R.id.user_bg_image)
        ImageView userBg;

        @Bind(R.id.user_icon_image)
        UserIconImageView userIcon;

        @Bind(R.id.user_text)
        TweetTextView content;

        public ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }

        public Context getContext() {
            return root.getContext();
        }
    }

    private class SortedListCallback extends SortedList.Callback<User> {

        private final String TAG = SortedListCallback.class.getName();

        @Override
        public boolean areItemsTheSame(User item1, User item2) {
            return item1.getId() == item2.getId();
        }

        @Override
        public boolean areContentsTheSame(User oldItem, User newItem) {
            return oldItem.getScreenName().equals(newItem.getScreenName());
        }

        @Override
        public int compare(User o1, User o2) {
            return -1;
        }

        @Override
        public void onInserted(int position, int count) {
            Log.v(TAG, "onInserted: " + position + " , " + count);
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            notifyItemRangeChanged(position, count);
        }
    }
}
