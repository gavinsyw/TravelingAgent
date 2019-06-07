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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelingagent.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NewLoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

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
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(NewLoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("验证中...");
        progressDialog.show();

        final String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess(email);
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 300);
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

    public void onLoginSuccess(String email) {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);
//        EditText editText = (EditText) findViewById(R.id.userName);
//        String username = editText.getText().toString();
        intent.putExtra("UserName", email);
        startActivity(intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, MainActivity.class);
//        EditText editText = (EditText) findViewById(R.id.userName);
//        String username = editText.getText().toString();
        startActivity(intent);
        _loginButton.setEnabled(true);
    }

    private boolean loginByasyncHttpcClientGet(String email,String password) {

        AsyncHttpClient client = new AsyncHttpClient();
        //String url = "http://www.baidu.com";
        String url = "http://10.162.235.166:8080/jsf-helloworld/login?";
        System.out.println("111");
        RequestParams params =  new RequestParams();
        params.put("mail",email);
        params.put("userpass",password);
        System.out.println("222:"+email+" "+password);
        final boolean[] objs = new boolean[1];
        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                Log.d("请求响应码",i+"");
                for (int ii = 0; ii < headers.length;ii++){
                    Header header = headers[ii];
                    Log.d("values","header name:"+header.getName()+" value:"+header.getValue());
                    System.out.println(new String(bytes));
                }
                String flag = new String(bytes);
//                tv_result.setText(new String(bytes));
                if(flag.equals("0")){
                    objs[0] = false;
                }
                else{
                    objs[0]= true;
                    System.out.println("+++");
                }

                System.out.println("333:" + " ");
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
//                objs[0]=false;
                Log.d("failure", "kkk");
                throwable.printStackTrace();
            }
        });
        return objs[0];
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
//        List<string> list = new ArrayList<boolean>();
//        boolean x[] = {valid};

        if (valid == true){
            valid =  loginByasyncHttpcClientGet(email,password);
            System.out.println("after:"+valid);
        }

        return valid;
    }
}