package com.capstone.petgroomingapplication.customer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.adapter.Services_class_customer;
import com.capstone.petgroomingapplication.adapter.ServicesAdapter_customer;

import java.util.ArrayList;
import java.util.List;

public class Services extends Fragment {

    private RecyclerView recyclerView;
    //private ServicesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public Services(){

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services__customer, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Create a list of products
        List<Services_class_customer> servicesList = new ArrayList<>();
        servicesList.add(new Services_class_customer("Product 1", "$10.00", R.drawable.dogcatdashboard));
        servicesList.add(new Services_class_customer("Product 2", "$20.00", R.drawable.dogcatdashboard));

        Context context = getContext(); // or this if in an Activity

        ServicesAdapter_customer adapter = new ServicesAdapter_customer(servicesList, context);
        recyclerView.setAdapter(adapter);

        return view;
    }

}