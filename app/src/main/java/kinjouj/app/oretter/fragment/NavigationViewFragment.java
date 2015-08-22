package kinjouj.app.oretter.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        Log.v(TAG, "onActivityCreated");
        navigationView.addHeaderView(new DrawerHeaderView(getActivity()));
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_menu_home:
                ((AppInterfaces.NavigateTabListener)getActivity()).navigateTab(1);
                break;

            case R.id.nav_menu_mention:
                ((AppInterfaces.NavigateTabListener)getActivity()).navigateTab(2);
                break;

            case R.id.nav_menu_favorite:
                ((AppInterfaces.NavigateTabListener)getActivity()).navigateTab(3);
                break;

            case R.id.nav_menu_follow:
                ((AppInterfaces.NavigateTabListener)getActivity()).navigateTab(4);
                break;

            case R.id.nav_menu_follower:
                ((AppInterfaces.NavigateTabListener)getActivity()).navigateTab(5);
                break;

            default:
                break;

        }

        ((MainActivity)getActivity()).closeDrawer();

        return false;
    }
}
