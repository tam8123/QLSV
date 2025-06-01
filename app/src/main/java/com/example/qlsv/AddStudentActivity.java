package com.example.qlsv;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class AddStudentActivity extends AppCompatActivity {

    EditText editTextName, editTextMSSV;
    Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextName = findViewById(R.id.editTextName);
        editTextMSSV = findViewById(R.id.editTextMSSV);
        buttonSave = findViewById(R.id.buttonSave);

        buttonSave.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String mssv = editTextMSSV.getText().toString().trim();

            if (!name.isEmpty() && !mssv.isEmpty()) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("student_data", name + " - " + mssv);
                setResult(RESULT_OK, resultIntent);
                finish();
            } else {
                editTextName.setError("Nhập họ tên");
                editTextMSSV.setError("Nhập MSSV");
            }
        });
    }
}

