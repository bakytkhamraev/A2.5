package com.geektech.taskapp.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Toaster;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneActivity extends AppCompatActivity {

    private EditText editPhone;
    private EditText editCode;

    private String verification;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        editPhone = findViewById(R.id.editPhone);
        editCode = findViewById(R.id.editCode);
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                Log.e("TAG", "onVerificationCompleted");
                signIn(phoneAuthCredential);


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Log.e("TAG", "onVerificationFailed");

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.e("TAG", "onCodeSent");

                verification = s;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Log.e("TAG", "onCodeAutoRetrievalTimeOut");
            }
        };
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(PhoneActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toaster.show("Ошибка" + task.getException().getMessage());

                }
            }
        });

    }

    public void onClick(View view) {

        LinearLayout number = findViewById(R.id.number);
        LinearLayout code = findViewById(R.id.code);
        String phone = editPhone.getText().toString().trim();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, callbacks);

        number.setVisibility(View.INVISIBLE);
        code.setVisibility(View.VISIBLE);

    }

    public void OnCode(View view) {

        String code= editCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)){
            return;
        }
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verification,code);
        signIn(phoneAuthCredential);
    }
    }

