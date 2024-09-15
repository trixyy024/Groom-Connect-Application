package com.capstone.petgroomingapplication.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.petgroomingapplication.R;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

public class Appointment extends Fragment {

    private MaterialCalendarView calendarView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointment_admin, container, false);
/**
        calendarView = view.findViewById(R.id.calendarView);

        if (calendarView.isInEditMode()) {
            // Provide sample data when in edit mode
            Set<CalendarDay> sampleDays = new HashSet<>();
            sampleDays.add(CalendarDay.from(2024, 8, 15));
            sampleDays.add(CalendarDay.from(2024, 8, 20));
            calendarView.addDecorator(new EventDecorator(sampleDays));
        } else {
            // Normal behavior
            DatabaseReference databaseAppointments = FirebaseDatabase.getInstance().getReference("appointments");

            databaseAppointments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Set<CalendarDay> appointmentDays = new HashSet<>();
                    for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                        Appointment appointment = appointmentSnapshot.getValue(Appointment.class);
                        if (appointment != null) {
                            // Convert appointment date to CalendarDay and add to the set
                            CalendarDay day = convertDateToCalendarDay(appointment.date);
                            appointmentDays.add(day);
                        }
                    }

                    // Clear previous decorators before adding new ones
                    calendarView.removeDecorators();
                    calendarView.addDecorator(new EventDecorator(appointmentDays));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Failed to load appointments.", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return view;
    }

    private CalendarDay convertDateToCalendarDay(String date) {
        // Adjust this method based on your date format
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]) - 1; // Calendar months are 0-based
        int day = Integer.parseInt(parts[2]);
        return CalendarDay.from(year, month, day);
    }

    private class EventDecorator implements DayViewDecorator {
        private final Set<CalendarDay> dates;

        public EventDecorator(Set<CalendarDay> dates) {
            this.dates = dates;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }**/

        return view;
    }}