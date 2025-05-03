package com.example.qlsv;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtMSSV;
    Button btnAdd, btnUpdate, btnDelete;
    ListView listView;
    ArrayList<String> studentList;
    ArrayAdapter<String> adapter;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edtName);
        edtMSSV = findViewById(R.id.edtMSSV);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        listView = findViewById(R.id.listView);

        studentList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, studentList);
        listView.setAdapter(adapter);

        btnAdd.setOnClickListener(view -> {
            String name = edtName.getText().toString();
            String mssv = edtMSSV.getText().toString();
            if (!name.isEmpty() && !mssv.isEmpty()) {
                studentList.add(name + " - " + mssv);
                adapter.notifyDataSetChanged();
                clearInput();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            String selectedItem = studentList.get(position);
            String[] parts = selectedItem.split(" - ");
            if (parts.length == 2) {
                edtName.setText(parts[0]);
                edtMSSV.setText(parts[1]);
            }
        });

        btnUpdate.setOnClickListener(view -> {
            if (selectedIndex != -1) {
                String name = edtName.getText().toString();
                String mssv = edtMSSV.getText().toString();
                if (!name.isEmpty() && !mssv.isEmpty()) {
                    studentList.set(selectedIndex, name + " - " + mssv);
                    adapter.notifyDataSetChanged();
                    clearInput();
                    selectedIndex = -1;
                }
            }
        });

        btnDelete.setOnClickListener(view -> {
            if (selectedIndex != -1) {
                studentList.remove(selectedIndex);
                adapter.notifyDataSetChanged();
                clearInput();
                selectedIndex = -1;
            }
        });
    }

    private void clearInput() {
        edtName.setText("");
        edtMSSV.setText("");
    }
}