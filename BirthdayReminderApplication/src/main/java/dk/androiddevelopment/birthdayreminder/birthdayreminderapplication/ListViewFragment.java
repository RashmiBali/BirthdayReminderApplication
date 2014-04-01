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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;

import model.Friend;
import register.FriendSerializer;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 *
 */
public class ListViewFragment extends ListFragment {
    Context context;
    ArrayList<Friend> friends;
    FriendSerializer fs;

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        friends = new ArrayList<Friend>();
        fs = new FriendSerializer(context, friends);
        fs.read();

        setListAdapter(new CustomArrayAdapter(context, friends));
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Friend friend = (Friend) getListAdapter().getItem(position);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new FriendProfileFragment(friend))
                .commit();
    }

    /**
     * A simple array adapter that creates a list of friends.
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
