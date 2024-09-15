package com.capstone.petgroomingapplication.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.adapter.Pet_class_admin;
import com.capstone.petgroomingapplication.adapter.PetAdapter_admin;

import java.util.ArrayList;
import java.util.List;


public class PetProfile extends Fragment {

    private RecyclerView recyclerView;
    private PetAdapter_admin adapter;
    private RecyclerView.LayoutManager layoutManager;

    public PetProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet_profile_admin, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.petRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Create a list of products
        List<Pet_class_admin> petList = new ArrayList<>();
        petList.add(new Pet_class_admin( R.drawable.dogcatdashboard, "Marimar ","Labrador"));
        petList.add(new Pet_class_admin( R.drawable.dogcatdashboard, "Marimar ","Labrador"));
        petList.add(new Pet_class_admin( R.drawable.dogcatdashboard, "Marimar ","Labrador"));
        petList.add(new Pet_class_admin( R.drawable.dogcatdashboard, "Marimar ","Labrador"));
        petList.add(new Pet_class_admin( R.drawable.dogcatdashboard, "Marimar ","Labrador"));


        // Set up the adapter
        adapter = new PetAdapter_admin(petList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    }
