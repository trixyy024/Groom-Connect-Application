package com.capstone.petgroomingapplication.registration;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.utils.AndroidUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class OTP_Verification extends AppCompatActivity {

     String phoneNumber;
     Long timeoutSeconds = 60L;
     String verificationCode;
     PhoneAuthProvider.ForceResendingToken resendingToken;

    EditText code1, code2, code3, code4, code5, code6;
    Button verify;
     ProgressBar progressBar;
     TextView resentOtpTextView;
     FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        //OTP inputs
        code1 = findViewById(R.id.otpCode1); // Ensure these IDs are correct
        code2 = findViewById(R.id.otpCode2);
        code3 = findViewById(R.id.otpCode3);
        code4 = findViewById(R.id.otpCode4);
        code5 = findViewById(R.id.otpCode5);
        code6 = findViewById(R.id.otpCode6);

        verify = findViewById(R.id.verifyBtn);
        progressBar = findViewById(R.id.progressBarOTP);
        resentOtpTextView = findViewById(R.id.resendOTP);

        TextView textMobile = findViewById(R.id.textMobile);

        phoneNumber = getIntent().getExtras().getString("phone");
     //   Toast.makeText(getApplicationContext(), phoneNumber, Toast.LENGTH_LONG).show();

        sendOtp(phoneNumber, false);

        // Check if the phone number is not null and set it to the TextView
       if (phoneNumber != null) {
          textMobile.setText(phoneNumber);
       } else {
           textMobile.setText("Phone number not available");
       }

       setUpOTPCodes();

        verify.setOnClickListener(v -> {
            String enteredOtp = code1.getText().toString() +
                    code2.getText().toString() +
                    code3.getText().toString() +
                    code4.getText().toString() +
                    code5.getText().toString() +
                    code6.getText().toString();
            PhoneAuthCredential credential =  PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
            signIn(credential);
        });

        resentOtpTextView.setOnClickListener((v)->{
            sendOtp(phoneNumber,true);
        });
    }

    void sendOtp(String phoneNumber, boolean isResend) {
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.e("OTP_verification", "Verification failed: " + e.getMessage());
                                AndroidUtils.showToast(getApplicationContext(), "OTP Verification failed");
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                AndroidUtils.showToast(getApplicationContext(), "OTP sent successfully");
                                setInProgress(false);
                            }
                        });

        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    void setInProgress(boolean inProgress) {
        if(inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            verify.setVisibility(View.GONE);
        }else {
            progressBar.setVisibility(View.GONE);
            verify.setVisibility(View.VISIBLE);
        }
    }

    void signIn(PhoneAuthCredential phoneAuthCredential) {
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(false);
                if(task.isSuccessful()){
                    Intent intent = new Intent(OTP_Verification.this,RegisteringAccount.class);
                    intent.putExtra("phone",phoneNumber);
                    startActivity(intent);
                }else{
                    AndroidUtils.showToast(getApplicationContext(),"OTP verification failed");
                }
            }
        });
    }
    void startResendTimer() {
        resentOtpTextView.setEnabled(false);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeoutSeconds--;
                resentOtpTextView.setText("Resend OTP in " + timeoutSeconds + " seconds");
                if (timeoutSeconds <= 0) {
                    timeoutSeconds = 60L;
                    timer.cancel();
                    runOnUiThread(() -> {
                        resentOtpTextView.setEnabled(true);
                    });
                }
            }
        }, 0, 1000);
    }


    void setUpOTPCodes() {
        code1.addTextChangedListener(new OTPTextWatcher(code2));
        code2.addTextChangedListener(new OTPTextWatcher(code3));
        code3.addTextChangedListener(new OTPTextWatcher(code4));
        code4.addTextChangedListener(new OTPTextWatcher(code5));
        code5.addTextChangedListener(new OTPTextWatcher(code6));
    }

    private static class OTPTextWatcher implements TextWatcher {
        private final EditText nextEditText;

        OTPTextWatcher(EditText nextEditText) {
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!s.toString().trim().isEmpty()) {
                nextEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }
}