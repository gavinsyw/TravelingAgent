package com.example.travelingagent.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.travelingagent.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    //public static final String EXTRA_USERNAME = "com.example.myfirstapp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        List<String> permissionList = new ArrayList<>();
        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissionList.add(Manifest.permission.READ_PHONE_STATE);
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        String [] permissions = permissionList.toArray(new String[permissionList.size()]);
        ActivityCompat.requestPermissions(LoginActivity.this, permissions, 1);
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