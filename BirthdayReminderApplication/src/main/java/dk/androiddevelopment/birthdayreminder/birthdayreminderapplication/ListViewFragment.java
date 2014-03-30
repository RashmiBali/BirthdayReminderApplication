package dk.androiddevelopment.birthdayreminder.birthdayreminderapplication;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;

import model.Friend;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 *
 */
public class ListViewFragment extends ListFragment {

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Friend> friends = new ArrayList<Friend>();
        Friend f1 = new Friend();
        f1.setFirstName("Shahroz");
        f1.setLastName("Ali");

        GregorianCalendar birthdayF1 = new GregorianCalendar();
        birthdayF1.set(1992, 03, 23);
        f1.setBirthday(birthdayF1);

        Friend f2 = new Friend();
        f2.setFirstName("Ali");
        f2.setLastName("Mahroz");

        GregorianCalendar birthdayF2 = new GregorianCalendar();
        birthdayF1.set(1995, 07, 18);
        f2.setBirthday(birthdayF2);

        Friend f3 = new Friend();
        f3.setFirstName("Sharyaar");
        f3.setLastName("Azmat");

        GregorianCalendar birthdayF3 = new GregorianCalendar();
        birthdayF1.set(2000, 01, 01);
        f3.setBirthday(birthdayF3);

        friends.add(f1);
        friends.add(f2);
        friends.add(f3);

        setListAdapter(new CustomArrayAdapter(getActivity(), friends));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * A simple array adapter that creates a list of cheeses.
     */
    private class CustomArrayAdapter extends ArrayAdapter<Friend> {
        private final Context context;
        private final ArrayList<Friend> friends;

        class ViewHolder {
            public TextView name;
            public TextView birthday;
            public ImageView icon;
        }

        public CustomArrayAdapter(Context context, ArrayList<Friend> friends) {
            super(context, R.layout.list_layout, friends);
            this.context = context;
            this.friends = friends;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            // reuse views
            if (rowView == null) {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_layout, parent, false);
                // configure view holder
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.name = (TextView) rowView
                        .findViewById(R.id.nameTextView1);
                viewHolder.birthday = (TextView) rowView
                        .findViewById(R.id.birthdayTextView1);
                viewHolder.icon = (ImageView) rowView.findViewById(R.id.icon);
                rowView.setTag(viewHolder);
            }

            // fill data
            ViewHolder holder = (ViewHolder) rowView.getTag();
            Friend f = friends.get(position);
            holder.name.setText(f.getFirstName() + " " + f.getLastName());
            holder.birthday.setText(f.getDayOfMonth() + "/" + f.getMonth() + "/"
                    + f.getYear());

            return rowView;
        }

        @Override
        public void add(Friend object) {
            super.add(object);
        }

        @Override
        public void clear() {
            super.clear();
        }

        @Override
        public void remove(Friend object) {
            super.remove(object);
        }

        @Override
        public void sort(Comparator<? super Friend> comparator) {
            super.sort(comparator);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }

        @Override
        public void setNotifyOnChange(boolean notifyOnChange) {
            super.setNotifyOnChange(notifyOnChange);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public Friend getItem(int position) {
            return super.getItem(position);
        }
    }
}
