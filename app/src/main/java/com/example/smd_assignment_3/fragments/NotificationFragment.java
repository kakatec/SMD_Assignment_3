package com.example.smd_assignment_3.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.fragment.app.Fragment;

import com.example.smd_assignment_3.R;
import com.example.smd_assignment_3.database.DBHelper;
import java.text.SimpleDateFormat;
import java.util.*;

public class NotificationFragment extends Fragment {

    ListView notificationList;
    DBHelper dbHelper;

    public NotificationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        notificationList = view.findViewById(R.id.notificationList);
        dbHelper = new DBHelper(getContext());

        // Add dummy notifications
        addDummyNotifications();

        // Show from DB
        showNotifications();

        return view;
    }

    private void addDummyNotifications() {
        String[] messages = {"Meeting at 3PM", "Don't forget to submit report", "App update available"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String now = sdf.format(new Date());

        for (String msg : messages) {
            dbHelper.insertNotification(msg, now); // Insert with current time
        }
    }

    private void showNotifications() {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = dbHelper.getAllNotifications();

        while (cursor.moveToNext()) {
            String msg = cursor.getString(1);
            String datetime = cursor.getString(2);
            list.add(msg + "\n" + datetime);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
        notificationList.setAdapter(adapter);
    }
}