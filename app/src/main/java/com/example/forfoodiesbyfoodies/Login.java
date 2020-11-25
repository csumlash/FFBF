package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    EditText logEmail, logPw;
    Button login, logRegister;
    TextView logWarning, logRegMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logEmail = findViewById(R.id.et_log_email);
        logPw = findViewById(R.id.et_log_password);
        login = findViewById(R.id.btn_log_login);
        logWarning = findViewById(R.id.tv_log_warning);
        logRegMessage = findViewById(R.id.tv_log_regmessage);
        logRegister = findViewById(R.id.btn_log_register);
    }
}