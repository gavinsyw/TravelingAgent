//package com.example.travelingagent.activity;
//
//import android.Manifest;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.EditText;
//
//import com.example.travelingagent.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class LoginActivity extends AppCompatActivity {
//    //public static final String EXTRA_USERNAME = "com.example.myfirstapp.MESSAGE";
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_activity);
//
//        List<String> permissionList = new ArrayList<>();
//        permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissionList.add(Manifest.permission.READ_PHONE_STATE);
//        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        String [] permissions = permissionList.toArray(new String[permissionList.size()]);
//        ActivityCompat.requestPermissions(LoginActivity.this, permissions, 1);
//    }
//
//    /** Called when the user taps the Send button */
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, MainActivity.class);
//        EditText editText = (EditText) findViewById(R.id.userName);
//        String username = editText.getText().toString();
//        intent.putExtra("UserName", username);
//        startActivity(intent);
//    }
//}
package com.example.travelingagent.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelingagent.R;
import com.example.travelingagent.protocol.api.LoginClientApi;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private String email;
    private String password;
    private String user_id;
    private String BASE_URL = "http://192.168.43.126:8080/";


    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login_activity);
        ButterKnife.inject(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ///////////////////////////////////////////////////////////////// 未连接
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                //        EditText editText = (EditText) findViewById(R.id.userName);
//                //        String username = editText.getText().toString();
//                intent.putExtra("UserName", "test@163.com");
//                intent.putExtra("user_id", "1");
//                startActivity(intent);
//                ///////////////////////////////////////////////////////////////// 未连接

                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);//连接SignupActivity
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "LoginEntity");

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

        if (!validate()) {
            onLoginFailed();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LoginClientApi loginClientApi = retrofit.create(LoginClientApi.class);

        Map<String, String> options = new HashMap<String, String>();
        options.put("mail", email);
        options.put("userpass", password);

        Call<String> call = loginClientApi.loginState(options);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String state = response.body();

                if (state.equals("0")) {
                    Toast.makeText(LoginActivity.this, "邮箱或密码错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {
                    user_id = state;
                }

                _loginButton.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("验证中...");
                progressDialog.show();

                // TODO: Implement your own authentication logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                onLoginSuccess();
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 300);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("email", email);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        System.out.println("!!!!!!!!!");

        return valid;
    }
}