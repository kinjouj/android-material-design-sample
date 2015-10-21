package kinjouj.app.oretter.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import twitter4j.MediaEntity;

import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.dialog.PhotoPreviewDialogFragment;
import kinjouj.app.oretter.util.FragmentUtil;

public class GridViewAdapter extends BaseAdapter {

    private static final String TAG = GridViewAdapter.class.getName();
    private MediaEntity[] entities;

    public GridViewAdapter(MediaEntity[] entities) {
        this.entities = entities;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return entities[position];
    }

    @Override
    public int getCount() {
        return entities.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final MediaEntity entity = (MediaEntity) getItem(position);
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Picasso.with(context).load(entity.getMediaURL()).resize(90, 90).into(holder.media);
        holder.media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviewImage(context, entity.getMediaURL());
            }
        });

        return view;
    }

    void showPreviewImage(Context context, String url) {
        FragmentUtil.showDialogFragment(context, PhotoPreviewDialogFragment.build(url));
    }

    public static class ViewHolder {

        ImageView media;

        public ViewHolder(View root) {
            media = (ImageView) root.findViewById(R.id.media_thumb_image_view);
        }

        public Context getContext() {
            return media.getContext();
        }
    }
}
