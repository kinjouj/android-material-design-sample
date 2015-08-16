package kinjouj.app.oretter.view.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.oretter.R;
import kinjouj.app.oretter.SortedListAdapter;
import kinjouj.app.oretter.fragment.StatusFragment;
import kinjouj.app.oretter.view.UserIconImageView;

public class StatusListRecyclerViewAdapter extends RecyclerView.Adapter<StatusListRecyclerViewAdapter.ViewHolder> implements SortedListAdapter<Status> {

    private static final String TAG = StatusListRecyclerViewAdapter.class.getName();

    private SortedList<Status> statuses = new SortedList<>(Status.class, new SampleCallback());
    private Context context;
    private Picasso picasso;

    public StatusListRecyclerViewAdapter(Context context) {
        this.context = context;
        picasso = Picasso.with(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.status_list_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Status _status = statuses.get(i);
        final Status status = _status.isRetweet() ? _status.getRetweetedStatus() : _status;

        viewHolder.setContentText(status.getText());
        viewHolder.setMediaEntities(status.getExtendedMediaEntities());

        User user = status.getUser();

        viewHolder.icon.setUser(user);
        picasso.load(user.getProfileBackgroundImageURL())
                .fit()
                .into(viewHolder.bg);


        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusFragment fragment = StatusFragment.newInstance(status);

                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.content, fragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.commit();
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
        if (statuses == null || statuses.size() < 1) {
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

        public void setContentText(CharSequence text) {
            content.setText(text);
            Linkify.addLinks(content, Linkify.WEB_URLS);
        }

        public void setMediaEntities(MediaEntity[] entities) {
            mediaGrid.setAdapter(new MediaGridViewAdapter(root.getContext(), entities));
        }
    }

    private class SampleCallback extends SortedList.Callback<Status> {

        private final String TAG = SampleCallback.class.getName();

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
