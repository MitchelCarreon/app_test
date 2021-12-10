package com.example.app_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.app_test.Utils.UserData;
import com.example.app_test.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private EditText input_email, input_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        // test
        initComponents();

        this.binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AdventureSelectActivity.class);

                if (isAdmin()) startActivity(intent);
                else if (hasInvalidInput()) { }
                else userLogin(intent);

            }
        });
        this.binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
            }
        });

        this.binding.cyoaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getResources().getString(R.string.what_is_a_cyoa).toString());
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ")); good feature
                if (!v.isSelected()) v.setSelected(true);

                startActivity(intent);
            }
        });
    }

    private void initComponents() {
        this.mAuth = FirebaseAuth.getInstance();
        this.input_email = this.binding.emailField.getEditText();
        this.input_password = this.binding.passwordField.getEditText();
    }

    private void userLogin(Intent intent) {
        mAuth
                .signInWithEmailAndPassword(
                        this.input_email.getText().toString(), this.input_password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful.", Toast.LENGTH_SHORT).show();
                            UserData.init();
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed. Check login details", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private Boolean isAdmin() {
        // admin login
        if (this.binding.emailField.getEditText().getText().toString().equals("admin") &&
                this.binding.passwordField.getEditText().getText().toString().equals("admin")) {
            return true;
        }
        return false;
    }

    private Boolean hasEmptyFields() {
        if (input_email.getText().toString().isEmpty()) {
            this.binding.emailField.setError(getString(R.string.error_required));
            this.binding.emailField.requestFocus();
            return true;
        } else this.binding.emailField.setError(null);

        if (input_password.getText().toString().isEmpty()) {
            this.binding.passwordField.setError(getString(R.string.error_required));
            this.binding.passwordField.requestFocus();
            return true;
        } else this.binding.passwordField.setError(null);

        return false;
    }
    private Boolean hasInvalidInput() {
        if (hasEmptyFields()) return true;

        if (!Patterns.EMAIL_ADDRESS.matcher(input_email.getText().toString()).matches()){
            this.binding.emailField.setError("Please enter a valid email");
            this.binding.emailField.requestFocus();
            return true;
        }
        return false;
    }
}