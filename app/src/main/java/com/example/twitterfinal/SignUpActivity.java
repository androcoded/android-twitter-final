package com.example.twitterfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    private EditText edtSignUpUsername,edtSignUpEmail,edtSignUpPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edtSignUpUsername = findViewById(R.id.edtSignUpUserName);
        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}
