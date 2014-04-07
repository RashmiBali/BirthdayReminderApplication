package dk.androiddevelopment.birthdayreminder.birthdayreminderapplication;



import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import model.Friend;


public class FriendProfileFragment extends Fragment {
    Friend friend;
    TextView firstName;
    TextView lastName;
    TextView birthday;
    View view;
    Context context;

    public FriendProfileFragment() {

    }

    public FriendProfileFragment(Friend friend) {
        this.friend = friend;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupVariables();
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friend_profile, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.friendprofilefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.home:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new ListViewFragment())
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupVariables() {
        firstName = (TextView) view.findViewById(R.id.firstNameView);
        lastName = (TextView) view.findViewById(R.id.lastNameView);
        birthday = (TextView) view.findViewById(R.id.birthdayView);

        firstName.setText(friend.getFirstName());
        lastName.setText(friend.getLastName());
        birthday.setText(friend.getDayOfMonth() + "/" + friend.getMonth() + "/" + friend.getYear());
    }
}
