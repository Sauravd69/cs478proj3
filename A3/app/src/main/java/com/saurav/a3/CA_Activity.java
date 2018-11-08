package com.saurav.a3;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class CA_Activity extends AppCompatActivity implements TextViewFragment.ListSelectionListener{

    public static String[] places;
    public static String[] websites;

    // The key into the "saved state" bundle
    protected static final String indexpos = "INDEX_POS";
    //variable to store old position
    protected int pos = -1;
    //variable to realize that is the webview fragment was showing previous configuration
    protected int isShown = 0;

    private TextViewFragment textViewFragment = new TextViewFragment();
    private WebViewFragment webViewFragment = new WebViewFragment();
    private FragmentManager mFragmentManager;
    private FrameLayout textViewFrameLayout, webViewFrameLayout;

    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //not starting from scratch
        if(savedInstanceState != null)
        {
            //get old position and know that the webview was showing previous configuration or not
            pos = savedInstanceState.getInt(indexpos);
            isShown = savedInstanceState.getInt("ISSHOWN");
        }

        // Get the string arrays with the places and websites
        places = getResources().getStringArray(R.array.place);
        websites = getResources().getStringArray(R.array.website);
        setContentView(R.layout.activity_ca);

        // Get references to the TextViewFragment and to the WebViewFragment
        textViewFrameLayout = (FrameLayout) findViewById(R.id.text_fragment_container);
        webViewFrameLayout = (FrameLayout) findViewById(R.id.webview_fragment_container);


        // Get a reference to the FragmentManager
        mFragmentManager = getFragmentManager();

        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the TextFragment to the layout
        // UB: 10/2/2016 Changed add() to replace() to avoid overlapping fragments
        fragmentTransaction.replace(R.id.text_fragment_container, textViewFragment);

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        mFragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout()
    {
        // Determine whether the WebViewFragment has been added
        if (!webViewFragment.isAdded()) {

            // Make the TextFragment occupy the entire layout
            textViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
        } else {

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                // Make the WebViewFragment occupy the entire layout
                textViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
                webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
            }
            else
            {
                // Make the TextLayout take 1/3 of the layout's width
                textViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the WebViewLayout take 2/3's of the layout's width
                webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }
        }
    }

    //TextViewFragment class has listener called onListSelection
    //if user select item on textView fragment, then this method will call
    @Override
    public void onListSelection(int index)
    {
        //user selects one item, so webview fragment will appear
        isShown = 1;
        if (!webViewFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the webViewFragment to the layout
            fragmentTransaction.replace(R.id.webview_fragment_container, webViewFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }
        //tell WebViewFragment class that which website user wants to see
        webViewFragment.showWebsiteAtIndex(index);
    }

    @Override
    protected void onStart() {

        super.onStart();
        if(isShown == 1)
        {
            textViewFragment.getListView().setItemChecked(pos,true);
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                // Make the webViewFragment occupy the entire layout
                textViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        0, MATCH_PARENT));
                webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
            }
            else
            {
                // Make the textLayout take 1/3 of the layout's width
                textViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                // Make the webviewLayout take 2/3's of the layout's width
                webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            }

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

            // Add the webViewFragment to the layout
            fragmentTransaction.replace(R.id.webview_fragment_container, webViewFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
            webViewFragment.showWebsiteAtIndex(pos);
        }
    }

    //save required info
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(indexpos,webViewFragment.getShownIndex());
        outState.putInt("ISSHOWN", isShown);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //if user press soft back button
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            //webView fragment will disappear
            isShown = 0;
            //selected item will be unchecked
            textViewFragment.getListView().setItemChecked(-1,true);
        }
        return super.onKeyDown(keyCode, event);
    }
}
