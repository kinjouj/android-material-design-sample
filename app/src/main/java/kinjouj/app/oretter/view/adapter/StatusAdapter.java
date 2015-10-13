package kinjouj.app.oretter.view.adapter;

import java.util.List;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.User;
import twitter4j.util.TimeSpanConverter;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.StatusFragmentBuilder;
import kinjouj.app.oretter.view.TweetTextView;
import kinjouj.app.oretter.view.UserIconImageView;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>
    implements AppInterfaces.SortedListAdapter<Status> {

    private static final String TAG = StatusAdapter.class.getName();

    private SortedList<Status> statuses = new SortedList<>(Status.class, new SortedListCallback());

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.list_item_status, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Context context = viewHolder.getContext();
        final Status status = getStatus(position);
        final User user = status.getUser();

        if (status.isFavorited()) {
            viewHolder.favoriteIcon.setImageResource(R.drawable.ic_star_small_marked);
        } else {
            viewHolder.favoriteIcon.setImageResource(R.drawable.ic_star_small);
        }

        if (status.isRetweeted()) {
            viewHolder.retweetIcon.setImageResource(R.drawable.ic_rt_small_marked);
        } else {
            viewHolder.retweetIcon.setImageResource(R.drawable.ic_rt_small);
        }

        Picasso.with(context).load(user.getProfileImageURL()).fit().into(viewHolder.icon);
        viewHolder.icon.setTag(user);
        viewHolder.userName.setText(user.getName() + "\r\n@" + user.getScreenName());
        viewHolder.createdAt.setText(new TimeSpanConverter().toTimeSpanString(status.getCreatedAt()));
        viewHolder.content.linkify(status.getText());
        viewHolder.mediaGrid.setAdapter(new GridViewAdapter(status.getExtendedMediaEntities()));
        viewHolder.favoriteIconText.setText(String.valueOf(status.getFavoriteCount()));
        viewHolder.retweetIconText.setText(String.valueOf(status.getRetweetCount()));

        /*
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TabLayoutManager tm = ((MainActivity) context).getTabLayoutManager();
                tm.select(
                    tm.addTab(
                        String.format("%s @%s", user.getName(), user.getScreenName()),
                        R.drawable.ic_person,
                        new StatusFragmentBuilder(status).build()
                    ),
                    300
                );
            }
        });
        */
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        Log.v(TAG, "onViewRecycled: " + holder);
        super.onViewRecycled(holder);
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        Log.v(TAG, "onViewDetachedFromWindow: " + holder);
        super.onViewDetachedFromWindow(holder);
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public void add(Status status) {
        statuses.add(status);
    }

    public void addAll(List<Status> statuses) {
        if (statuses.size() <= 0) {
            return;
        }

        this.statuses.beginBatchedUpdates();

        for (Status status : statuses) {
            add(status);
        }

        this.statuses.endBatchedUpdates();
    }

    Status getStatus(int position) {
        Status status = statuses.get(position);
        return status.isRetweet() ? status.getRetweetedStatus() : status;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @Bind(R.id.status_icon_image)
        UserIconImageView icon;

        @Bind(R.id.status_user_name)
        TextView userName;

        @Bind(R.id.status_created_at)
        TextView createdAt;

        @Bind(R.id.status_text)
        TweetTextView content;

        @Bind(R.id.status_media_grid)
        GridView mediaGrid;

        @Bind(R.id.status_retweet_icon)
        ImageView retweetIcon;

        @Bind(R.id.status_retweet_icon_text)
        TextView retweetIconText;

        @Bind(R.id.status_favorite_icon)
        ImageView favoriteIcon;

        @Bind(R.id.status_favorite_icon_text)
        TextView favoriteIconText;

        public ViewHolder(View view) {
            super(view);
            Log.v(TAG, "ViewHolder: " + view);
            root = view;
            ButterKnife.bind(this, view);
        }

        public Context getContext() {
            return root.getContext();
        }
    }

    private class SortedListCallback extends SortedList.Callback<Status> {

        private final String TAG = SortedListCallback.class.getName();

        @Override
        public boolean areItemsTheSame(Status item1, Status item2) {
            return item1.getId() == item2.getId();
        }

        @Override
        public boolean areContentsTheSame(Status oldItem, Status newItem) {
            return oldItem.getText().equals(newItem.getText());
        }

        @Override
        public int compare(Status o1, Status o2) {
            Log.v(TAG, "compare: "  + o1 + " = " + o2);
            return Long.valueOf(o2.getId()).compareTo(o1.getId());
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
