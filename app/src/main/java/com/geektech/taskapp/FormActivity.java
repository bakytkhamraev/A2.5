package com.geektech.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormActivity extends AppCompatActivity {
    private EditText editTitle;
    private EditText editDesc;
    Button button, buttonTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        button=findViewById(R.id.oneBtn);
        buttonTwo=findViewById(R.id.TwoBtn);

        button.setEnabled(false);
        buttonTwo.setEnabled(false);
    }


    public void onClick(View view) {
        String title = editTitle.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();

        if (editDesc.getText().toString().equals("") || editTitle.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Заполните данные...", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Intent intent = new Intent();
            Task task = new Task(title, desc);

            App.getDatabase().taskDao().insert(task);
            intent.putExtra("task", task);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}
