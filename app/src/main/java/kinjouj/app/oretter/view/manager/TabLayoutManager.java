package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.BindString;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.FavoriteListFragment;
import kinjouj.app.oretter.fragment.FollowListFragment;
import kinjouj.app.oretter.fragment.FollowerListFragment;
import kinjouj.app.oretter.fragment.HomeStatusListFragment;
import kinjouj.app.oretter.fragment.MentionListFragment;
import kinjouj.app.oretter.fragment.SearchFragment;

public class TabLayoutManager extends ViewManager<MainActivity> implements TabLayout.OnTabSelectedListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    @BindString(R.string.nav_menu_home)
    String navHomeTitle;

    @BindString(R.string.nav_menu_mention)
    String navMentionTitle;

    @BindString(R.string.nav_menu_favorite)
    String navFavoriteTitle;

    @BindString(R.string.nav_menu_follow)
    String navFollowTitle;

    @BindString(R.string.nav_menu_follower)
    String navFollowerTitle;

    public TabLayoutManager(Activity activity) {
        super(activity);
        init();
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Fragment fragment = getTagFragment(tab.getTag());

        if (fragment != null) {
            getActivity().setContentFragment(fragment);
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_TAG);

        if (fragment != null && fragment instanceof AppInterfaces.ReloadableFragment) {
            ((AppInterfaces.ReloadableFragment)fragment).reload();
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // noop
    }

    Fragment getTagFragment(Object o) {
        Fragment fragment = null;

        if (o instanceof Fragment) {
            fragment = (Fragment)o;
        }

        return fragment;
    }

    void init() {
        tabLayout.setOnTabSelectedListener(this);
        addTab(navHomeTitle, R.drawable.ic_home, new HomeStatusListFragment(), true);
        addTab(navMentionTitle, R.drawable.ic_reply, new MentionListFragment());
        addTab(navFavoriteTitle, R.drawable.ic_grade, new FavoriteListFragment());
        addTab(navFollowTitle, R.drawable.ic_follow, new FollowListFragment());
        addTab(navFollowerTitle, R.drawable.ic_follower, new FollowerListFragment());
    }

    View createTabView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab, null);

        return view;
    }
}
