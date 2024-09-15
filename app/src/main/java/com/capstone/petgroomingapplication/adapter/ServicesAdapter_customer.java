package com.capstone.petgroomingapplication.adapter;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.customer.Book_now;

import java.util.List;


public class ServicesAdapter_customer extends RecyclerView.Adapter<ServicesAdapter_customer.ProductViewHolder> {



    private List<Services_class_customer> servicesList;
    private Context context;

    public ServicesAdapter_customer(List<Services_class_customer> servicesList, Context context) { // Add context as a parameter
        this.servicesList = servicesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_services_list_customer, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Services_class_customer services = servicesList.get(position);
        holder.servicesDescription.setText(services.getName());
        holder.sPrice.setText(services.getPrice());
        holder.servicesImage.setImageResource(services.getImageResource());

        holder.btnBookNow.setOnClickListener(v -> {
            // Handle Buy Now button click
            Log.d("ServicesAdapter", "Book Now button clicked for: " + services.getName());

            Intent intent = new Intent(context, Book_now.class); // Use context here
            intent.putExtra("service_name", services.getName());
            intent.putExtra("service_price", services.getPrice());
            context.startActivity(intent);
            // Start the activity

            Toast.makeText(v.getContext(), "Book now: " + services.getName(), Toast.LENGTH_SHORT).show();

        });

    }

    @Override
    public int getItemCount() {
        return servicesList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView servicesDescription;
        public TextView sPrice;
        public ImageView servicesImage;
        public Button btnBookNow;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            servicesDescription = itemView.findViewById(R.id.serviceDescription);
            sPrice = itemView.findViewById(R.id.sPrice);
            servicesImage = itemView.findViewById(R.id.servicesImage);
            btnBookNow = itemView.findViewById(R.id.btnBookNow);
        }
    }
}
