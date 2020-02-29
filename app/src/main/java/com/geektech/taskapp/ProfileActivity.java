package com.geektech.taskapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.PrimaryKey;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.zip.Inflater;

public class ProfileActivity extends AppCompatActivity {

    EditText editName;
    EditText editEmail;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        imageView = findViewById(R.id.imageView);
        //getDate();
        getDataListener();


    }

    private void getDataListener() {
        String userID = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(userID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (snapshot != null && snapshot.exists()) {
                    String name = snapshot.getString("name");
                    String email = snapshot.getString("email");
                    editName.setText(name);
                    editEmail.setText(email);
                    String avatarUrl = snapshot.getString("avatarUrl");
                    if (avatarUrl != null)
                        Glide.with(ProfileActivity.this).load(avatarUrl).into(imageView);
                }
            }
        });


    }

    private void getDate() {

        String userId = FirebaseAuth.getInstance().getUid();

        FirebaseFirestore.getInstance().collection("user")
                .document(userId)
                .get().
                addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            String name = task.getResult().getString("name");
                            editName.setText(name);
                            String avatarUrl = task.getResult().getString("avatarUrl");
                            if (avatarUrl != null)
                                Glide.with(ProfileActivity.this).load(avatarUrl).into(imageView);

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
                .document(userId).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void onClickImage(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 100 && data != null) {
            Uri uri = data.getData();

            imageView.setImageURI(uri);
            uploadImage(uri);

        }
    }

    private void uploadImage(Uri uri) {
        String userId = FirebaseAuth.getInstance().getUid();
        final StorageReference reference = FirebaseStorage.getInstance().getReference().child(userId + "jpeg").child("avatar.jpg");


        UploadTask uploadTask = reference.putFile(uri);
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return reference.getDownloadUrl();

            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful())
                    update(task.getResult());

                else
                    Toaster.show("Не успешно");

            }
        });
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

            }
        });


    }

    private void update(Uri result) {
        String userId = FirebaseAuth.getInstance().getUid();

        FirebaseFirestore.getInstance().collection("user").document(userId).
                update("avatarUrl", result.toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toaster.show("Упешно");
            }
        });


    }
}
