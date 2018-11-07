package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osori.hopelinev1.R;
//import com.microsoft.aspnet.signalr.HubConnection;
//import com.microsoft.aspnet.signalr.HubConnectionBuilder;
//import com.microsoft.aspnet.signalr.HubConnection;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserChatActivity extends AppCompatActivity{

    private Button btnSend;
    private EditText inputBar;
    private LinearLayout chatBox;
    private ScrollView chatBoxScroll;
    private ImageView btnReturn;
    private TextView mentorName;
    private HubConnection hubConnection = HubConnectionBuilder.create("https://hopelineapi.azurewebsites.net/v2/chathub").build();
    private String userName;
    private ArrayList<String> topicsChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        btnSend = (Button)findViewById(R.id.btn_send);
        inputBar = (EditText)findViewById(R.id.edt_chat_input);
        chatBoxScroll = (ScrollView)findViewById(R.id.txt_chat_box_scroll);
        chatBox = (LinearLayout)findViewById(R.id.txt_chat_box);
        btnReturn = (ImageView)findViewById(R.id.btn_chat_return);
        mentorName = (TextView)findViewById(R.id.txt_incoming_user_name);
        userName = "Anon";

        boolean isTopicsEmpty = getIntent().getBooleanExtra("emptyTopics", false);
        if(isTopicsEmpty){
            topicsChosen = getIntent().getStringArrayListExtra("topics");
        }


//        TODO: add the mentor's name to the top of the screen
//        mentorName.setText("");

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        hubConnection.on("NotifyUser", (connected)->{
            Log.d("Connected", "User is: " + connected);
        }, String.class);

        hubConnection.on("Load", (user, message)->{
            Log.d("Loading all messages", message);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("Loading: ",message);
                }
            });
        }, String.class, String.class);

        hubConnection.on("ReceiveMessage", (user, message)->{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!user.equals(userName)){
                        TextView textView = new TextView(getApplicationContext());
                        textView.append(message);
                        chatBox.addView(textView);
                    }
                }
            });
        }, String.class, String.class);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputBar.getText().toString();
                try{
                    hubConnection.send("sendMessage", "Anon", userInput, "room");
                    TextView textView = new TextView(v.getContext());
                    textView.setText("\n" + userInput);
                    textView.setGravity(Gravity.RIGHT);
                    textView.setTextColor(Color.BLUE);
                    chatBox.addView(textView);
                    chatBoxScroll.fullScroll(chatBoxScroll.FOCUS_DOWN);
                    inputBar.setText("");
                }catch (Exception e){
                    Log.d("Connection Error: ", e.toString());
                    Toast.makeText(UserChatActivity.this, "Can't send message, not connected to server...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new HubConnectionTask().execute(hubConnection);
    }
}

class HubConnectionTask extends AsyncTask<HubConnection, Void, Void>{

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
