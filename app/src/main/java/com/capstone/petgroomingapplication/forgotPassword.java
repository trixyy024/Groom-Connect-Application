package com.capstone.petgroomingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.capstone.petgroomingapplication.registration.OTP_Verification;

public class forgotPassword extends AppCompatActivity {
    Button OTP;
    ImageView xBtn;
    EditText phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);

        phonenumber = findViewById(R.id.phoneNumber);
        xBtn = findViewById(R.id.closeButton);
        xBtn.setOnClickListener(v ->{
            finish();
        });
        OTP = findViewById(R.id.sendOtpButton);

        OTP.setOnClickListener(v -> {
            // Get the phone number from EditText
            String phoneNumber = phonenumber.getText().toString().trim();

            if (!phoneNumber.isEmpty()) {
                // Pass phone number to OTP_Verification activity
                Intent intent = new Intent(forgotPassword.this, OTP_Verification.class);
                intent.putExtra("phone_number", phoneNumber);
                startActivity(intent);
            } else {
                phonenumber.setError("Phone number cannot be empty");
            }
        });
    }
}