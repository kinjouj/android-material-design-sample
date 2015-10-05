package kinjouj.app.oretter.view.manager;

import java.util.LinkedList;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.EventManager;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.RecyclerViewFragment;
import kinjouj.app.oretter.util.ThreadUtil;

public class TabLayoutManager extends ViewManager<TabLayout> implements TabLayout.OnTabSelectedListener {

    private static final String TAG = TabLayoutManager.class.getName();
    private static LinkedList<TabLayout.Tab> backStackTabs = new LinkedList<>();
    private boolean backStackState = false;

    public TabLayoutManager(View view) {
        super(view);
        getView().setOnTabSelectedListener(this);
    }

    public TabLayout.Tab addTab(String title, int iconRes, Fragment tagFragment) {
        return addTab(title, iconRes, tagFragment, false);
    }

    public TabLayout.Tab addTab(String title, int icon, Fragment fragment, boolean isSelected) {
        TabLayout.Tab tab = getView().newTab()
                                    .setText(title)
                                    .setIcon(icon)
                                    .setTag(fragment)
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

        ThreadUtil.run(new Runnable() {
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
        });
    }

    public TabLayout.Tab getCurrentTab() {
        return get(getView().getSelectedTabPosition());
    }

    public void addToBackStack(TabLayout.Tab tab) {
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
        final Fragment fragment = getTagFragment(tab.getTag());

        if (fragment == null) {
            return;
        }

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

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Fragment fragment = getTagFragment(tab.getTag());

        if (fragment != null && fragment instanceof RecyclerViewFragment) {
            ((RecyclerViewFragment) fragment).scrollToTop();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (!backStackState) {
            addToBackStack(tab);
        } else {
            backStackState = false;
        }
    }

    @Override
    public void unbind() {
        getView().setOnTabSelectedListener(null);
    }

    Fragment getTagFragment(Object obj) {
        return obj instanceof Fragment ? (Fragment) obj : null;
    }

    View createTabView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.tab, null);
        return view;
    }
}
