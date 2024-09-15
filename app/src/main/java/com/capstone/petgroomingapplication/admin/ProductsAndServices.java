package com.capstone.petgroomingapplication.admin;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.database.FirebaseHelper;

import java.util.List;

public class ProductsAndServices extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_and_services_admin, container, false);

        Button addServiceBtn = view.findViewById(R.id.sAddBtn);
        addServiceBtn.setOnClickListener(v -> {
            // Navigate to AddServices fragment
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new AddServices());
            fragmentTransaction.addToBackStack(null); // Add the transaction to the back stack
            fragmentTransaction.commit();
        });

        // Call fetchAndDisplayData to load existing services
        fetchAndDisplayData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAndDisplayData();
    }

    private void fetchAndDisplayData() {
        FirebaseHelper firebaseHelper = new FirebaseHelper();
        firebaseHelper.fetchData(new FirebaseHelper.DataFetchListener() {
            @Override
            public void onDataFetched(List<ServiceClass> services) {
                // Handle the fetched data
                if (getView() != null) {
                    updateTableLayout(services);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getContext(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTableLayout(List<ServiceClass> services) {
        TableLayout tableLayout = getView().findViewById(R.id.serviceTableLayout);

        if (tableLayout == null) {
            return; // Handle the case where tableLayout is not found
        }

        // Clear existing rows
        tableLayout.removeAllViews();

        // Add table headers (optional)
        TableRow headerRow = new TableRow(getContext());
        String[] headers = {"Code", "Name", "Category", "Price"};
        for (String header : headers) {
            TextView textView = new TextView(getContext());
            textView.setText(header);
            headerRow.addView(textView);
        }
        tableLayout.addView(headerRow);

        // Add data rows
        for (ServiceClass service : services) {
            TableRow row = new TableRow(getContext());

            TextView codeTextView = new TextView(getContext());
            codeTextView.setText(service.getCode());
            row.addView(codeTextView);

            TextView nameTextView = new TextView(getContext());
            nameTextView.setText(service.getName());
            row.addView(nameTextView);

            TextView categoryTextView = new TextView(getContext());
            categoryTextView.setText(service.getCategory());
            row.addView(categoryTextView);

            TextView priceTextView = new TextView(getContext());
            priceTextView.setText(service.getPrice());
            row.addView(priceTextView);

            tableLayout.addView(row);
        }
    }
}
