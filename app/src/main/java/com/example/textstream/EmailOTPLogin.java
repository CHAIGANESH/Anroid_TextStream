package com.example.textstream;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailOTPLogin extends AppCompatActivity {

    private EditText emailInput, otpInput;
    private Button sendOtpButton, verifyOtpButton;

    private TextView signupRedirect;
    private String generatedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_otplogin);

        emailInput = findViewById(R.id.emailInput);
        otpInput = findViewById(R.id.otpInput);
        sendOtpButton = findViewById(R.id.sendOtpButton);
        verifyOtpButton = findViewById(R.id.verifyOtpButton);

        signupRedirect = findViewById(R.id.signupRedirect);

        sendOtpButton.setOnClickListener(view -> sendOtp());
        verifyOtpButton.setOnClickListener(view -> verifyOtp());

        signupRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailOTPLogin.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendOtp() {
        String email = emailInput.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(this, "Enter email address", Toast.LENGTH_SHORT).show();
            return;
        }

        generatedOtp = String.valueOf((int) (Math.random() * 900000) + 100000); // Generate 6-digit OTP

        new Thread(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("textstream26@gmail.com", "vmzl gaka kkgw zcqa ");
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("textstream26@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Your OTP Code");
                message.setText("Welcome To Textstream.\n"+"Your OTP is: " + generatedOtp);

                Transport.send(message);
                runOnUiThread(() -> Toast.makeText(this, "OTP sent to email!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "Failed to send OTP: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    private void verifyOtp() {
        String enteredOtp = otpInput.getText().toString();
        String email = emailInput.getText().toString();

        if (enteredOtp.equals(generatedOtp)) {

            new Thread(() -> {
                try {
                    Properties props = new Properties();
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.port", "587");

                    Session session = Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("textstream26@gmail.com", "vmzl gaka kkgw zcqa");
                        }
                    });

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("textstream26@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject("Successfully Loggged In");
                    message.setText("You Successfully Logged In to the TextStream Application.\n Thank you for Preferring Us.\n");

                    Transport.send(message);
                    runOnUiThread(() -> Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show());
                } catch (Exception e) {
                    runOnUiThread(() -> Toast.makeText(this, "Failed To LogIn" + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }).start();

            Intent intent = new Intent(EmailOTPLogin.this,MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }
}