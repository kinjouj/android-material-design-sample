package kinjouj.app.oretter.view.manager;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import butterknife.Bind;

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

    public TabLayoutManager(Activity activity) {
        super(activity);
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
        int tagId = -1;

        if (tag != null) {
            System.out.println(tag instanceof Integer);
            tagId = (int)tag;
        }

        switch (tagId) {
            case R.id.tab_menu_home:
                getActivity().setContentFragment(new HomeStatusListFragment());
                break;

            case R.id.tab_menu_mention:
                getActivity().setContentFragment(new MentionListFragment());
                break;

            case R.id.tab_menu_favorite:
                getActivity().setContentFragment(new FavoriteListFragment());
                break;

            case R.id.tab_menu_follow:
                getActivity().setContentFragment(new FollowListFragment());
                break;

            case R.id.tab_menu_follower:
                getActivity().setContentFragment(new FollowerListFragment());
                break;

            default:
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(MainActivity.FRAGMENT_TAG);
        ((AppInterfaces.ReloadableFragment)fragment).reload();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // noop
    }
}
