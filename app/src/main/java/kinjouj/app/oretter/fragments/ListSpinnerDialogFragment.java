package kinjouj.app.oretter.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import twitter4j.ResponseList;
import twitter4j.UserList;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class ListSpinnerDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String EXTRA_USER_LISTS = "extra_userlists";
    private List<UserList> userLists;
    private int selectedIndex = -1;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        if (args != null && args.containsKey(EXTRA_USER_LISTS)) {
            userLists = (ResponseList<UserList>)args.getSerializable(EXTRA_USER_LISTS);
        }

        int userListsSize = userLists != null ? userLists.size() : 0;

        if (userListsSize <= 0) {
            setShowsDialog(false);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_list_item_single_choice
        );

        for (UserList userList : userLists) {
            if (userList.getMemberCount() <= 0) {
                continue;
            }

            adapter.add(userList.getName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle("リスト")
            .setSingleChoiceItems(
                adapter,
                selectedIndex,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedIndex = which;
                    }
                }
            )
            .setPositiveButton("OK", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        UserList userList = userLists.get(selectedIndex);

        UserListFragment fragment = UserListFragment.newInstance(userList);
        String title = "リスト: " + userList.getName();
        TabLayoutManager tabManager = ((MainActivity)getActivity()).getTabLayoutManager();
        TabLayout.Tab tab = tabManager.addTab(title, R.drawable.ic_list, fragment);
        tabManager.select(tab, 300);
        System.out.println(selectedIndex);
    }

    public static ListSpinnerDialogFragment newInstance(ResponseList<UserList> userLists) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_USER_LISTS, userLists);

        ListSpinnerDialogFragment fragment = new ListSpinnerDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
