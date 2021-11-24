package com.example.app_test;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.example.app_test.Utils.User;
import com.example.app_test.databinding.ActivitySignupBinding;


public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;


    private EditText email_editText, username_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        setTitle(getString(R.string.signup_title));
//        this.binding.inputPasswordretypeArea.setFocusable(true);
//        this.binding.userAndPwArea.setChecked(true);

        this.email_editText = this.binding.inputEmail.getEditText();
        this.username_editText = this.binding.inputUsername.getEditText();

        this.binding.signupRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserViaFirebase();
            }
        });
    }

    public void registerUserViaFirebase() {

    }
}