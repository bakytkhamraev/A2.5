package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    EditText editName;
    EditText editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        getDate();
        getDateListener();


    }

    private void getDateListener(){


    }
    private void getDate() {

        String userId=FirebaseAuth.getInstance().getUid();

        FirebaseFirestore.getInstance().collection("user")
                .document(userId)
                .get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            String name = task.getResult().getString("name");
                            editName.setText(name);

                        }
                    }
                });
    }

    public void onClick(View view) {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);

        String userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(userId).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toaster.show("Успешно");

                } else {
                    Toaster.show("Ошибка" + task.getException().getMessage());
                }

            }
        });


    }
}
