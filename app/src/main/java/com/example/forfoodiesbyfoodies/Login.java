package com.example.forfoodiesbyfoodies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    // The following attributes will contain information are mandatory to perform a login
    EditText username, psw;
    // These button attributes will listen to the user interaction to do the login or registration process
    Button login, register;
    // The next attributes will be responsible to interact with the user if anything needs more attention
    TextView loginWarningMsg, registerMsg;
    // The attributes below will be technical ones to let the code perform actions and accept data to/from databases


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Linking the activity GUI views/elements to this code
        username = findViewById(R.id.et_log_email);
        psw = findViewById(R.id.et_log_password);
        login = findViewById(R.id.btn_log_login);
        loginWarningMsg = findViewById(R.id.tv_log_warning);
        registerMsg = findViewById(R.id.tv_log_regmessage);
        register = findViewById(R.id.btn_log_register);

        // the login button object performs the declared actions in the login.setonClickListener(...){...};
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // local variables to store and then check the values are entered by the user
                String enteredName = username.getText().toString();
                String enteredPsw = username.getText().toString();
                // logical evaluation of username and password
                if (enteredName.length() > 0 && enteredPsw.length() > 0){
                    loginWarningMsg.setText("Looks fine!");
                } else {
                    loginWarningMsg.setText("E-mail & Password are mandatory to login!");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister = new Intent(Login.this, Registration.class);
                startActivity(toRegister);
            }
        });
    }


}