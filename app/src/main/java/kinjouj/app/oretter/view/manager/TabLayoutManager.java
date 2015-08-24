package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragment.FavoriteListFragment;
import kinjouj.app.oretter.fragment.FollowListFragment;
import kinjouj.app.oretter.fragment.FollowerListFragment;
import kinjouj.app.oretter.fragment.HomeStatusListFragment;
import kinjouj.app.oretter.fragment.MentionListFragment;
import kinjouj.app.oretter.fragment.SearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TabLayoutManager implements TabLayout.OnTabSelectedListener {

    Activity activity;

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;

    public TabLayoutManager(Activity activity) {
        this.activity = activity;
        ButterKnife.bind(this, activity);
        init();
    }

    private void init() {
        tabLayout.setOnTabSelectedListener(this);
    }

    public TabLayout.Tab addTab(TabLayout.Tab tab) {
        return addTab(tab, false);
    }

    public TabLayout.Tab addTab(TabLayout.Tab tab, boolean isSelected) {
        tabLayout.addTab(tab, isSelected);
        return null;
    }

    public TabLayout.Tab addTab(String title, int iconRes, int tagRes) {
        return addTab(title, iconRes, tagRes, false);
    }

    public TabLayout.Tab addTab(String title, int iconRes, int tagRes, boolean isSelected) {
        TabLayout.Tab tab = createTab(title, iconRes, tagRes);
        addTab(tab, isSelected);

        return tab;
    }

    public TabLayout.Tab get(int position) {
        return tabLayout.getTabAt(position);
    }

    public TabLayout.Tab createTab(String title, int iconRes, int tagRes) {
        return tabLayout.newTab().setText(title).setIcon(iconRes).setTag(tagRes);
    }

    public int getCurrentPosition() {
        return tabLayout.getSelectedTabPosition();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Object tag = tab.getTag();
        int tagId = tag != null ? (int)tag : -1;

        switch (tagId) {
            case R.id.tab_menu_home:
                ((MainActivity)activity).setContentFragment(new HomeStatusListFragment());
                break;

            case R.id.tab_menu_mention:
                ((MainActivity)activity).setContentFragment(new MentionListFragment());
                break;

            case R.id.tab_menu_favorite:
                ((MainActivity)activity).setContentFragment(new FavoriteListFragment());
                break;

            case R.id.tab_menu_follow:
                ((MainActivity)activity).setContentFragment(new FollowListFragment());
                break;

            case R.id.tab_menu_follower:
                ((MainActivity)activity).setContentFragment(new FollowerListFragment());
                break;

            default:
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Fragment fragment = ((MainActivity)activity).getSupportFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_TAG);
        System.out.println(fragment);
        ((AppInterfaces.ReloadableFragment)fragment).reload();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // noop
    }
}
