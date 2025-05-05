package com.example.smd_assignment_3.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.smd_assignment_3.R;
import com.example.smd_assignment_3.database.DBHelper;

import java.util.ArrayList;

public class PastFragment extends Fragment {

    ListView pastTasksList;
    DBHelper dbHelper;

    public PastFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past, container, false);

        dbHelper = new DBHelper(getContext());
        pastTasksList = view.findViewById(R.id.pastTasksList);
        showPastTasks();

        return view;
    }

    private void showPastTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        Cursor cursor = dbHelper.getPastTasks();

        while (cursor.moveToNext()) {
            String title = cursor.getString(1);
            String desc = cursor.getString(2);

            // Convert timestamp to readable format
            long timestamp = cursor.getLong(3);
            String formattedDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
                    .format(new java.util.Date(timestamp));

            tasks.add(title + "\n" + desc + "\n" + formattedDate);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tasks);
        pastTasksList.setAdapter(adapter);
    }

}