package kinjouj.app.oretter.util;

import java.io.Serializable;

import android.os.Bundle;

public class BundleUtil {

    public static Bundle createSerializable(String key, Serializable obj) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(key, obj);

        return bundle;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T getSerializable(Bundle bundle, String key) {
        Object obj = null;

        if (bundle != null && bundle.containsKey(key)) {
            obj = bundle.getSerializable(key);
        }

        return (T) obj;
    }
}
