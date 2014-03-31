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
    EditText firstName;
    EditText lastName;
    EditText day;
    EditText month;
    EditText year;
    Button saveButton;
    Button cancelButton;
    View view;
    Context context;
    ArrayList<Friend> friends;
    FriendSerializer fs;

    public AddFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupVariables();
        context = getActivity();
        friends = new ArrayList<Friend>();
        fs = new FriendSerializer(context, friends);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add, container, false);

        saveButton = (Button) view.findViewById(R.id.saveButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialize();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serializeR();
            }
        });

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

    /**@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.saveButton:
                save();
                break;

            case R.id.cancelButton:
                cancel();
                break;
        }
    }**/

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

        /**saveButton = (Button) view.findViewById(R.id.saveButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);**/
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
			Toast.makeText(context, "The birtday is saved succesfully!", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context, "The birthday already exist!", Toast.LENGTH_SHORT).show();
		}

    }

    public void cancel() {
        fs.read();
        Toast.makeText(context, friends.get(0).getFirstName(), Toast.LENGTH_SHORT).show();
    }

    public void serialize() {
        ArrayList<Friend> f = new ArrayList<Friend>();
        Friend ff1 = new Friend();
        ff1.setFirstName("Tariq");
        ff1.setLastName("Amin");

        GregorianCalendar birthdayF1 = new GregorianCalendar();
        birthdayF1.set(1992, 03, 23);
        ff1.setBirthday(birthdayF1);

        /**Friend ff2 = new Friend();
        ff2.setFirstName("Muhammad");
        ff2.setLastName("Sarfraz");

        GregorianCalendar birthdayF2 = new GregorianCalendar();
        birthdayF1.set(1995, 07, 18);
        ff2.setBirthday(birthdayF2);

        Friend ff3 = new Friend();
        ff3.setFirstName("Muhammad");
        ff3.setLastName("Ali");

        GregorianCalendar birthdayF3 = new GregorianCalendar();
        birthdayF1.set(2000, 01, 01);
        ff3.setBirthday(birthdayF3);**/

        f.add(ff1);
       /** f.add(ff2);
        f.add(ff3);**/

        try{
            FileOutputStream fos= context.openFileOutput("testFile.ser", Context.MODE_PRIVATE);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(f);
            oos.close();
            fos.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void serializeR() {
        ArrayList<Friend> friendsRead = new ArrayList<Friend>();
        try
        {
            FileInputStream fis = context.openFileInput("testFile.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            friendsRead = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
        }catch(IOException ioe){
            ioe.printStackTrace();
            return;
        }catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
        for(Friend v: friendsRead){
            Toast.makeText(context, v.getFirstName(), Toast.LENGTH_SHORT).show();
        }
    }
}
