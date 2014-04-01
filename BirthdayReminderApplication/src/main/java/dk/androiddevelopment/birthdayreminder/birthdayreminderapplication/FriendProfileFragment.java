package dk.androiddevelopment.birthdayreminder.birthdayreminderapplication;



import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.Friend;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the FriendProfileFragment factory method to
 * create an instance of this fragment.
 *
 */
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

       /** ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);**/

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

    public void setupVariables() {
        firstName = (TextView) view.findViewById(R.id.firstNameView);
        lastName = (TextView) view.findViewById(R.id.lastNameView);
        birthday = (TextView) view.findViewById(R.id.birthdayView);

        firstName.setText(friend.getFirstName());
        lastName.setText(friend.getLastName());
        birthday.setText(friend.getDayOfMonth() + "/" + friend.getMonth() + "/" + friend.getYear());
    }
}
