package sample.app.view.adapter;

import java.io.IOException;

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

import sample.app.MainActivity;
import sample.app.R;
import sample.app.fragment.StatusFragment;
import sample.app.util.PicassoLoader;

public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    SortedList<Status> statuses = new SortedList<>(Status.class, new SampleCallback());

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
            new MediaGridViewAdapter(
                viewHolder.getContext(),
                status.getExtendedMediaEntities()
            )
        );

        User user = status.getUser();
        Picasso pcs = PicassoLoader.getPicasso(viewHolder.getContext());
        pcs.load(user.getProfileBackgroundImageURL()).fit().into(viewHolder.userBg);
        pcs.load(user.getProfileImageURL()).into(viewHolder.userIcon);

        viewHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();

                Bundle extras = new Bundle();
                extras.putSerializable(StatusFragment.EXTRA_STATUS, status);

                StatusFragment fragment = new StatusFragment();
                fragment.setArguments(extras);

                FragmentTransaction transaction = activity
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

        public Context getContext() {
            return root.getContext();
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
            notifyItemRangeInserted(position, count);
            Log.v(TAG, "count: " + count);
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
