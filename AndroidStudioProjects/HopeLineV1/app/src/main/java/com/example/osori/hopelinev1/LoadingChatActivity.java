package com.example.osori.hopelinev1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.Activities.CommonActivities.UserChatActivity;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.util.concurrent.TimeUnit;

public class LoadingChatActivity extends AppCompatActivity {

    private String connectionString;
    private HubConnection hubConnection = HubConnectionBuilder.create("https://hopelineapi.azurewebsites.net/v2/chathub").build();
    private TextView tvLoadingChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_chat);

        tvLoadingChat = (TextView)findViewById(R.id.tv_loadingChat);

        Log.d("LoadingChat", "Started");

//        LoadingChatActivity.this.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                Toast.makeText(LoadingChatActivity.this, "Hi", Toast.LENGTH_SHORT).show();
//                hubConnection.on("NotifyUser", (connection)->{
//                    Log.d("LoadingChat", "inside runUI");
//                    Toast.makeText(LoadingChatActivity.this, "Inside", Toast.LENGTH_SHORT).show();
//                }, String.class);
//            }
//        });
        hubConnection.on("NotifyUser", (connected)->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int time = 0;
//                    tvLoadingChat.setText("Find Ment");
                    while(!connected.equals("Finding Available Mentors...") || time != 10) {
                        Log.d("LoadingChat", "Before toast" + connected);
                        tvLoadingChat.append(".");
                        Toast.makeText(LoadingChatActivity.this, connected, Toast.LENGTH_SHORT).show();
                        Log.d("LoadingChat", "After toast");
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (Exception e) {
                            Log.d("LoadingChat", "TimeUnit Failed");
                        }
                        Log.d("LoadingChat", "time: " + time + " getting text: " + tvLoadingChat.getText().toString());
                        time++;
                    }
                    if(time == 10){
                        Toast.makeText(LoadingChatActivity.this, "Could not find mentor, going back...", Toast.LENGTH_SHORT).show();
                        try {
                            TimeUnit.SECONDS.sleep(5);
                            LoadingChatActivity.this.finish();
                        } catch (Exception e) {
                            Log.d("LoadingChat", "TimeUnit Failed");
                        }
                    }else{
                        Toast.makeText(LoadingChatActivity.this, "Mentor Found", Toast.LENGTH_SHORT).show();
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (Exception e) {
                            Log.d("LoadingChat", "TimeUnit Failed");
                        }
//                        Intent intent = new Intent(LoadingChatActivity.this, UserChatActivity.class);
//                        startActivity(intent);
//                        LoadingChatActivity.this.finish();
                    }
                }
            });


        }, String.class);
        new HubConnectionTask().execute(hubConnection);
    }




    class HubConnectionTask extends AsyncTask<HubConnection, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(HubConnection... hubConnections) {
            HubConnection hubConnection = hubConnections[0];
            hubConnection.start().blockingAwait();
            hubConnection.invoke(String.class, "RequestToTalk", "Guest1234512345");
            hubConnection.invoke(String.class, "LoadMessage", "room");
            return null;
        }
    }
}
