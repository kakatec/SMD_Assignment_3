package com.example.smd_assignment_3.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.smd_assignment_3.R;
import com.example.smd_assignment_3.database.SharedPreferenceManager;


public class ProfileFragment extends Fragment {

    EditText nameInput, emailInput;
    Switch themeSwitch;
    Button saveBtn;
    SharedPreferenceManager prefs;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameInput = view.findViewById(R.id.nameInput);
        emailInput = view.findViewById(R.id.emailInput);
        themeSwitch = view.findViewById(R.id.themeSwitch);
        saveBtn = view.findViewById(R.id.saveBtn);

        prefs = new SharedPreferenceManager(getContext());

        // Load saved data
        nameInput.setText(prefs.getName());
        emailInput.setText(prefs.getEmail());
        themeSwitch.setChecked(prefs.isDarkMode());

        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.setDarkMode(isChecked);
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        saveBtn.setOnClickListener(v -> {
            String name = nameInput.getText().toString();
            String email = emailInput.getText().toString();
            prefs.saveUserInfo(name, email);
            Toast.makeText(getContext(), "Saved!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}