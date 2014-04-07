package dk.androiddevelopment.birthdayreminder.birthdayreminderapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.Friend;
import register.CustomArrayAdapter;

public class AddEditFragment extends Fragment{
    Friend friend;
    CustomArrayAdapter adapter;
    EditText firstName;
    EditText lastName;
    EditText day;
    EditText month;
    EditText year;
    Button saveButton;
    Button cancelButton;
    Button editButton;
    Button datePicker;
    GregorianCalendar birthday;
    View view;
    Context context;
    ArrayList<Friend> friends;

    public AddEditFragment() {
    }

    public AddEditFragment(Friend friend, CustomArrayAdapter adapter) {
        this.friend = friend;
        this.adapter = adapter;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();

        if(friend == null) {
            friends = new ArrayList<Friend>();
            adapter = new CustomArrayAdapter(context, friends);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);

        setupVariables();

        if(friend != null) {
            firstName.setText(String.valueOf(friend.getFirstName()));
            lastName.setText(String.valueOf(friend.getLastName()));

            editButton = (Button) view.findViewById(R.id.editButton);

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edit();
                }
            });

            saveButton.setVisibility(view.GONE);
            editButton.setVisibility(view.VISIBLE);
        }

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
        birthday = new GregorianCalendar();

        saveButton = (Button) view.findViewById(R.id.saveButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);
        datePicker = (Button) view.findViewById(R.id.datePicker);

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
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void save() {

		if(adapter.add(firstName.getText().toString(), lastName.getText().toString(), birthday) == true){
			Toast.makeText(context, "The birthday is saved successfully!", Toast.LENGTH_SHORT).show();
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new ListViewFragment())
                    .commit();
		}else{
			Toast.makeText(context, "The birthday already exists!", Toast.LENGTH_SHORT).show();
		}

    }

    public void edit() {

        adapter.editFriend(friend.getFirstName(), friend.getLastName(), firstName.getText().toString(), lastName.getText().toString(), birthday);

        Toast.makeText(context, "The birthday is edited successfully!", Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ListViewFragment())
                .commit();
    }

    public void cancel() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new ListViewFragment())
                .commit();
    }

    private class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();

            if(friend == null) {
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Create a new instance of DatePickerDialog and return it
                return new DatePickerDialog(getActivity(), this, year, month, day);
            }

            int year = friend.getYear();
            int month = friend.getMonth();
            int day = friend.getDayOfMonth();

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            birthday.set(year, month, day);
        }

    }
}
