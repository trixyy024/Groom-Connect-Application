package com.capstone.petgroomingapplication.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.database.FirebaseHelper;

import java.util.List;

public class AddServices extends Fragment {
    private EditText codeEditText, nameEditText, categoryEditText, priceEditText;
    private FirebaseHelper firebaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_services, container, false);

        // Initialize views
        codeEditText = view.findViewById(R.id.editTextCode);
        nameEditText = view.findViewById(R.id.editTextDesc);
        categoryEditText = view.findViewById(R.id.editTextCategory);
        priceEditText = view.findViewById(R.id.editTextPrice);
        Button saveButton = view.findViewById(R.id.saveBtn);

        firebaseHelper = new FirebaseHelper();

        // Save new data on button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = codeEditText.getText().toString();
                String name = nameEditText.getText().toString();
                String category = categoryEditText.getText().toString();
                String price = priceEditText.getText().toString();

                ServiceClass service = new ServiceClass(code, name, category, price);
                firebaseHelper.addService(service);

                Toast.makeText(getContext(), "Service saved", Toast.LENGTH_SHORT).show();

                // Navigate back to ProductsAndServices fragment
                getParentFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void fetchAndDisplayData() {
        firebaseHelper.fetchData(new FirebaseHelper.DataFetchListener() {
            @Override
            public void onDataFetched(List<ServiceClass> services) {
                // Handle the fetched data (e.g., display in a TableLayout or RecyclerView)
                displayServices(services);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayServices(List<ServiceClass> services) {
        // Implement this method to display the list of services
    }
}
