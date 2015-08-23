package kinjouj.app.oretter;

import android.content.Context;
import android.support.design.widget.TabLayout;

import kinjouj.app.oretter.fragment.FavoriteListFragment;
import kinjouj.app.oretter.fragment.FollowListFragment;
import kinjouj.app.oretter.fragment.FollowerListFragment;
import kinjouj.app.oretter.fragment.HomeStatusListFragment;
import kinjouj.app.oretter.fragment.MentionListFragment;
import kinjouj.app.oretter.fragment.SearchFragment;

public class TabLayoutManager implements TabLayout.OnTabSelectedListener {

    private Context context;
    private TabLayout tabLayout;

    public TabLayoutManager(Context context, TabLayout tabLayout) {
        this.context = context;
        this.tabLayout = tabLayout;
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Object tag = tab.getTag();
        int tagId = tag != null ? (int)tag : -1;

        switch (tagId) {
            case R.id.tab_menu_home:
                ((MainActivity)context).setContentFragment(
                    new HomeStatusListFragment()
                );
                break;

            case R.id.tab_menu_mention:
                ((MainActivity)context).setContentFragment(
                    new MentionListFragment()
                );
                break;

            case R.id.tab_menu_favorite:
                ((MainActivity)context).setContentFragment(
                    new FavoriteListFragment()
                );
                break;

            case R.id.tab_menu_follow:
                ((MainActivity)context).setContentFragment(
                    new FollowListFragment()
                );
                break;

            case R.id.tab_menu_follower:
                ((MainActivity)context).setContentFragment(
                    new FollowerListFragment()
                );
                break;

            default:
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        // noop
    }
}
