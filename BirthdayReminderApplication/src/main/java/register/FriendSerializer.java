package register;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import model.Friend;

/**
 * Created by ShahrozAliAzmat on 29/03/14.
 */
public class FriendSerializer {
    private Context context;
    private ArrayList<Friend> friends;
    private ArrayList<Friend> friendsList;
    final String FILENAME = "friendsList";

    public FriendSerializer() {

    }

    public FriendSerializer(Context context) {
        this.context = context;
        friends = new ArrayList<Friend>();
    }

    public FriendSerializer(Context context, ArrayList<Friend> friendsList) {
        this.context = context;
        this.friendsList = friendsList;
        friends = new ArrayList<Friend>();
    }

    public boolean findDuplicate(String firstName, String lastName) {
        String myFirstName;
        String myLastName;

        for (Friend friend : friendsList) {
            myFirstName = friend.getFirstName();
            myLastName = friend.getLastName();
            if (myFirstName.equals(firstName) && myLastName.equals(lastName)) {
                return true;
            }
        }
        return false;
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
            friends.add(friend);

            try {
                FileOutputStream fileOut = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(friends);
                out.close();
                fileOut.close();
                return true;
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
        return false;
    }

    public void read() {
        friends.clear();
        try {
            FileInputStream in = context.openFileInput(FILENAME);
            ObjectInputStream sin = new ObjectInputStream(in);
            friends = (ArrayList<Friend>) sin.readObject();
            in.close();
            sin.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(Friend friend: friends){
            friendsList.add(friend);
        }
    }

    public Friend find(String firstName, String lastName) {
        String myFirstName;
        String myLastName;

        for (Friend friend : friends) {
            myFirstName = friend.getFirstName();
            myLastName = friend.getLastName();
            if (myFirstName.equals(firstName) && myLastName.equals(lastName)) {
                return friend;
            }
        }
        return null; // not found
    }
}
