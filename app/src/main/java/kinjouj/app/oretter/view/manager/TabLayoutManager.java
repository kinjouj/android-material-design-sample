package kinjouj.app.oretter.view.manager;

import java.util.List;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.Bind;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.RecyclerViewFragment;

public class TabLayoutManager extends ViewManager<TabLayout> implements TabLayout.OnTabSelectedListener {

    private static final String TAG = TabLayoutManager.class.getName();
    private static LinkedList<TabLayout.Tab> backStackTabs = new LinkedList<TabLayout.Tab>();
    private boolean backStackState = false;
    private AppInterfaces.FragmentRenderListener listener;

    public TabLayoutManager(View view, AppInterfaces.FragmentRenderListener listener) {
        super(view);
        this.listener = listener;
        getView().setOnTabSelectedListener(this);
    }

    public TabLayout.Tab addTab(String title, int iconRes, Fragment tagFragment) {
        return addTab(title, iconRes, tagFragment, false);
    }

    public TabLayout.Tab addTab(String title, int iconRes, Fragment tagFragment, boolean isSelected) {
        TabLayout.Tab tab = getView().newTab()
                                    .setText(title)
                                    .setIcon(iconRes)
                                    .setTag(tagFragment)
                                    .setCustomView(createTabView());

        return addTab(tab, isSelected);
    }

    public TabLayout.Tab addTab(TabLayout.Tab tab, boolean isSelected) {
        getView().addTab(tab, isSelected);
        return tab;
    }

    public TabLayout.Tab get(int position) {
        return getView().getTabAt(position);
    }

    public void select(final TabLayout.Tab tab, final int interval) {
        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(interval);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            tab.select();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public int getCurrentPosition() {
        return getView().getSelectedTabPosition();
    }

    public TabLayout.Tab getCurrentTab() {
        return get(getCurrentPosition());
    }

    public int getBackStackTabEntryCount() {
        return backStackTabs.size();
    }

    public void addToBackStackTab(TabLayout.Tab tab) {
        backStackTabs.add(tab);
    }

    public void popBackStackTab() {
        TabLayout.Tab tab = backStackTabs.removeLast();
        backStackState = true;
        select(tab, 300);
    }

    public void clearBackStack() {
        backStackTabs.clear();
    }

    public void clearTabs() {
        getView().removeAllTabs();
    }

    public void clear() {
        clearBackStack();
        clearTabs();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment fragment = getTagFragment(tab.getTag());

        if (fragment != null) {
            listener.render(fragment);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Fragment fragment = getTagFragment(tab.getTag());

        if (fragment != null && fragment instanceof RecyclerViewFragment) {
            RecyclerView recyclerView = ((RecyclerViewFragment) fragment).getRecyclerView();

            if (recyclerView.computeVerticalScrollOffset() > 0) {
                recyclerView.scrollToPosition(0);
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (!backStackState) {
            addToBackStackTab(tab);
        } else {
            backStackState = false;
        }
    }

    Fragment getTagFragment(Object o) {
        Fragment fragment = null;

        if (o instanceof Fragment) {
            fragment = (Fragment)o;
        }

        return fragment;
    }

    View createTabView() {
        View view = LayoutInflater.from(getView().getContext()).inflate(R.layout.tab, null);
        return view;
    }
}
