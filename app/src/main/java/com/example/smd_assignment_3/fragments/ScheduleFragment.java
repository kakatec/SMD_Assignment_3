package com.example.smd_assignment_3.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.fragment.app.Fragment;

import com.example.smd_assignment_3.R;
import com.example.smd_assignment_3.database.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ScheduleFragment extends Fragment {

    EditText titleInput, descInput;
    TextView datetimeDisplay;
    Button pickDatetimeBtn, saveBtn;
    ListView upcomingTasksList;
    String selectedDatetime = "";
    DBHelper dbHelper;

    public ScheduleFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        dbHelper = new DBHelper(getContext());

        titleInput = view.findViewById(R.id.titleInput);
        descInput = view.findViewById(R.id.descInput);
        datetimeDisplay = view.findViewById(R.id.datetimeDisplay);
        pickDatetimeBtn = view.findViewById(R.id.pickDatetimeBtn);
        saveBtn = view.findViewById(R.id.saveBtn);
        upcomingTasksList = view.findViewById(R.id.upcomingTasksList);

        pickDatetimeBtn.setOnClickListener(v -> showDateTimePicker());

        saveBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String desc = descInput.getText().toString();
            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(selectedDatetime)) {
                Toast.makeText(getContext(), "Fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = dbHelper.addTask(title, desc, selectedDatetime, 0);
                if (inserted) {
                    Toast.makeText(getContext(), "Task saved", Toast.LENGTH_SHORT).show();
                    showUpcomingTasks();
                    titleInput.setText("");
                    descInput.setText("");
                    datetimeDisplay.setText("");
                } else {
                    Toast.makeText(getContext(), "Error saving task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showUpcomingTasks();
        return view;
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
            new TimePickerDialog(getContext(), (timeView, hour, minute) -> {
                selectedDatetime = String.format("%04d-%02d-%02d %02d:%02d:00", year, month+1, dayOfMonth, hour, minute);
                datetimeDisplay.setText(selectedDatetime);
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showUpcomingTasks() {
        ArrayList<String> tasks = new ArrayList<>();
        Cursor cursor = dbHelper.getUpcomingTasks();
        while (cursor.moveToNext()) {
            tasks.add(cursor.getString(1) + "\n" + cursor.getString(2) + "\n" + cursor.getString(3));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, tasks);
        upcomingTasksList.setAdapter(adapter);
    }
}
