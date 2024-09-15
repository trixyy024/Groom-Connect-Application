package com.capstone.petgroomingapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.capstone.petgroomingapplication.model.UserModel;
import com.capstone.petgroomingapplication.registration.Registration;
import com.capstone.petgroomingapplication.store.LocalUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

public class Login extends AppCompatActivity {

    private CountryCodePicker countryCodePicker;
    private EditText phoneInputNumber;
    private EditText passwordInput;
    private ProgressBar progressBar;
    private TextView btnReg;
    Button continueMobileNo;
    TextView ForgotPass;


    private FirebaseFirestore db;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Fire store
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        phoneInputNumber = findViewById(R.id.phoneNumber);
        passwordInput = findViewById(R.id.passwordLogin);
        countryCodePicker = findViewById(R.id.countryCodePickLogin);

        countryCodePicker.registerCarrierNumberEditText(phoneInputNumber);
        continueMobileNo = findViewById(R.id.continue_MobileNo);
        getLogin();
        btnReg = findViewById(R.id.btnRegister);
        getRegisterButton();
        progressBar = findViewById(R.id.progressBarLogin);
        ForgotPass = findViewById(R.id.forgotPassword);
        ForgotPass.setOnClickListener(v ->{
            Intent intent = new Intent(Login.this, forgotPassword.class);
            startActivity(intent);
        });


    }


    private void getRegisterButton() {
        btnReg.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Registration.class);
            startActivity(intent);
        });
    }

    // Method to handle user login and redirection based on user type

    private void handleUserLogin(UserModel user, String userType) {
        if (user != null) {
            Intent intent;
            LocalUser.Store(user);
            if ("admin".equals(userType)) {
                intent = new Intent(Login.this, com.capstone.petgroomingapplication.admin.Dashboard.class);
                Toast.makeText(Login.this, "You are now logged in as Admin", Toast.LENGTH_SHORT).show();
            } else{
                intent = new Intent(Login.this, com.capstone.petgroomingapplication.customer.Dashboard.class);
                Toast.makeText(Login.this, "You are now logged in as Customer", Toast.LENGTH_SHORT).show();
            }

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, "User data not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void AuthenticateUser (PhoneAuthCredential credential, String password) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();

                            if (user != null) {
                                db.collection("admin").whereEqualTo("phoneNumber", user.getPhoneNumber()).get()
                                        .addOnCompleteListener(adminTask -> {
                                            if (adminTask.isSuccessful() && !adminTask.getResult().isEmpty()) {
                                                UserModel admin = adminTask.getResult().getDocuments().get(0).toObject(UserModel.class);
                                                if (admin != null && password.equals(admin.getPassword())) {
                                                    handleUserLogin(admin, "admin");
                                                }
                                            }
                                            else {
                                                db.collection("customer").whereEqualTo("phoneNumber", user.getPhoneNumber()).get()
                                                        .addOnCompleteListener(customerTask -> {
                                                           if (customerTask.isSuccessful() && !customerTask.getResult().isEmpty()) {
                                                               UserModel customer = customerTask.getResult().getDocuments().get(0).toObject(UserModel.class);
                                                               if (customer != null && password.equals(customer.getPassword())) {
                                                                   handleUserLogin(customer, "customer");
                                                               }
                                                           }
                                                        });
                                            }
                                        });
                            }
                        }
                        else {
                            Log.e("App Auth", "Failed to authenticate User.");
                        }
                    }
                });
    }

    private void getLogin() {
        continueMobileNo.setOnClickListener(v -> {
            if (!countryCodePicker.isValidFullNumber()) {
                phoneInputNumber.setError("Phone number not valid");
                return;
            }

            String fullPhoneNumber = countryCodePicker.getFullNumberWithPlus();
            String enteredPassword = passwordInput.getText().toString();

            if (enteredPassword.isEmpty()) {
                passwordInput.setError("Password cannot be empty");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);



            // Query Fire store for both admin and customer collections
            db.collection("admin").whereEqualTo("phoneNumber", fullPhoneNumber).get()
                    .addOnCompleteListener(adminTask -> {
                        if (adminTask.isSuccessful() && !adminTask.getResult().isEmpty()) {
                            UserModel user = adminTask.getResult().getDocuments().get(0).toObject(UserModel.class);
                            if (user != null && enteredPassword.equals(user.getPassword())) {
                                handleUserLogin(user, "admin");
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            db.collection("customer").whereEqualTo("phoneNumber", fullPhoneNumber).get()
                                    .addOnCompleteListener(customerTask -> {
                                        progressBar.setVisibility(View.GONE);
                                        if (customerTask.isSuccessful() && !customerTask.getResult().isEmpty()) {
                                            UserModel user = customerTask.getResult().getDocuments().get(0).toObject(UserModel.class);
                                            if (user != null && enteredPassword.equals(user.getPassword())) {
                                                handleUserLogin(user, "customer");
                                            } else {
                                                Toast.makeText(Login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });
        });
    }
}

