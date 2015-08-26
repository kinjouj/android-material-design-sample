package kinjouj.app.oretter.view.adapter;

import java.util.List;

import android.content.Context;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.TabLayout;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.StatusFragment;
import kinjouj.app.oretter.view.UserIconImageView;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class StatusRecyclerViewAdapter
    extends RecyclerView.Adapter<StatusRecyclerViewAdapter.ViewHolder>
    implements AppInterfaces.SortedListAdapter<Status> {

    private static final String TAG = StatusRecyclerViewAdapter.class.getName();

    private SortedList<Status> statuses = new SortedList<>(Status.class, new StatusSortedListCallback());
    private Context context;

    public StatusRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_status, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Status _status = statuses.get(i);
        final Status status = _status.isRetweet() ? _status.getRetweetedStatus() : _status;
        viewHolder.setContentText(status.getText());
        viewHolder.setMediaEntities(status.getExtendedMediaEntities());

        final User user = status.getUser();
        viewHolder.icon.setUser(user);
        viewHolder.setBackground(user.getProfileBackgroundImageURL());
        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = String.format("%s @%s", user.getName(), user.getScreenName());
                StatusFragment fragment = StatusFragment.newInstance(status);

                TabLayoutManager tm = ((MainActivity)context).getTabLayoutManager();
                TabLayout.Tab tab = tm.addTab(title, R.drawable.ic_person, fragment);
                tm.select(tab, 300);
            }
        });
    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    public void add(Status status) {
        statuses.add(status);
    }

    public void addAll(List<Status> statuses) {
        if (statuses == null || statuses.size() <= 0) {
            return;
        }

        this.statuses.beginBatchedUpdates();

        for (Status status : statuses) {
            add(status);
        }

        this.statuses.endBatchedUpdates();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @Bind(R.id.status_bg_image)
        ImageView bg;

        @Bind(R.id.status_icon_image)
        UserIconImageView icon;

        @Bind(R.id.status_text)
        TextView content;

        @Bind(R.id.status_media_grid)
        GridView mediaGrid;

        public ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
        }

        public void setBackground(String url) {
            Picasso.with(root.getContext()).load(url).fit().into(bg);
        }

        public void setContentText(String text) {
            content.setText(text);
            Linkify.addLinks(content, Linkify.WEB_URLS);
        }

        public void setMediaEntities(MediaEntity[] entities) {
            mediaGrid.setAdapter(new MediaGridViewAdapter(root.getContext(), entities));
        }
    }

    private class StatusSortedListCallback extends SortedList.Callback<Status> {

        private final String TAG = StatusSortedListCallback.class.getName();

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
