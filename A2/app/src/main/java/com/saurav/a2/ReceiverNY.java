package com.saurav.a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

public class ReceiverNY extends BroadcastReceiver {

    private static final String CUSTOM_PERMISSION = "edu.uic.cs478.f18.project3";

    //callback method to receive broadcast
    @Override
    public void onReceive(Context context, Intent intent) {
        //if user already allowed the permission, this condition will be true
        if (ContextCompat.checkSelfPermission(context, CUSTOM_PERMISSION)
                == PackageManager.PERMISSION_GRANTED)
        {
            //then toast the message
            Toast.makeText(context, "NY selected", Toast.LENGTH_SHORT).show();
        }
    }
}
