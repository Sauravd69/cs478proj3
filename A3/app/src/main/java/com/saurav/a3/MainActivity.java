package com.saurav.a3;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //this is the permission which describe in manifest, needs to allow to receive broadcast
    private static final String CUSTOM_PERMISSION = "edu.uic.cs478.f18.project3";
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            {}
            //if user click on deny
            else
            {
                Toast.makeText(this, "WARNING: You can't receive broadcast", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
