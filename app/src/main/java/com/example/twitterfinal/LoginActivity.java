package com.example.twitterfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLoginUsername, etdLoginPassword;
    private Button btnLogin, btnSignUpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        edtLoginUsername = findViewById(R.id.edtUserName);
        etdLoginPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signingParseUser();
            }
        });
        btnSignUpActivity = findViewById(R.id.btnSignUpActivity);
        btnSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        if (ParseUser.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this,TweetUsers.class));
            finish();
        }
    }

    private void signingParseUser() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Siging in...");
        progressDialog.show();
        ParseUser.logInInBackground(edtLoginUsername.getText().toString(),
                etdLoginPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user!=null && e == null){
                            Toast.makeText(LoginActivity.this, "Signed in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,TweetUsers.class));
                            finish();
                        }else {
                            ParseUser.logOut();
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    progressDialog.dismiss();
                    }
                });
    }
}
