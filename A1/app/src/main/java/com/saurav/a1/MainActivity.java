package com.saurav.a1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //this msz will broadcast from app1 to app2 if user select san fransisco
    private static final String BROADCAST_MSZ1 = "CA.is.selected";
    //this msz will broadcast from app1 to app2 if user select new york
    private static final String BROADCAST_MSZ2 = "NY.is.selected";
    //this msz will broadcast from app1 to app3 for any selection
    private static final String BROADCAST_MSZ3 = "broadcast.msz.for.app3";
    //this is the permission which describe in manifest, needs to allow to send broadcast
    private static final String CUSTOM_PERMISSION = "edu.uic.cs478.f18.project3";
    private Button btn1;
    private Button btn2;
    private int selectedByUser = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.id_btn1);
        btn2 = (Button) findViewById(R.id.id_btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option 1 is selected by user
                selectedByUser = 1;
                //check permission status when user select this button
                checkPermission();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //option 2 is selected by user
                selectedByUser = 2;
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
                == PackageManager.PERMISSION_GRANTED) {
            //if option 1 is selected by user
            if(selectedByUser == 1)
            {
                //then msz1 will broadcast to app2
                Intent aIntent = new Intent(BROADCAST_MSZ1);
                sendOrderedBroadcast(aIntent, CUSTOM_PERMISSION);

                //and msz3 will broadcast to app3
                Intent bIntent = new Intent(BROADCAST_MSZ3);
                //since app3 has only one receiver, app3 needs to know which option is selected
                //for that reason we need to use extra
                bIntent.putExtra("extramszforapp3",1);
                sendOrderedBroadcast(bIntent, CUSTOM_PERMISSION);
            }
            //if option2 is selected by user
            else if(selectedByUser == 2)
            {
                //then msz2 will broadcast to app2
                Intent aIntent = new Intent(BROADCAST_MSZ2);
                sendOrderedBroadcast(aIntent, CUSTOM_PERMISSION);

                //and msz3 will broadcast to app3
                Intent bIntent = new Intent(BROADCAST_MSZ3);
                //since app3 has only one receiver, app3 needs to know which option is selected
                //for that reason we need to use extra
                bIntent.putExtra("extramszforapp3",2);
                sendOrderedBroadcast(bIntent, CUSTOM_PERMISSION);
            }
        }
        //if user didn't allow the permission yet, then ask for permission
        else {
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
                if(selectedByUser == 1)
                {
                    Intent aIntent = new Intent(BROADCAST_MSZ1);
                    sendOrderedBroadcast(aIntent, CUSTOM_PERMISSION);

                    Intent bIntent = new Intent(BROADCAST_MSZ3);
                    bIntent.putExtra("extramszforapp3",1);
                    sendOrderedBroadcast(bIntent, CUSTOM_PERMISSION);
                }
                else if(selectedByUser == 2)
                {
                    Intent aIntent = new Intent(BROADCAST_MSZ2);
                    sendOrderedBroadcast(aIntent, CUSTOM_PERMISSION);

                    Intent bIntent = new Intent(BROADCAST_MSZ3);
                    bIntent.putExtra("extramszforapp3",1);
                    sendOrderedBroadcast(bIntent, CUSTOM_PERMISSION);
                }
            }
            //if user click on deny
            else {
                Toast.makeText(this, "WARNING: You can't send broadcast", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
