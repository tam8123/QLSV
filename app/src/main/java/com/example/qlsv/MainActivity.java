package com.example.qlsv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtMSSV;
    Button btnAdd, btnUpdate, btnDelete;
    ListView listView;
    ArrayList<String> studentList;
    ArrayAdapter<String> adapter;
    int selectedIndex = -1;

    public static final int REQUEST_ADD = 100;
    public static final int REQUEST_UPDATE = 200;

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
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });

        btnUpdate.setOnClickListener(view -> {
            if (selectedIndex != -1) {
                Intent intent = new Intent(MainActivity.this, UpdateStudentActivity.class);
                intent.putExtra("student_data", studentList.get(selectedIndex));
                intent.putExtra("student_index", selectedIndex);
                startActivityForResult(intent, REQUEST_UPDATE);
            } else {
                showToast("Vui lòng chọn sinh viên để cập nhật");
            }
        });

        btnDelete.setOnClickListener(view -> {
            if (selectedIndex != -1) {
                showDeleteConfirmationDialog();
            } else {
                showToast("Vui lòng chọn sinh viên để xóa");
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
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?");
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            studentList.remove(selectedIndex);
            adapter.notifyDataSetChanged();
            clearInput();
            selectedIndex = -1;
            showToast("Xóa thành công");
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void clearInput() {
        edtName.setText("");
        edtMSSV.setText("");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD) {
                String studentData = data.getStringExtra("student_data");
                if (studentData != null) {
                    studentList.add(studentData);
                    adapter.notifyDataSetChanged();
                    showToast("Thêm sinh viên thành công");
                }
            } else if (requestCode == REQUEST_UPDATE) {
                String updatedData = data.getStringExtra("updated_data");
                int index = data.getIntExtra("student_index", -1);
                if (updatedData != null && index != -1) {
                    studentList.set(index, updatedData);
                    adapter.notifyDataSetChanged();
                    showToast("Cập nhật sinh viên thành công");
                    selectedIndex = -1;
                    clearInput();
                }
            }
        }
    }
}
