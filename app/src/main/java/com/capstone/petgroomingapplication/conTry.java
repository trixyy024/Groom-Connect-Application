package com.capstone.petgroomingapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.capstone.petgroomingapplication.database.ConnectSQL;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class conTry extends AppCompatActivity {

    private Button getDataButton;
    private TextView dataTextView;
    private ExecutorService executorService;
    private Handler mainThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_con_try);

        Log.w("App","Created App");


        // Initialize button and text view
        getDataButton = findViewById(R.id.getDataButton);
        dataTextView = findViewById(R.id.dataTextView);

        // Initialize ExecutorService for background threads
        executorService = Executors.newSingleThreadExecutor();

        // Handler to update the UI from the main thread
        mainThreadHandler = new Handler(Looper.getMainLooper());

        // Set an OnClickListener to the button
        getDataButton.setOnClickListener(v -> fetchData());
    }

    // Method to fetch data
    private void fetchData() {
        Log.w("App", "Firebase: "+FirebaseApp.getInstance().toString());

        executorService.execute(() -> {
            String result = "";
            Connection conn = ConnectSQL.getConnection();
            Log.w("App", conn.toString());

            if (conn != null) {
                Log.w("Connection","connection stablished");
                mainThreadHandler.post(() -> Toast.makeText(conTry.this, "Connection established successfully", Toast.LENGTH_SHORT).show());

                try {
                    // Create a statement and execute the query
                    Statement stmt = conn.createStatement();
                    String query = "SELECT TOP 1 * FROM Services";  // Modify this query
                    ResultSet rs = stmt.executeQuery(query);

                    // Retrieve data from the result set
                    if (rs.next()) {
                        result = rs.getString("Category");  // Replace with your actual column name
                    } else {
                        mainThreadHandler.post(() -> Toast.makeText(conTry.this, "No data found.", Toast.LENGTH_SHORT).show());
                    }

                    // Close the result set, statement, and connection
                    rs.close();
                    stmt.close();
                    conn.close();

                } catch (Exception e) {
                    // Show toast message for any error during query execution
                    mainThreadHandler.post(() -> Toast.makeText(conTry.this, "Error fetching data", Toast.LENGTH_SHORT).show());
                    e.printStackTrace();
                }
            } else {
                // Show toast message for failed connection
                Log.e("App", "Failed To Connect");
                mainThreadHandler.post(() -> Toast.makeText(conTry.this, "Connection failed", Toast.LENGTH_SHORT).show());
            }

            // Update the UI on the main thread
            String finalResult = result;
            mainThreadHandler.post(() -> {
                if (!finalResult.isEmpty()) {
                    dataTextView.setText(finalResult);
                    // Show toast message with fetched data
                    Toast.makeText(conTry.this, "Data retrieved: " + finalResult, Toast.LENGTH_SHORT).show();
                } else {
                    dataTextView.setText("No data available.");
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();  // Shutdown the executor when the activity is destroyed
    }
}