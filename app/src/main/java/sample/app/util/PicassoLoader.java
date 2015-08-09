package sample.app.util;

import android.content.Context;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class PicassoLoader {

    public static Picasso getPicasso(Context context) {
        Picasso pcs = Picasso.with(context);
        pcs.setLoggingEnabled(true);

        return pcs;
    }

    public static RequestCreator load(Context context, String url) {
        return getPicasso(context).load(url);
    }
}
