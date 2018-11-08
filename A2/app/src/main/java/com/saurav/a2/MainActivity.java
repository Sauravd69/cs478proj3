package com.saurav.a2;

import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private ReceiverCA receiverCA;
    private ReceiverNY receiverNY;
    private IntentFilter intentFilterCA;
    private IntentFilter intentFilterNY;

    //app2 will receive this msz, if user select option 1
    private static final String BROADCAST_MSZ1 = "CA.is.selected";
    //app2 will receive this msz if user select option 2
    private static final String BROADCAST_MSZ2 = "NY.is.selected";
    //this is the permission which describe in manifest, needs to allow to receive broadcast
    private static final String CUSTOM_PERMISSION = "edu.uic.cs478.f18.project3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing two receivers for two options
        //this is programmatic to set up receiver
        receiverCA = new ReceiverCA();
        receiverNY = new ReceiverNY();
        //setting up intent filter and priority
        intentFilterCA = new IntentFilter(BROADCAST_MSZ1);   //msz1, for option1
        intentFilterCA.setPriority(10);
        intentFilterNY = new IntentFilter(BROADCAST_MSZ2);   //msz2, for option2
        intentFilterNY.setPriority(10);
        //register both receivers
        registerReceiver(receiverCA,intentFilterCA);
        registerReceiver(receiverNY,intentFilterNY);

        btn = (Button) findViewById(R.id.idbtn);
        //button to check or ask permission
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check permission status when user select this button
                checkPermission();
            }
        });
    }

    //check permission status, is permission granted or no
    private void checkPermission()
    {
        //if user already allowed the permission, this condition will be true
        if (ContextCompat.checkSelfPermission(this, CUSTOM_PERMISSION)
                == PackageManager.PERMISSION_GRANTED)
        { }
        //if user didn't allow the permission yet, then ask for permission
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{CUSTOM_PERMISSION}, 0);
        }
    }

    //callback method to request permission
    public void onRequestPermissionsResult(int code, String[] permissions, int[] results)
    {
        //if user click any bof the button, either deny or allow
        if (results.length > 0)
        {
            //if user click on allow
            if (results[0] == PackageManager.PERMISSION_GRANTED)
            {
                //then app2 will receive broadcast from app1
            }
            //if user click on deny
            else
            {
                Toast.makeText(this, "WARNING: You can't receive broadcast", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
