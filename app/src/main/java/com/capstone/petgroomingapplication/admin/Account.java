package com.capstone.petgroomingapplication.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.model.UserModel;
import com.capstone.petgroomingapplication.store.LocalUser;
import com.capstone.petgroomingapplication.utils.AndroidUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Account extends Fragment {

    private TextView editInfoButton;
    private ImageView profilePic;
    private ActivityResultLauncher<Intent> imagePickLauncher;
    private Uri selectedImageUri;
    private TextView userName;
    private TextView userRole;
    private TextView contactNo;
    private TextView address;



    public Account() {// Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            selectedImageUri = data.getData();
                            AndroidUtils.setProfilePic(getContext(), selectedImageUri, profilePic);
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_admin, container, false);

        profilePic = view.findViewById(R.id.profilePic);
        editInfoButton = view.findViewById(R.id.btnEditInfo);
        userName = view.findViewById(R.id.userNameAcc);
        userRole = view.findViewById(R.id.userRoleCustomer);
        contactNo = view.findViewById(R.id.ContactNumber);
        address = view.findViewById(R.id.Address);


        setupListeners();
        getUserRelatedData();

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void getUserRelatedData() {
        String Username = LocalUser.getUsername();
        userName.setText(Username);
        String Userrole = LocalUser.getCollection();
        userRole.setText(Userrole);
        String ContactNo = LocalUser.getContactNo();
        contactNo.setText(ContactNo);
        String Address = LocalUser.getAddress();
        address.setText(Address);




    }

    private void setupListeners() {

        editInfoButton.setOnClickListener(v -> {
           // Toast.makeText(getActivity(), "Successfully saved", Toast.LENGTH_SHORT).show();
        });

        profilePic.setOnClickListener((v) -> {
            ImagePicker.with(this).cropSquare().compress(512).maxResultSize(512, 512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });
    }
}
