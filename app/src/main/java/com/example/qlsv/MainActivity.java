package com.example.qlsv;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

        // Gọi dialog thêm sinh viên
        btnAdd.setOnClickListener(view -> showStudentDialog(false));

        // Gọi dialog cập nhật sinh viên nếu đã chọn
        btnUpdate.setOnClickListener(view -> {
            if (selectedIndex != -1) {
                showStudentDialog(true);
            } else {
                showToast("Vui lòng chọn sinh viên để cập nhật");
            }
        });

        // Gọi dialog xác nhận xóa sinh viên
        btnDelete.setOnClickListener(view -> {
            if (selectedIndex != -1) {
                showDeleteConfirmationDialog();
            } else {
                showToast("Vui lòng chọn sinh viên để xóa");
            }
        });

        // Gán sự kiện click item trong ListView để cập nhật input
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

    // Hiển thị dialog thêm/cập nhật sinh viên
    private void showStudentDialog(boolean isUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isUpdate ? "Cập nhật sinh viên" : "Thêm sinh viên");

        final EditText inputName = new EditText(this);
        final EditText inputMSSV = new EditText(this);
        inputName.setHint("Họ tên");
        inputMSSV.setHint("MSSV");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 40, 50, 10);
        layout.addView(inputName);
        layout.addView(inputMSSV);

        if (isUpdate && selectedIndex != -1) {
            String[] parts = studentList.get(selectedIndex).split(" - ");
            if (parts.length == 2) {
                inputName.setText(parts[0]);
                inputMSSV.setText(parts[1]);
            }
        }

        builder.setView(layout);

        builder.setPositiveButton(isUpdate ? "Cập nhật" : "Thêm", (dialog, which) -> {
            String name = inputName.getText().toString();
            String mssv = inputMSSV.getText().toString();
            if (!name.isEmpty() && !mssv.isEmpty()) {
                if (isUpdate && selectedIndex != -1) {
                    studentList.set(selectedIndex, name + " - " + mssv);
                    selectedIndex = -1;
                    showToast("Cập nhật thành công");
                } else {
                    studentList.add(name + " - " + mssv);
                    showToast("Thêm thành công");
                }
                adapter.notifyDataSetChanged();
                clearInput();
            } else {
                showToast("Vui lòng nhập đầy đủ thông tin");
            }
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    // Hiển thị dialog xác nhận xóa
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

    // Xóa nội dung input
    private void clearInput() {
        edtName.setText("");
        edtMSSV.setText("");
    }

    // Hiển thị Toast thông báo
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}