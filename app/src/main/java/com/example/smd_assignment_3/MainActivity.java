package com.example.smd_assignment_3;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager2.widget.ViewPager2;

import com.example.smd_assignment_3.adapters.ViewPagerAdapter;
import com.example.smd_assignment_3.fragments.NotificationFragment;
import com.example.smd_assignment_3.fragments.PastFragment;
import com.example.smd_assignment_3.fragments.ProfileFragment;
import com.example.smd_assignment_3.fragments.ScheduleFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply dark or light mode before view loads
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("dark_mode", false);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        adapter = new ViewPagerAdapter(this);
        adapter.addFragment(new ScheduleFragment());
        adapter.addFragment(new PastFragment());
        adapter.addFragment(new NotificationFragment());
        adapter.addFragment(new ProfileFragment());

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0: tab.setText("Schedule"); break;
                case 1: tab.setText("Past"); break;
                case 2: tab.setText("Notification"); break;
                case 3: tab.setText("Profile"); break;
            }
        }).attach();
    }
}