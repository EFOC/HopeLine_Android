package com.example.osori.hopelinev1.Activities.CommonActivities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.osori.hopelinev1.R;

import org.w3c.dom.Text;

public class UserChatActivity extends AppCompatActivity {

    private Button btnSend;
    private EditText inputBar;
    private LinearLayout chatBox;
    private ScrollView chatBoxScroll;
    private ImageView btnReturn;
    private TextView mentorName;

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

//        TODO: add the mentor's name to the top of the screen
//        mentorName.setText("");

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = inputBar.getText().toString();
                TextView textView = new TextView(v.getContext());
                textView.setText("\n" + userInput);
                textView.setGravity(Gravity.RIGHT);
                textView.setTextColor(Color.BLUE);
                chatBox.addView(textView);
                chatBoxScroll.fullScroll(chatBoxScroll.FOCUS_DOWN);
                inputBar.setText("");
            }
        });
    }

}
