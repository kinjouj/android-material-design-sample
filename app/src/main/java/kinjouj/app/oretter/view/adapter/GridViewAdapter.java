package kinjouj.app.oretter.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import twitter4j.MediaEntity;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.EventManager;
import kinjouj.app.oretter.fragments.dialog.PhotoPreviewDialogFragment;
import kinjouj.app.oretter.fragments.dialog.PhotoPreviewDialogFragmentBuilder;

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
        MediaEntity entity = (MediaEntity) getItem(position);
        ImageView imageView = null;

        if (convertView == null) {
            imageView = new ImageView(parent.getContext());
            imageView.setLayoutParams(new GridView.LayoutParams(130, 130));
        } else {
            imageView = (ImageView) convertView;
        }

        final Context context = imageView.getContext();
        final String url = entity.getMediaURL();
        Picasso.with(context).load(url).fit().into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreviewImage(context, url);
            }
        });

        return imageView;
    }

    void showPreviewImage(Context context, String url) {
        PhotoPreviewDialogFragment fragment = new PhotoPreviewDialogFragmentBuilder(url).build();
        fragment.show(
            ((MainActivity) context).getSupportFragmentManager(),
            PhotoPreviewDialogFragment.class.getName()
        );
    }
}
