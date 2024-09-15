package com.capstone.petgroomingapplication.customer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.petgroomingapplication.R;
import com.capstone.petgroomingapplication.adapter.FAQAdapter;
import com.capstone.petgroomingapplication.adapter.Faq;

import java.util.Arrays;
import java.util.List;

public class FaqsFragment extends Fragment {

    private RecyclerView recyclerView;
    private FAQAdapter adapter;
    private List<Faq> faqList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faqs, container, false);
        recyclerView = view.findViewById(R.id.faqRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize your FAQ list and adapter here
        faqList = getFaqList(); // This method should return your list of FAQs
        adapter = new FAQAdapter(faqList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Faq> getFaqList() {
        return Arrays.asList(
                new Faq("What services do you offer?", "The Charming Paws offers full grooming services, including bath, fur cut, ear cleaning, nail cutting, and anal sac draining. We also offer partial grooming services, like nail cutting and ear cleaning."),
                new Faq("How much do your grooming services cost?", "Pricing varies by the size of your dog: small dogs start at P600, medium dogs range from P650 to P750, and large dogs start at P750 up to P950. Partial grooming services cost between P150 to P200."),
                new Faq("Do you groom other pets besides dogs?", "Yes, while our main focus is on dogs, we occasionally offer partial grooming for cats, including nail cutting, ear cleaning, and bath & dry sessions."),
                new Faq("How do I book an appointment?","You can book an appointment directly through our mobile app, available for Android devices. The app allows you to select your preferred date and service."),
                new Faq ("Can I pay online for grooming services?", "Yes, we offer cashless payments through GCash. You can pay directly through our app during or after your appointment booking."),
                new Faq("How do you handle cancellations?", "Cancellations must be made at least 24 hours in advance to avoid charges. If you cancel last minute, we may not be able to reschedule your appointment promptly due to high demand."),
                new Faq("What if I can’t find your location?", "We offer real-time location tracking through our app, which helps groomers locate your exact address. Ensure location sharing is enabled when booking your appointment."),
                new Faq("Can I communicate with the groomer directly?", "Yes, our app includes a real-time messaging feature that allows you to communicate directly with your assigned groomer for updates or special requests."),
                new Faq("Do you offer any discounts or promotions?", "Currently, we do not have a loyalty program or promotions, but keep an eye on our app notifications for any future offers."),
                new Faq("What should I do if my pet has special grooming needs?", "You can add any special requests or grooming preferences in the app when booking your appointment. Our groomers will review these notes to ensure your pet receives personalized care."),
                new Faq( "Can I update my contact or pet’s information?", "Yes, you can easily update your contact details, pet information, and preferences directly through the update module in our app."),
                new Faq( "Do you provide services outside your listed areas?", "Our primary service areas include Daraga, Legazpi, and nearby towns. If you’re located outside these areas, please contact us through the app to check availability."),
                new Faq("How can I view my pet’s grooming history?",  "Your pet’s grooming history, including past services, notes from the groomer, and any special requests, can be viewed in the pet profile section of the app."),
                new Faq("What products do you use during grooming?","We use MDC Dog Shampoo, MDC Healing Ointment, and other high-quality pet care products to ensure your pet’s safety and comfort during grooming."),
                new Faq( "What is your policy for aggressive or anxious pets?", "We handle each pet with care and patience. If your pet has behavioral concerns, please inform us ahead of time so we can prepare accordingly.")

                );
        }
    }