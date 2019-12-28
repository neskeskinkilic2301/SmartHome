package com.cenah.smarthome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cenah.smarthome.R;
import com.cenah.smarthome.helpers.ApplicationPreferenceManager;
import com.cenah.smarthome.helpers.PrograssBarDialog;
import com.cenah.smarthome.models.SharedModel;
import com.cenah.smarthome.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName, edtPass;
    private CheckBox cbRememberMe;

    private FirebaseAuth auth;
    private PrograssBarDialog prograssBarDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtUserName = findViewById(R.id.edtUserName);
        edtPass = findViewById(R.id.edtPass);
        cbRememberMe = findViewById(R.id.cbRememberMe);

        auth = FirebaseAuth.getInstance();
        prograssBarDialog = new PrograssBarDialog(LoginActivity.this);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = edtUserName.getText().toString();
                String password = edtPass.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                Login(email, password);
            }
        });

    }

    private void Login(final String email, final String password) {
        prograssBarDialog.show();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        prograssBarDialog.hide();
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, Objects.requireNonNull(task.getException()).toString(), Toast.LENGTH_LONG).show();
                            }
                        } else {

                            getUser(email, password);

                        }
                    }
                });
    }

    private void getUser(String email, final String password) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = new User();

                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                     user = childSnapshot.getValue(User.class);
                }

                if(user.isFingerEnabled()){
                    Intent intent = new Intent(LoginActivity.this, FingerprintActivity.class);
                    new ApplicationPreferenceManager(getApplicationContext())
                            .saveSharedInfo(new SharedModel(user, cbRememberMe.isChecked()));
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
                    new ApplicationPreferenceManager(getApplicationContext())
                            .saveSharedInfo(new SharedModel(user, cbRememberMe.isChecked()));
                    startActivity(intent);
                    finish();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


}
