package kinjouj.app.oretter.fragments.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;
import com.hannesdorfmann.fragmentargs.FragmentArgs;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import twitter4j.ResponseList;
import twitter4j.UserList;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.UserListFragment;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class UserListDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    private static final String EXTRA_USER_LISTS = "extra_userlists";
    private int selectedIndex = -1;

    @Arg
    ResponseList<UserList> userLists;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentArgs.inject(this);

        if (userLists.size() <= 0) {
            setShowsDialog(false);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            getActivity(),
            android.R.layout.simple_list_item_single_choice
        );

        for (UserList userList : userLists) {
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
        TabLayoutManager tm = ((MainActivity) getActivity()).getTabLayoutManager();
        TabLayout.Tab tab = tm.addTab("リスト: " + userList.getName(), R.drawable.ic_list, fragment);
        tm.select(tab, 300);
    }
}
