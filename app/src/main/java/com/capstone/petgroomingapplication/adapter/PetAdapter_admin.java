package com.capstone.petgroomingapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.petgroomingapplication.R;

import java.util.List;

public class PetAdapter_admin extends RecyclerView.Adapter<PetAdapter_admin.PetViewHolder> {

    private final List<Pet_class_admin> pets;
   // private final OnPetClickListener onPetClickListener;

    public PetAdapter_admin(List<Pet_class_admin> pets) {
        this.pets = pets;
       // this.onPetClickListener = onPetClickListener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_petlist_admin, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet_class_admin pet = pets.get(position);
       // holder.bind(pet, onPetClickListener);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public interface OnPetClickListener {
        void onPetClick(Pet_class_admin pet);
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {

        private final TextView petName;
        private final TextView petBreed;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            petName = itemView.findViewById(R.id.petName);
            petBreed = itemView.findViewById(R.id.petBreed);
        }

        public void bind(Pet_class_admin pet, OnPetClickListener onPetClickListener) {
            petName.setText(pet.getName());
            petBreed.setText(pet.getBreed());
            itemView.setOnClickListener(v -> onPetClickListener.onPetClick(pet));
        }
    }
}
