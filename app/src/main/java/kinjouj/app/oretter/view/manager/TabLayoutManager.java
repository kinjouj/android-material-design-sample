package kinjouj.app.oretter.view.manager;

import java.util.List;
import java.util.LinkedList;

import android.app.Activity;
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

public class TabLayoutManager extends ViewManager<MainActivity> implements TabLayout.OnTabSelectedListener {

    private static final String TAG = TabLayoutManager.class.getName();
    private static LinkedList<TabLayout.Tab> backStackTabs = new LinkedList<TabLayout.Tab>();
    private boolean backStackState = false;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    public TabLayoutManager(Activity activity) {
        super(activity);
        tabLayout.setOnTabSelectedListener(this);
    }

    public TabLayout.Tab addTab(String title, int iconRes, Fragment tagFragment) {
        return addTab(title, iconRes, tagFragment, false);
    }

    public TabLayout.Tab addTab(String title, int iconRes, Fragment tagFragment, boolean isSelected) {
        TabLayout.Tab tab = tabLayout.newTab()
                                    .setText(title)
                                    .setIcon(iconRes)
                                    .setTag(tagFragment)
                                    .setCustomView(createTabView());

        return addTab(tab, isSelected);
    }

    public TabLayout.Tab addTab(TabLayout.Tab tab, boolean isSelected) {
        tabLayout.addTab(tab, isSelected);
        return tab;
    }

    public TabLayout.Tab get(int position) {
        return tabLayout.getTabAt(position);
    }

    public void select(final TabLayout.Tab tab, final int interval) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(interval);
                    getActivity().runOnUiThread(new Runnable() {
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
        return tabLayout.getSelectedTabPosition();
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment fragment = getTagFragment(tab.getTag());

        if (fragment != null) {
            MainActivity activity = getActivity();

            if (activity != null) {
                activity.getContentFragmentManager().render(fragment);
            }
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

    @Override
    public void unbind() {
        //tabLayout.removeAllTabs();
        super.unbind();
    }

    Fragment getTagFragment(Object o) {
        Fragment fragment = null;

        if (o instanceof Fragment) {
            fragment = (Fragment)o;
        }

        return fragment;
    }

    View createTabView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab, null);
        return view;
    }
}
