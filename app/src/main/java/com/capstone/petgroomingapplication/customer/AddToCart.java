package com.capstone.petgroomingapplication.customer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.capstone.petgroomingapplication.R;

public class AddToCart extends AppCompatActivity {

    ImageView adcBckbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        // Initialize the back button
        adcBckbtn = findViewById(R.id.addToCartBckBtn);

        // Set the listener for the back button
        adcBckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back when the button is clicked
                finish();
            }
        });
    }
}