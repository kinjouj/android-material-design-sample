package kinjouj.app.oretter.view.manager;

import java.util.LinkedList;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.EventManager;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.RecyclerViewFragment;

public class TabLayoutManager extends ViewManager<TabLayout> implements TabLayout.OnTabSelectedListener {

    private static final String TAG = TabLayoutManager.class.getName();
    static LinkedList<TabLayout.Tab> backStackTabs = new LinkedList<>();
    boolean backStackState = false;

    public TabLayoutManager(View view) {
        super(view);
        getView().setOnTabSelectedListener(this);
    }

    public TabLayout.Tab addTab(String title, int iconRes, Fragment tagFragment) {
        return addTab(title, iconRes, tagFragment, false);
    }

    public TabLayout.Tab addTab(String title, int icon, Fragment fragment, boolean isSelected) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab, null);
        TabLayout.Tab tab = getView().newTab()
                                    .setText(title)
                                    .setIcon(icon)
                                    .setTag(fragment)
                                    .setCustomView(view);

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
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                tab.select();
            }
        }, interval);
    }

    void addBackStack(TabLayout.Tab tab) {
        if (backStackState) {
            backStackState = false;
            return;
        }

        if (tab == null) {
            throw new IllegalArgumentException("tab is null");
        }

        backStackTabs.add(tab);
    }

    public boolean hasBackStack() {
        return backStackTabs.size() > 0;
    }

    public void popBackStack() {
        TabLayout.Tab tab = backStackTabs.removeLast();
        backStackState = true;
        select(tab, 300);
    }

    public void clear() {
        backStackTabs.clear();
        getView().removeAllTabs();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.v(TAG, "onTabSelected");

        if (tab.getTag() instanceof Fragment) {
            Fragment fragment = (Fragment) tab.getTag();
            renderFragment(fragment);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.v(TAG, "onTabReselected");

        if (tab.getTag() instanceof Fragment) {
            Fragment fragment = (Fragment) tab.getTag();

            if (fragment instanceof RecyclerViewFragment) {
                ((RecyclerViewFragment) fragment).scrollToTop();
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.v(TAG, "onTabUnselected");
        addBackStack(tab);
    }

    @Override
    public void unbind() {
        getView().setOnTabSelectedListener(null);
    }

    void renderFragment(final Fragment fragment) {
        EventManager.getInstance().post(new AppInterfaces.AppEvent() {
            @Override
            public void run(Context context) {
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.disallowAddToBackStack();
                tx.replace(R.id.content, fragment, "current_content_fragment");
                tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                tx.commit();
            }
        });
    }
}
