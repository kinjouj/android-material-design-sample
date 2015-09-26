package kinjouj.app.oretter.util;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class LayoutManagerUtil {

    public static int STAGGERED_GRID_NUM_COLUMNS = 2;

    public static int findFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager) {
        int pos = 0;

        if (layoutManager instanceof LinearLayoutManager) {
            pos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager lm = (StaggeredGridLayoutManager) layoutManager;
            int[] into = new int[lm.getSpanCount()];
            pos = lm.findFirstVisibleItemPositions(into)[0];
        }

        return pos;
    }

    public static RecyclerView.LayoutManager getOrientationLayoutManager(Context context) {
        Configuration config = context.getResources().getConfiguration();

        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return new StaggeredGridLayoutManager(
                STAGGERED_GRID_NUM_COLUMNS,
                StaggeredGridLayoutManager.VERTICAL
            );
        } else {
            return new LinearLayoutManager(context);
        }
    }
}
