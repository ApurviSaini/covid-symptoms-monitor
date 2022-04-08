package com.example.mobilecomputinghomework1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.net.Uri;

import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import static java.lang.Math.abs;


import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private Button btnsymptoms;
    private Button btnrespiratory;
    private Button btnheartrate;
    private Button btnuploadsign;
    private boolean breathing_rate_process = false;
    private Uri fileUri;
    private static final int VIDEO_RECORD_CODE = 101;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private Uri videoPath;
    private TextView breathing_rate_text_view;
    myDbAdapter helper;

    private BroadcastReceiver breathReceiver;
    Bundle bundle;
    private String root_path = Environment.getExternalStorageDirectory().getPath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnrespiratory = findViewById(R.id.btnrespiratory);
        btnheartrate = (Button) findViewById(R.id.btnheartrate);
        btnuploadsign = findViewById(R.id.btnuploadsign);
        btnsymptoms = findViewById(R.id.btnsymptoms);





        breathReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bundle = intent.getExtras();
                Toast.makeText(getApplicationContext(), "Respiratory Rate in Main Thread "+ bundle.getFloat("Respiratory Rate"), Toast.LENGTH_SHORT).show();



                unregisterReceiver(breathReceiver);
            }
        };

        btnrespiratory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Service ", Toast.LENGTH_LONG).show();
                IntentFilter intfil = new IntentFilter();

                intfil.addAction("RESPIRATION");
                registerReceiver(breathReceiver,intfil);
                Intent int2 = new Intent(getApplicationContext(), Breath.class); //Who is requesting, Where is code to be run located
                startService(int2);
            }
        });



        btnuploadsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*

                long identity = helper.insertData("Breathrate", "3");
                if(identity<0)
                {
                    Message.message(getApplicationContext(),"Unsuccessful");
                } else
                {
                    Message.message(getApplicationContext(),"Successful");
                }

                */
            }
        });

        btnsymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();

            }
        });

        if(isCameraPresentInPhone())
        {
            Log.i("VIDEO_RECORD_TAG", "Camera is detected");
            getCameraPermission();
        }
        else
        {
            Log.i("VIDEO_RECORD_TAG", "No Camera detected");
        }




    }



    public void openActivity2() {
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }


    /////////////////////////////////////////////////////////////////////////////////////
    public void recordVideoButtonPressed(View view)
    {
        recordVideo();
    }

    private boolean isCameraPresentInPhone()
    {
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            return true;
        }
        else
        {
            return false;
        }
    }

    private void getCameraPermission()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }

    }

    private void recordVideo()
    {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult(intent, VIDEO_RECORD_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == VIDEO_RECORD_CODE){
            if(resultCode == RESULT_OK)
            {
                videoPath = data.getData();
                Log.i("VIDEO_RECORD_TAG", "Video is recorded and available at path" + videoPath);
            }

            else if (resultCode == RESULT_CANCELED)
            {
                Log.i("VIDEO_RECORD_TAG", "recording video is cancelled");
            }
            else
            {
                Log.i("VIDE0_RECORD_TAG", "recording video GOT SOME ERROR ");
            }
        }


    }


}