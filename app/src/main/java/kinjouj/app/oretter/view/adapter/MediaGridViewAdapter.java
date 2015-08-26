package kinjouj.app.oretter.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import twitter4j.MediaEntity;

public class MediaGridViewAdapter extends BaseAdapter {

    private static final String TAG = MediaGridViewAdapter.class.getName();

    private Context context;
    private MediaEntity[] entities;

    public MediaGridViewAdapter(Context context, MediaEntity[] entities) {
        this.context  = context;
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
        ImageView imageView = null;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(130, 130));
        } else {
            imageView = (ImageView)convertView;
        }

        final MediaEntity entity = (MediaEntity)getItem(position);
        Picasso.with(context).load(entity.getMediaURL()).fit().into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(entity.getMediaURL());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

        return imageView;
    }
}
