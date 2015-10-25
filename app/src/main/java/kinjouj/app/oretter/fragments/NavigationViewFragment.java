package kinjouj.app.oretter.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import butterknife.Bind;
import butterknife.ButterKnife;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.dialog.UserListDialogFragment;
import kinjouj.app.oretter.util.ThreadUtil;
import kinjouj.app.oretter.view.DrawerHeaderView;

public class NavigationViewFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = NavigationViewFragment.class.getName();
    private static Twitter twitter = TwitterFactory.getSingleton();
    private User user;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_navigation_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        navigationView.setNavigationItemSelectedListener(null);
        ButterKnife.unbind(this);
        user = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navigationView.setNavigationItemSelectedListener(this);
        final Handler handler = new Handler();
        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                try {
                    user = twitter.verifyCredentials();
                    handler.post(new Runnable() {
                         @Override
                         public void run() {
                            navigationView.addHeaderView(new DrawerHeaderView(getActivity(), user));
                         }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_menu_home:
                navigateTab(0);
                break;

            case R.id.nav_menu_mention:
                navigateTab(1);
                break;

            case R.id.nav_menu_favorite:
                navigateTab(2);
                break;

            case R.id.nav_menu_follow:
                navigateTab(3);
                break;

            case R.id.nav_menu_follower:
                navigateTab(4);
                break;

            case R.id.nav_menu_list:
                showListSpinnerFragment();
                break;

            case R.id.nav_menu_exit:
                MainActivity activity = (MainActivity) getActivity();
                activity.getTabLayoutManager().clear();
                activity.finish();
                break;

            default:
                break;

        }

        ((MainActivity) getActivity()).getDrawerLayoutManager().close();

        return false;
    }

    void navigateTab(int position) {
        ((MainActivity) getActivity()).getTabLayoutManager().get(position).select();
    }

    void showListSpinnerFragment() {
        ThreadUtil.run(new Runnable() {
            @Override
            public void run() {
                UserListDialogFragment fragment = UserListDialogFragment.build(getUserLists());
                fragment.show(getFragmentManager(), UserListDialogFragment.class.getName());
            }
        });
    }

    ResponseList<UserList> getUserLists() {
        ResponseList<UserList> userLists = null;

        try {
            if (user == null) {
                user = twitter.verifyCredentials();
            }

            userLists = twitter.getUserLists(user.getId());

            for (int i = 0; i < userLists.size(); i++) {
                UserList userList = userLists.get(i);

                if (userList.getMemberCount() > 0) {
                    continue;
                }

                userLists.remove(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            userLists = null;
        }

        return userLists;
    }
}
