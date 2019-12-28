package com.cenah.smarthome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.cenah.smarthome.R;
import com.cenah.smarthome.helpers.PrograssBarDialog;
import com.cenah.smarthome.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private PrograssBarDialog prograssBarDialog;
    private Toolbar toolbar;
    private Activity activity;

    private EditText tx_name, tx_surname,
            tx_email, tx_username, tx_password, tx_repassword, tx_phone;

    private FirebaseAuth auth;
    private RadioGroup radioGroup;

    private boolean loginOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activity = this;
        setToolBar();
        prograssBarDialog = new PrograssBarDialog(this);

        auth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.with_fingerprint) {
                    loginOption = false;
                }
                if (id == R.id.with_fingerprint_and_password) {
                    loginOption = true;
                }
            }
        });
    }


    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Register");
        ((AppCompatActivity) Objects.requireNonNull(activity)).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // toolbar.setSubtitle(activity.getString(R.string.today));
    }

    private void register() {

        tx_repassword = findViewById(R.id.tx_repassword);
        tx_name = findViewById(R.id.tx_name);
        tx_surname = findViewById(R.id.tx_surname);
        tx_username = findViewById(R.id.tx_username);
        tx_email = findViewById(R.id.tx_email);
        tx_password = findViewById(R.id.tx_password);
        tx_phone = findViewById(R.id.tx_phone);

        final String name = tx_name.getText().toString();
        final String surName = tx_surname.getText().toString();
        final String userName = tx_username.getText().toString();
        final String userEmail = tx_email.getText().toString();
        final String password = tx_password.getText().toString();
        final String rePassword = tx_repassword.getText().toString();
        final String phone = tx_phone.getText().toString();


        int selectedButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedButtonId == -1) {
            Toast.makeText(RegisterActivity.this, "Login option must be selected", Toast.LENGTH_LONG).show();
            return;
        }

        if (name.isEmpty() || surName.isEmpty() || userName.isEmpty() ||
                userEmail.isEmpty() || password.isEmpty() || rePassword.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(rePassword)) {
            Toast.makeText(this, "Password does not match", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        prograssBarDialog.show();


        auth.createUserWithEmailAndPassword(userEmail, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        prograssBarDialog.hide();
                        Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            addUserTodatabase(userEmail, password, userName, name, surName, phone, loginOption);
                          /*  new ApplicationPreferenceManager(getApplicationContext())
                                    .saveSharedInfo(new User(name,surName,userName,userEmail,password));*/
                            onBackPressed();
                        }
                    }
                });
    }

    private void addUserTodatabase(String email, String password, String userName, String name, String surName, String phone, boolean fingerEnabled) {
        User user = new User(name, surName, userName, email, password, phone, fingerEnabled);
        FirebaseDatabase.getInstance().getReference("users").push().setValue(user).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, "User Info error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        ;

    }
}
