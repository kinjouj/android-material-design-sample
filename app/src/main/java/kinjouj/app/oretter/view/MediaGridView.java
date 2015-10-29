package kinjouj.app.oretter.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class MediaGridView extends GridView {

    public MediaGridView(Context context) {
        super(context);
    }

    public MediaGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MediaGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
            ViewCompat.MEASURED_SIZE_MASK, MeasureSpec
            .AT_MOST
        );
        super.onMeasure(widthMeasureSpec, expandSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
}
