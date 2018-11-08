package com.saurav.a3;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TextViewFragment extends ListFragment {
    private ListSelectionListener mListener = null;

    // Callback interface that allows this Fragment to notify the CA_Activity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the List
    @Override
    public void onListItemClick(ListView l, View v, int pos, long id) {

        // Indicates the selected item has been checked
        //getListView().setItemChecked(pos, true);

        // Inform the CA_Activity that the item in position pos has been selected
        mListener.onListSelection(pos);
    }

    @Override
    public void onAttach(Context activity) {

        super.onAttach(activity);

        try {
            // Set the ListSelectionListener for communicating with the CA_Activity
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //save the state of this fragment
        setRetainInstance(true);
    }

    // UB:  Notice that the superclass's method does an OK job of inflating the
    //      container layout.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //return the textView fragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        // Set the list adapter for the ListView
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.textview_fragment_layout, CA_Activity.places));

        // Set the list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }
}
