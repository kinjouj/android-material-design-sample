package kinjouj.app.oretter.fragments;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import kinjouj.app.oretter.AppInterfaces;
import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.DrawerHeaderView;

public class NavigationViewFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = NavigationViewFragment.class.getName();

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(R.layout.fragment_navigation_view, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navigationView.addHeaderView(new DrawerHeaderView(getActivity()));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        ((MainActivity)getActivity()).getDrawerLayoutManager().close();
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
                ((MainActivity)getActivity()).getTabLayoutManager().clearBackStack();
                getActivity().finish();
                break;

            default:
                break;

        }

        return false;
    }

    private void navigateTab(int position) {
        ((MainActivity)getActivity()).getTabLayoutManager().get(position).select();
    }

    private void showListSpinnerFragment() {
        final Handler handler = new Handler();

        new Thread() {
            @Override
            public void run() {

                ListSpinnerDialogFragment fragment = ListSpinnerDialogFragment
                                                        .newInstance(getUserLists());
                fragment.show(getFragmentManager(), "list_spinner_dialog_fragment");
            }
        }.start();
    }

    private ResponseList<UserList> getUserLists() {
        ResponseList<UserList> userLists = null;

        try {
            Twitter twitter = TwitterFactory.getSingleton();
            User user = twitter.verifyCredentials();
            userLists = twitter.getUserLists(user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userLists;
    }
}
