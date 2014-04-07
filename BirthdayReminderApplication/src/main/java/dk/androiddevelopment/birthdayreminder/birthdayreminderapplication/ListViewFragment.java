package dk.androiddevelopment.birthdayreminder.birthdayreminderapplication;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import model.Friend;
import register.CustomArrayAdapter;

public class ListViewFragment extends ListFragment {
    Context context;
    ArrayList<Friend> friends;
    CustomArrayAdapter adapter;

    public ListViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        friends = new ArrayList<Friend>();

        adapter = new CustomArrayAdapter(context, friends);
        setListAdapter(adapter);
        adapter.read();

        registerForContextMenu(getListView());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Friend friend = (Friend) getListAdapter().getItem(info.position);
        switch (item.getItemId()) {
            case R.id.edit:
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new AddEditFragment(friend, adapter))
                        .commit();
                return true;
            case R.id.delete:
                adapter.delete(friend.getFirstName(), friend.getLastName());
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Friend friend = (Friend) getListAdapter().getItem(position);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new FriendProfileFragment(friend))
                .commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.listviewfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.sortByName:
                sortByName();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.sortByBirthdayDate:
                sortByBirthdayDay();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.sortByBirthdayYear:
                sortByBirthdayYear();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sortByName() {
        Collections.sort(friends, Friend.NAME_COMPARATOR);
    }

    public void sortByBirthdayYear() {
        Collections.sort(friends, Friend.BIRTHDAY_COMPARATOR);
    }

    public void sortByBirthdayDay() {
        Collections.sort(friends, Friend.BIRTHDAY_COMPARATOR_EXCLUDING_YEAR);
    }
}
