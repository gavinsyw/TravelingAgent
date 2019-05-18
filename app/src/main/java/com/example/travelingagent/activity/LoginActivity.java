package com.example.travelingagent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.travelingagent.R;

public class LoginActivity extends AppCompatActivity {
    //public static final String EXTRA_USERNAME = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.userName);
        String username = editText.getText().toString();
        intent.putExtra("UserName", username);
        startActivity(intent);
    }
}