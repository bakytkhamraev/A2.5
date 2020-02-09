package com.geektech.taskapp;

import android.widget.Toast;

public class Toaster {

    public void show(String message) {
        Toast.makeText(App.getInstance().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
