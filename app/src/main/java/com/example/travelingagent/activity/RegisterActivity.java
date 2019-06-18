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
import com.example.travelingagent.protocol.api.RegisterClientApi;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private String BASE_URL = "http://192.168.43.126:8080/";
    private String user_id;
    private String email;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the LoginEntity activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (!validate()) {
            onSignupFailed();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterClientApi registerClientApi = retrofit.create(RegisterClientApi.class);

        Map<String, String> options = new HashMap<String, String>();
        options.put("mail", email);
        options.put("userpass", password);
        options.put("username", name);

        Call<String> call = registerClientApi.registerState(options);

       call.enqueue(new Callback<String>() {
           @Override
           public void onResponse(Call<String> call, Response<String> response) {
               user_id = response.body();

               _signupButton.setEnabled(false);

               final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                       R.style.AppTheme_Dark_Dialog);
               progressDialog.setIndeterminate(true);
               progressDialog.setMessage("注册中...");
               progressDialog.show();

               // TODO: Implement your own signup logic here.

               new android.os.Handler().postDelayed(
                       new Runnable() {
                           public void run() {
                               // On complete call either onSignupSuccess or onSignupFailed
                               // depending on success
                               onSignupSuccess(user_id, email);
                               // onSignupFailed();
                               progressDialog.dismiss();
                           }
                       }, 3000);
           }

           @Override
           public void onFailure(Call<String> call, Throwable t) {
               t.printStackTrace();
           }
       });
    }

    public void onSignupSuccess(String userid, String email) {
        _signupButton.setEnabled(true);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
//        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "邮箱或密码不合法", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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

        return valid;
    }
}
