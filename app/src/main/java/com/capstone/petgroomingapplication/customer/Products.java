package com.capstone.petgroomingapplication.customer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.adapter.Product_class_customer;
import com.capstone.petgroomingapplication.adapter.ProductAdapter_admin;

import java.util.ArrayList;
import java.util.List;

public class Products extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter_admin adapter;
    private RecyclerView.LayoutManager layoutManager;

    public Products() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_admin, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Create a list of products
        List<Product_class_customer> productList = new ArrayList<>();
        productList.add(new Product_class_customer("Product 1", "$10.00", R.drawable.dogcatdashboard));
        productList.add(new Product_class_customer("Product 2", "$20.00", R.drawable.dogcatdashboard));
        // Add more products as needed

        // Set up the adapter
        adapter = new ProductAdapter_admin(productList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
