package com.example.osori.hopelinev1.Activities.UserActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.osori.hopelinev1.Activities.CommonActivities.MapsActivity;
import com.example.osori.hopelinev1.Activities.CommonActivities.UserChatActivity;
import com.example.osori.hopelinev1.Presenter.GridPresenter;
import com.example.osori.hopelinev1.R;

public class UserHomePageActivity extends AppCompatActivity {

    private Button btnMap, btnTalkToMentor, btnSignOut, btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);

        btnMap = (Button)findViewById(R.id.btn_map);
        btnTalkToMentor = (Button)findViewById(R.id.btn_talktomentor);
        btnSignOut = (Button)findViewById(R.id.btn_userSignOut);
        btnOptions = (Button)findViewById(R.id.btn_userOptions);

        btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserOptionsActivity.class);
                startActivity(intent);
            }
        });

        btnTalkToMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserChatActivity.class);
                startActivity(intent);
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Remove user data
                Toast.makeText(UserHomePageActivity.this, "Signing out... Removing user data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
