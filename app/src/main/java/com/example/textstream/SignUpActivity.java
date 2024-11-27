package com.example.textstream;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class SignUpActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupUsername, signupPassword;
    TextView loginRedirectText, emailOTPLogin;
    Button signupButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupName = findViewById(R.id.sign_name);
        signupEmail = findViewById(R.id.sign_email);
        //signupUsername = findViewById(R.id.sign_username);  // Initialize username EditText
        signupPassword = findViewById(R.id.sign_password);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        emailOTPLogin = findViewById(R.id.email_otp_login);

        databaseHelper = new DatabaseHelper(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = signupName.getText().toString();
                String email = signupEmail.getText().toString();
                String password = signupPassword.getText().toString();

                if(name.isEmpty() || email.isEmpty() ||  password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else{
                    boolean checkUserEmail = databaseHelper.checkEmail(email);

                    if(!checkUserEmail){
                        boolean insert = databaseHelper.insertData(name, email, password);

                        if(insert){
                            Toast.makeText(SignUpActivity.this, "Signup Successful!!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Signup Failed!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(SignUpActivity.this, "User already exists!! Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        emailOTPLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,EmailOTPLogin.class);
                startActivity(intent);
            }
        });
    }

    public static class LogoutDialogFragment extends DialogFragment {

        public interface LogoutDialogListener {
            void onLogoutConfirmed();
        }

        private LogoutDialogListener listener;

        public static LogoutDialogFragment newInstance() {
            return new LogoutDialogFragment();
        }

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            if (context instanceof LogoutDialogListener) {
                listener = (LogoutDialogListener) context;
            } else {
                throw new RuntimeException(context.toString() + " must implement LogoutDialogListener");
            }
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", (dialog, id) -> {
                        if (listener != null) {
                            listener.onLogoutConfirmed();
                        }
                    })
                    .setNegativeButton("No", (dialog, id) -> dialog.dismiss());
            return builder.create();
        }
    }
}