package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import butterknife.Bind;
import butterknife.BindString;

import kinjouj.app.oretter.ApplicationInterfaces;
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

    @BindString(R.string.nav_menu_my)
    String navMyTweetTitle;

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
        TabLayout.Tab tab = createTab(title, iconRes, tagFragment);
        addTab(tab, isSelected);

        return tab;
    }

    public TabLayout.Tab addTab(TabLayout.Tab tab, boolean isSelected) {
        tabLayout.addTab(tab, isSelected);
        return tab;
    }

    public TabLayout.Tab get(int position) {
        return tabLayout.getTabAt(position);
    }

    public TabLayout.Tab createTab(String title, int iconRes, Fragment tagFragment) {
        return tabLayout.newTab()
                        .setText(title)
                        .setIcon(iconRes)
                        .setCustomView(R.layout.tab)
                        .setTag(tagFragment);
    }

    public void select(TabLayout.Tab tab) {
        select(tab, 300);
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
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_TAG);

        if (fragment != null) {
            ((ApplicationInterfaces.ReloadableFragment)fragment).reload();
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
        //addTab(navMyTweetTitle, R.drawable.ic_home, R.id.tab_menu_my);
        addTab(navHomeTitle, R.drawable.ic_home, new HomeStatusListFragment(), true);
        addTab(navMentionTitle, R.drawable.ic_reply, new MentionListFragment());
        addTab(navFavoriteTitle, R.drawable.ic_grade, new FavoriteListFragment());
        addTab(navFollowTitle, R.drawable.ic_follow, new FollowListFragment());
        addTab(navFollowerTitle, R.drawable.ic_follower, new FollowerListFragment());
    }
}
