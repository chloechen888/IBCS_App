package edu.cis.ibcs_app.Controllers;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import edu.cis.ibcs_app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //This is not great, for extra credit you can fix this so that network calls happen on a different thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

}