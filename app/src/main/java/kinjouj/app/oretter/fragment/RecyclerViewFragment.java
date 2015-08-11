package kinjouj.app.oretter.fragment;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.AppBarLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.Status;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.TwitterApi;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.adapter.SampleAdapter;

public abstract class RecyclerViewFragment extends Fragment
    implements SwipeRefreshLayout.OnRefreshListener,
               AppBarLayout.OnOffsetChangedListener {

    private static final String TAG = StatusListRecyclerViewFragment.class.getName();

    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    protected SampleAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.tweet_list, container, false);
        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(getLayoutManager());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Log.v(TAG, "onCreate");
        adapter = new SampleAdapter(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        ((MainActivity)getActivity()).addOnOffsetChangedListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(TAG, "onPause");
        ((MainActivity)getActivity()).removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBar, int verticalOffset) {
        swipeRefreshLayout.setEnabled(verticalOffset == 0);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity());
    }
}
