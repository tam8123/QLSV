package com.example.qlsv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText editTextName, editTextMSSV;
    Button buttonSave;
    int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student); // Dùng lại layout thêm

        editTextName = findViewById(R.id.editTextName);
        editTextMSSV = findViewById(R.id.editTextMSSV);
        buttonSave = findViewById(R.id.buttonSave);

        Intent intent = getIntent();
        String studentData = intent.getStringExtra("student_data");
        index = intent.getIntExtra("student_index", -1);

        if (studentData != null) {
            String[] parts = studentData.split(" - ");
            if (parts.length == 2) {
                editTextName.setText(parts[0]);
                editTextMSSV.setText(parts[1]);
            }
        }

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String mssv = editTextMSSV.getText().toString().trim();

            if (!name.isEmpty() && !mssv.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("updated_data", name + " - " + mssv);
                resultIntent.putExtra("student_index", index);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                editTextName.setError("Nhập họ tên");
                editTextMSSV.setError("Nhập MSSV");
            }
        });
    }
}