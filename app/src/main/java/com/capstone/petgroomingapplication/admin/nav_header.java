package com.capstone.petgroomingapplication.admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.store.LocalUser;


public class nav_header extends Fragment {

    private TextView USERNAME;
    private TextView CONTACTNO;


    public nav_header() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nav_header_admin, container, false);


        USERNAME = view.findViewById(R.id.username);
        CONTACTNO = view.findViewById(R.id.phoneNo);
        getUserRelatedData();
        return view;
    }
    @SuppressLint("SetTextI18n")
    private void getUserRelatedData() {
        String userName = LocalUser.getUsername();
        USERNAME.setText(userName);
        String PhoneNo = LocalUser.getPhoneNumber();
        CONTACTNO.setText(PhoneNo);
    }
}