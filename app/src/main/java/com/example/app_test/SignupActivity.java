package com.example.app_test;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.app_test.Utils.User;
import com.example.app_test.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;


public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private EditText email_editText, username_editText, pw_editText, pwretype_editText;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        initComponents();

        this.binding.signupRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserViaFirebase();
            }
        });
    }

    public void initComponents() {
        setTitle(getString(R.string.signup_title));
        this.email_editText = this.binding.inputEmail.getEditText();
        this.username_editText = this.binding.inputUsername.getEditText();
        this.pw_editText = this.binding.inputPassword.getEditText();
        this.pwretype_editText = this.binding.inputPasswordretype.getEditText();

        this.db = FirebaseDatabase.getInstance();
        this.db_reference = this.db.getReference("Users");
        this.mAuth = FirebaseAuth.getInstance();
    }

    private User createUser() {
        User new_user = new User(
                this.email_editText.getText().toString(),
                this.username_editText.getText().toString(),
                this.binding.signupCountrySpinner.getSelectedCountryName());

        if (new_user.username.isEmpty()) {
            Random rand = new Random();
            int upperBound = 9999;
            Integer userRandID = rand.nextInt(upperBound);
            new_user.username = "User" + userRandID.toString();
        }
        return new_user;
    }

    public void registerUserViaFirebase() {
        if (hasInvalidInput()) return;

        User new_user = createUser();

        this.mAuth
                .createUserWithEmailAndPassword(new_user.email, this.pw_editText.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            db_reference.child(new_user.username).setValue(new_user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(SignupActivity.this, "Registration complete.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(SignupActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private Boolean hasInvalidInput() { // TODO: Fix dirty code
        Boolean hasInvalidInput = false;
        int errorCounter = 0;

        if (email_editText.getText().toString().isEmpty()) {
            this.binding.inputEmail.setError("*Required");
            this.binding.inputEmail.requestFocus();
            hasInvalidInput = true;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email_editText.getText().toString()).matches()) {
            this.binding.inputEmail.setError("Please enter a valid email");
            this.binding.inputEmail.requestFocus();
            hasInvalidInput = true;

        } else this.binding.inputEmail.setError(null);

        if (pw_editText.getText().toString().isEmpty()) {
            this.binding.inputPassword.setError("*Required");
            if (this.binding.inputEmail.getError() == null)this.binding.inputPassword.requestFocus();
            hasInvalidInput = true;

        } else if (!pw_editText.getText().toString().matches("(?=.*[0-9a-zA-Z]).{6,}")) {
            this.binding.inputPassword.setError("Must contain at least 6 characters");
            if (this.binding.inputEmail.getError() == null) this.binding.inputPassword.requestFocus();
            hasInvalidInput = true;

        } else this.binding.inputPassword.setError(null);

        if (pwretype_editText.getText().toString().isEmpty()) {

            this.binding.inputPasswordretype.setError("Please confirm password");
            if (this.binding.inputPassword.getError() == null) this.binding.inputPasswordretype.requestFocus();
            hasInvalidInput = true;

        } else if (!pw_editText.getText().toString().equals(pwretype_editText.getText().toString())) {
            this.binding.inputPasswordretype.setError("Password does not match");
            if (this.binding.inputPassword.getError() == null) this.binding.inputPasswordretype.requestFocus();
            hasInvalidInput = true;

        } else this.binding.inputPasswordretype.setError(null);

        return hasInvalidInput;
    }
}

