package com.capstone.petgroomingapplication.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.customer.AddToCart;

import java.util.List;

public class ProductAdapter_admin extends RecyclerView.Adapter<ProductAdapter_admin.ProductViewHolder> {

    private final List<Product_class_customer> productList;

    public ProductAdapter_admin(List<Product_class_customer> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_customer, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product_class_customer product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice());
        holder.productImage.setImageResource(product.getImageResource());

        holder.productLinear.setOnClickListener(v -> {
            // Intent to start the AddToCart activity
            Intent intent = new Intent(v.getContext(), AddToCart.class);

            // Pass additional data like product information if needed
            intent.putExtra("productName", product.getName());
            intent.putExtra("productPrice", product.getPrice());
            intent.putExtra("productImageResource", product.getImageResource());

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public TextView productPrice;
        public ImageView productImage;
        public LinearLayout productLinear;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
            productLinear = itemView.findViewById(R.id.ProductLinear);
        }
    }
}
