package kinjouj.app.oretter.fragments.dialog;

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
import kinjouj.app.oretter.fragments.UserListFragment;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class UserListDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String EXTRA_KEY_USERLISTS = "extra_key_userlists";
    int selectedIndex = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<UserList> userLists = getUserLists();

        if (userLists == null || userLists.size() <= 0) {
            setShowsDialog(false);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(
            getActivity(),
            android.R.layout.simple_list_item_single_choice
        );

        for (UserList userList : getUserLists()) {
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
        if (selectedIndex < 0) {
            return;
        }

        UserList userList = getUserLists().get(selectedIndex);
        TabLayoutManager tm = ((MainActivity) getActivity()).getTabLayoutManager();
        TabLayout.Tab tab = tm.addTab(
            "リスト: " + userList.getName(),
            R.drawable.ic_list,
            UserListFragment.build(userList)
        );
        tm.select(tab, 300);
    }

    @SuppressWarnings("unchecked")
    ResponseList<UserList> getUserLists() {
        Bundle args = getArguments();
        return (ResponseList<UserList>) args.getSerializable(EXTRA_KEY_USERLISTS);
    }

    public static UserListDialogFragment build(ResponseList<UserList> userLists) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_KEY_USERLISTS, userLists);

        UserListDialogFragment fragment = new UserListDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
