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
import de.greenrobot.event.EventBus;
import twitter4j.MediaEntity;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.EventHandler;

public class GridViewAdapter extends BaseAdapter {

    private static final String TAG = GridViewAdapter.class.getName();

    private Context context;
    private MediaEntity[] entities;

    public GridViewAdapter(Context context, MediaEntity[] entities) {
        this.context  = context.getApplicationContext();
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
        MediaEntity entity = (MediaEntity)getItem(position);
        ImageView imageView = null;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(130, 130));
        } else {
            imageView = (ImageView)convertView;
        }

        final String url = entity.getMediaURL();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventHandler.post(
                    new EventHandler.AppEvent() {
                        @Override
                        public void run(Context context) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                );
            }
        });
        Picasso.with(context).load(url).fit().into(imageView);

        return imageView;
    }
}
