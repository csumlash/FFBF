package com.example.forfoodiesbyfoodies;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    EditText email, firstName, lastName, password;
    TextView emailWarning, firstNameWarning, lastNameWarning, passwordWarning;
    Button register;
    TextWatcher emailTextWatcher;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.et_registration_email);
        /*emailTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if( !email.getText().toString().contains("@")){
                    emailWarning.setText("@ is essential in e-mails");
                }else{
                    emailWarning.setText("looks fine!");
                }
            }
        };
        email.addTextChangedListener(emailTextWatcher);*/
        emailWarning = findViewById(R.id.tv_registration_email_warning);
        firstName = findViewById(R.id.et_registration_firstname);
        firstNameWarning = findViewById(R.id.tv_registration_firstname_warning);
        lastName = findViewById(R.id.et_registration_lastname);
        lastNameWarning = findViewById(R.id.tv_registration_lastname_warning);
        password = findViewById(R.id.et_registration_password);
        passwordWarning = findViewById(R.id.tv_registration_password_warning);
        register = findViewById(R.id.btn_registration_register);

        dbRef = FirebaseDatabase.getInstance().getReference("users");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(
                        email.getText().toString(), firstName.getText().toString(),
                        lastName.getText().toString(), "user"
                );

                dbRef.child(dbRef.push().getKey()).setValue(user);
            }
        });


    }
}
