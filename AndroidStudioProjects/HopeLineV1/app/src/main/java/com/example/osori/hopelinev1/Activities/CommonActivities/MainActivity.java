package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.Activities.UserActivities.UserHomePageActivity;
import com.example.osori.hopelinev1.Activities.UserActivities.UserSignOnActivity;
import com.example.osori.hopelinev1.Model.ApiInformation;
import com.example.osori.hopelinev1.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    private android.support.v4.app.Fragment selected;
//    public static ApiInformation apiInformation = new ApiInformation();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        this.selected = HomeFragment.newInstance();
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.toolbar_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_resources:
                        if(selected.getClass() == ResourceFragment.class)
                            return true;
                        selected = ResourceFragment.newInstance();
                        break;
                    case R.id.action_home:
                        if(selected.getClass() == HomeFragment.class)
                            return true;
                        selected = HomeFragment.newInstance();
                        break;
                    case R.id.action_map:
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                        break;
                }
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layout_home, selected);
                transaction.commit();
                return true;

            }
        });

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.layout_home, HomeFragment.newInstance());
        transaction.commit();
    }
}





