package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.Model.ApiConnection;
import com.example.osori.hopelinev1.R;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LoadingChatActivity extends AppCompatActivity {

    private String connectionString;
//    private HubConnection hubConnection = HubConnectionBuilder.create("https://hopelineapi.azurewebsites.net/v2/chathub").build();
    private TextView tvLoadingChat;
    private Button btn_cancel;
    private boolean connected;
    private String room;
    private String guestName;
    private int time;
    public static ApiConnection apiConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_chat);

        Random random = new Random();
        guestName = "Guest" + random.nextInt(999999) + 1000;
        Log.d("LoadingChat", "guest name " + guestName);
        tvLoadingChat = (TextView)findViewById(R.id.tv_loadingChat);
        time = 0;
        btn_cancel = (Button)findViewById(R.id.btn_cancelChat);
        Log.d("LoadingChat", "Started");
        apiConnection = new ApiConnection(guestName, getApplicationContext(), this);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoadingChatActivity.this, "Canceling...", Toast.LENGTH_SHORT).show();
                try{
                    TimeUnit.SECONDS.sleep(3);
                    LoadingChatActivity.this.finish();
                }catch (Exception e){
                    Log.d("LoadingChat", "Error: " + e);
                }
            }
        });
        int time = 0;
        int connectionCode = -2;
        for(time = 0; time < 10; time++){
            try{
                Log.d("LoadingChat", "Time is: " + time + "Connection Code is: " + connectionCode);
                TimeUnit.SECONDS.sleep(3);
                time++;
                connectionCode = apiConnection.getConnectionCode();
                if(connectionCode == 1)
                    break;
            }catch (Exception e) {
                Log.d("LoadingChat", "Error in getting connection code: " + e.toString());
            }
        }
        if(time == 10){
            Log.d("LoadingChat", "time is 10");
            finish();
        }else if(connectionCode == 1){
            Intent intent = new Intent(this, UserChatActivity.class);
            startActivity(intent);
            intent.putExtra("room", room);
            intent.putExtra("guestName", guestName);
        }
    }
}
