package com.saurav.a3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

//for this app receiver is setting up statically
//this receiver is initiating in manifest file

public class MyReceiver extends BroadcastReceiver {

    private static final String CUSTOM_PERMISSION = "edu.uic.cs478.f18.project3";

    //callback method to receive broadcast
    @Override
    public void onReceive(Context context, Intent intent) {
        //if user already allowed the permission, this condition will be true
        if (ContextCompat.checkSelfPermission(context, CUSTOM_PERMISSION)
                == PackageManager.PERMISSION_GRANTED)
        {
            //getting extra from app1, since this app has only one receiver
            int val = intent.getIntExtra("extramszforapp3",0);
            //if extra value is 1 then option 1 is selected
            if(val == 1)
            {
                //open san fransisco activity
                Intent intent1 = new Intent(context, CA_Activity.class);
                context.startActivity(intent1);
            }
            //if extra value is 2 then option 2 is selected
            else if(val == 2)
            {
                //open new york activity
                Intent intent1 = new Intent(context, NY_Activity.class);
                context.startActivity(intent1);
            }
        }
    }
}
