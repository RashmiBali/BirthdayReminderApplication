package register;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;

import dk.androiddevelopment.birthdayreminder.birthdayreminderapplication.R;
import model.Friend;

/**
 * Created by ShahrozAliAzmat on 05/04/14.
 */
public class CustomArrayAdapter extends ArrayAdapter<Friend> {
    private final Context context;
    private final ArrayList<Friend> friends;
    private ArrayList<Friend> friendsList;
    private final String FILENAME = "friendsList2.ser";

    class ViewHolder {
        public TextView name;
        public TextView birthday;
        public ImageView icon;
    }

    public CustomArrayAdapter(Context context, ArrayList<Friend> friends) {
        super(context, R.layout.list_layout, friends);
        this.context = context;
        this.friends = friends;
        friendsList = new ArrayList<Friend>();
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
        holder.birthday.setText(f.getDayOfMonth() + "/" + (f.getMonth() + 1) + "/"
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

    @Override
    public void addAll(Collection<? extends Friend> collection) {
        super.addAll(collection);
    }

    @Override
    public void addAll(Friend... items) {
        super.addAll(items);
    }

    public void read() {
        clear();
        try {
            FileInputStream in = context.openFileInput(FILENAME);
            ObjectInputStream sin = new ObjectInputStream(in);
            friendsList = (ArrayList<Friend>) sin.readObject();
            in.close();
            sin.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(Friend friend: friendsList){
            add(friend);
        }
        notifyDataSetChanged();
    }

    public boolean add(String firstName, String lastName,
                       GregorianCalendar birthday) {
        if (new File(context.getFilesDir() + "/" + FILENAME).isFile()) {
            read();
        }

        if(!findDuplicate(firstName, lastName)) {
            Friend friend = new Friend();
            friend.setFirstName(firstName);
            friend.setLastName(lastName);
            friend.setBirthday(birthday);
            add(friend);

            try {
                FileOutputStream fileOut = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(friends);
                out.close();
                fileOut.close();
                notifyDataSetChanged();
                return true;
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        return false;
    }

    public boolean findDuplicate(String firstName, String lastName) {
        String myFirstName;
        String myLastName;

        for (Friend friend : friends) {
            myFirstName = friend.getFirstName();
            myLastName = friend.getLastName();
            if (myFirstName.equals(firstName) && myLastName.equals(lastName)) {
                return true;
            }
        }
        return false;
    }

    public boolean delete(String firstName, String lastName) {
        Iterator<Friend> it = friendsList.iterator();
        while (it.hasNext()) {
            Friend f = it.next();
            if (f.getFirstName() == firstName && f.getLastName() == lastName) {
                it.remove();
                remove(f);
            }
        }
        addList(friendsList);
        return true;
    }

    public void addList(ArrayList<Friend> friends) {
        try {
            FileOutputStream fileOut = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(friends);
            out.close();
            fileOut.close();
            notifyDataSetChanged();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void editFriend(String oldFirstName, String oldLastName, String firstName, String lastName, GregorianCalendar birthday) {
        delete(oldFirstName, oldLastName);
        add(firstName, lastName, birthday);
    }
}
