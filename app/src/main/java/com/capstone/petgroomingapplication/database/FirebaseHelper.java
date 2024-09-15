package com.capstone.petgroomingapplication.database;

import com.capstone.petgroomingapplication.admin.ServiceClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {

    private DatabaseReference databaseReference;

    public FirebaseHelper() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("services");
    }

    public interface DataFetchListener {
        void onDataFetched(List<ServiceClass> services);
        void onError(Exception e);
    }

    public void addService(ServiceClass service) {
        databaseReference.push().setValue(service);
    }

    public void fetchData(final DataFetchListener listener) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ServiceClass> services = new ArrayList<>();
                for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                    ServiceClass service = serviceSnapshot.getValue(ServiceClass.class);
                    services.add(service);
                }
                listener.onDataFetched(services);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError(databaseError.toException());
            }
        });
    }
}
