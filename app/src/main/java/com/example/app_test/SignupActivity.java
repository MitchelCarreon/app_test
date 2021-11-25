package com.example.app_test;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.example.app_test.Utils.User;
import com.example.app_test.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private EditText email_editText, username_editText;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference db_reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        initComponents();

//       FirebaseDatabase.getInstance().getReference("Users").child("Sample_node").setValue("sample_val");
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

        this.db = FirebaseDatabase.getInstance();
        this.db_reference = this.db.getReference("Users");
    }
    public void registerUserViaFirebase() {
        User new_user = new User(
                this.email_editText.getText().toString(),
                this.username_editText.getText().toString(),
                this.binding.signupCountrySpinner.getSelectedCountryName());

        this.db_reference.child(new_user.username).setValue(new_user);
        // TODO: FIX DB.
    }
}