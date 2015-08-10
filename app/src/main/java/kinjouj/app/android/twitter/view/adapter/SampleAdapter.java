package kinjouj.app.android.twitter.view.adapter;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import twitter4j.Status;
import twitter4j.User;

import kinjouj.app.android.twitter.MainActivity;
import kinjouj.app.android.twitter.R;
import kinjouj.app.android.twitter.fragment.StatusFragment;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    private SortedList<Status> statuses = new SortedList<>(Status.class, new SampleCallback());
    private Context context;
    private Picasso picasso;

    public SampleAdapter(Context context) {
        this.context = context;
        picasso = Picasso.with(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Status _status = statuses.get(i);

        if (_status.isRetweet()) {
            _status = _status.getRetweetedStatus();
        }

        final Status status = _status;

        viewHolder.content.setText(status.getText());
        viewHolder.mediaGrid.setAdapter(
            new MediaGridViewAdapter(context, status.getExtendedMediaEntities())
        );

        User user = status.getUser();
        picasso.load(user.getProfileBackgroundImageURL())
                .fit()
                .into(viewHolder.userBg);

        picasso.load(user.getProfileImageURL())
                .into(viewHolder.userIcon);

        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putSerializable(StatusFragment.EXTRA_STATUS, status);

                StatusFragment fragment = new StatusFragment();
                fragment.setArguments(extras);

                FragmentTransaction transaction = ((AppCompatActivity)context)
                                                    .getSupportFragmentManager()
                                                    .beginTransaction();

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
        this.statuses.beginBatchedUpdates();

        for (Status status : statuses) {
            add(status);
        }

        this.statuses.endBatchedUpdates();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View root;

        @Bind(R.id.user_bg_image)
        ImageView userBg;

        @Bind(R.id.user_icon_image)
        ImageView userIcon;

        @Bind(R.id.status_text)
        TextView content;

        @Bind(R.id.status_media_grid)
        GridView mediaGrid;

        public ViewHolder(View view) {
            super(view);
            root = view;
            ButterKnife.bind(this, view);
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
