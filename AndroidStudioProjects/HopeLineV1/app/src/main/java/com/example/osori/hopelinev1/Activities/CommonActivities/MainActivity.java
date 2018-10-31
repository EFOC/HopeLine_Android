package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.osori.hopelinev1.Activities.UserActivities.UserHomePageActivity;
import com.example.osori.hopelinev1.Activities.UserActivities.UserSignOnActivity;
import com.example.osori.hopelinev1.R;
import com.example.osori.hopelinev1.View.ILoginView;

public class MainActivity extends AppCompatActivity{

    Button btnGuest, btnUser, btnMentor;
    private String TAG = "HERE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"Start");

        btnGuest = (Button)findViewById(R.id.btn_guestSignOn);
        btnUser = (Button)findViewById(R.id.btn_userSignOn);
        btnMentor = (Button)findViewById(R.id.btn_mentorSignOn);

        btnMentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logged in Mentor", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, );
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logged in User", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), UserHomePageActivity.class);
                Log.d(TAG,"Button Clicked");

                startActivity(intent);
            }
        });

        btnGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Logged in Guest", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
