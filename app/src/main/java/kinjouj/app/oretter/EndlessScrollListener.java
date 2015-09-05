package kinjouj.app.oretter;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal    = 0;
    private int visibleThreshold = 0;
    private int currentPage      = 1;
    private boolean loading      = true;

    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemCount   = recyclerView.getLayoutManager().getItemCount();
        firstVisibleItem = findFirstVisibleItemPosition(recyclerView.getLayoutManager());

        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage);
            loading = true;
        }
    }

    private int findFirstVisibleItemPosition(RecyclerView.LayoutManager layoutManager)
        throws UnsupportedOperationException {

        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int[] into = new int[2];
            return ((StaggeredGridLayoutManager) layoutManager)
                            .findFirstVisibleItemPositions(into)[0];
        }

        throw new UnsupportedOperationException();
    }

    public abstract void onLoadMore(int current_page);

}
