package kinjouj.app.oretter.view.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
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
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.util.TimeSpanConverter;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.util.ThreadUtil;
import kinjouj.app.oretter.view.TweetTextView;
import kinjouj.app.oretter.view.UserIconImageView;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.ViewHolder>
    implements AppInterfaces.SortedListAdapter<Status> {

    private static final String TAG = StatusAdapter.class.getName();

    private SortedList<Status> statuses = new SortedList<>(Status.class, new SortedListCallback());

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v(TAG, "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.list_item_status, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.v(TAG, "onBindViewHolder");
        final Context context = viewHolder.getContext();
        final Status status = getStatus(position);
        final User user = status.getUser();
        Picasso.with(context).load(user.getProfileImageURL()).fit().into(viewHolder.icon);
        viewHolder.icon.setTag(user);
        viewHolder.userName.setText(user.getName() + "\r\n@" + user.getScreenName());
        viewHolder.createdAt.setText(new TimeSpanConverter().toTimeSpanString(status.getCreatedAt()));
        viewHolder.content.linkify(getStatusText(status));
        viewHolder.mediaGrid.setAdapter(new GridViewAdapter(status.getExtendedMediaEntities()));

        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                TabLayoutManager tm = ((MainActivity) context).getTabLayoutManager();
                tm.select(
                    tm.addTab(
                        String.format("%s @%s", user.getName(), user.getScreenName()),
                        R.drawable.ic_person,
                        new StatusFragmentBuilder(status).build()
                    ),
                    300
                );
                */
            }
        });

        viewHolder.favoriteIconLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Status status = getStatus(position);

                if (status.isFavorited()) {
                    destroyFavorite(viewHolder, position);
                } else {
                    updateFavorite(viewHolder, position);
                }
            }
        });

        updateFavoriteIcon(status, viewHolder);
        updateRetweetIcon(status, viewHolder);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        Log.v(TAG, "onViewRecycled: " + holder);
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    @Override
    public void addAll(List<Status> statuses) {
        if (statuses.size() <= 0) {
            return;
        }

        this.statuses.beginBatchedUpdates();
        this.statuses.addAll(statuses);
        this.statuses.endBatchedUpdates();
    }

    @Override
    public void clear() {
        statuses.clear();
    }

    Status getStatus(int position) {
        Status status = statuses.get(position);
        return status.isRetweet() ? status.getRetweetedStatus() : status;
    }

    String getStatusText(Status status) {
        URLEntity[] entities = status.getURLEntities();
        String text = status.getText();

        for (URLEntity entity : entities) {
            text = text.replace(entity.getURL(), " " + entity.getExpandedURL() + " ");
        }

        return text;
    }

    void updateFavorite(final ViewHolder viewHolder, final int position) {
        final Status status = getStatus(position);
        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                Twitter twitter = TwitterFactory.getSingleton();

                try {
                    final Status createFavoriteStatus = twitter.createFavorite(status.getId());
                    ((Activity) viewHolder.getContext()).runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            updateFavoriteIcon(createFavoriteStatus, viewHolder);
                            statuses.updateItemAt(position, createFavoriteStatus);
                        }
                    });
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void destroyFavorite(final ViewHolder viewHolder, final int position) {
        final Status status = getStatus(position);
        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                Twitter twitter = TwitterFactory.getSingleton();

                try {
                    final Status destroyFavoriteStatus = twitter.destroyFavorite(status.getId());
                    ((Activity) viewHolder.getContext()).runOnUiThread(new Thread() {
                        @Override
                        public void run() {
                            updateFavoriteIcon(destroyFavoriteStatus, viewHolder);
                            statuses.updateItemAt(position, destroyFavoriteStatus);
                        }
                    });
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void updateFavoriteIcon(Status status, ViewHolder viewHolder) {
        int resId = status.isFavorited() ? R.drawable.ic_star_small_marked : R.drawable.ic_star_small;
        viewHolder.favoriteIcon.setImageResource(resId);
        viewHolder.favoriteIconText.setText(String.valueOf(status.getFavoriteCount()));
    }


    void updateRetweetIcon(Status status, ViewHolder viewHolder) {
        int resId = status.isRetweeted() ? R.drawable.ic_rt_small_marked : R.drawable.ic_rt_small;
        viewHolder.retweetIcon.setImageResource(resId);
        viewHolder.retweetIconText.setText(String.valueOf(status.getRetweetCount()));
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

        @Bind(R.id.status_favorite_icon_layout)
        View favoriteIconLayout;

        @Bind(R.id.status_favorite_icon)
        ImageView favoriteIcon;

        @Bind(R.id.status_favorite_icon_text)
        TextView favoriteIconText;

        public ViewHolder(View view) {
            super(view);
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
            //return oldItem.get().equals(newItem.getText());
            return areItemsTheSame(oldItem, newItem);
        }

        @Override
        public int compare(Status o1, Status o2) {
            int compare = Long.valueOf(o2.getId()).compareTo(o1.getId());
            return compare;
        }

        @Override
        public void onInserted(int position, int count) {
            Log.v(TAG, "onInserted: " + position + " , " + count);
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            Log.v(TAG, "onRemoved: " + position + " , " + count);
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            Log.v(TAG, "onMoved: " + fromPosition + " , " + toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count) {
            Log.v(TAG, "onChanged: " + position + " , " + count);
            notifyItemRangeChanged(position, count);
        }
    }
}
