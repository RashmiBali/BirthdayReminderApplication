package dk.androiddevelopment.birthdayreminder.birthdayreminderapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.Friend;
import register.FriendSerializer;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * interface
 * to handle interaction events.
 *
 */
public class AddFragment extends Fragment{
    Friend friend;
    EditText firstName;
    EditText lastName;
    EditText day;
    EditText month;
    EditText year;
    Button saveButton;
    Button cancelButton;
    View view;
    Context context;
    ArrayList<Friend> friendsList;
    FriendSerializer fs;

    public AddFragment() {
        // Required empty public constructor
    }

    public AddFragment(Friend friend) {
        this.friend = friend;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        friendsList = new ArrayList<Friend>();
        fs = new FriendSerializer(context, friendsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);

        setupVariables();

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
        firstName = (EditText) view.findViewById(R.id.enterFirstName);
        lastName = (EditText) view.findViewById(R.id.enterLastName);
        day = (EditText) view.findViewById(R.id.enterDay);
        month = (EditText) view.findViewById(R.id.enterMonth);
        year = (EditText) view.findViewById(R.id.enterYear);

        day.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2)
        });

        month.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(2)
        });

        year.setFilters(new InputFilter[]{
                new InputFilter.LengthFilter(4)
        });

        saveButton = (Button) view.findViewById(R.id.saveButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void save() {
        String sDay = day.getText().toString();
        int iDay = Integer.parseInt(sDay);

        String sMonth = month.getText().toString();
        int iMonth = Integer.parseInt(sMonth);

        String sYear = year.getText().toString();
        int iYear = Integer.parseInt(sYear);

        GregorianCalendar birthday = new GregorianCalendar();
        birthday.set(iYear, iMonth, iDay);


		if(fs.add(firstName.getText().toString(), lastName.getText().toString(), birthday) == true){
			Toast.makeText(context, "The birthday is saved successfully!", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ListViewFragment())
                    .commit();
		}else{
			Toast.makeText(context, "The birthday already exists!", Toast.LENGTH_SHORT).show();
		}

    }

    public void edit() {

    }

    public void cancel() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ListViewFragment())
                .commit();
    }
}
