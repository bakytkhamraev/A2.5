package com.geektech.taskapp.onboard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView);
        Button button = view.findViewById(R.id.startBtn);
        LinearLayout background = view.findViewById(R.id.background);
        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                textView.setText("Привет");
                imageView.setImageResource(R.drawable.image1);
                button.setVisibility(View.INVISIBLE);
                view.setBackgroundResource(R.drawable.gradiend1);
                break;
            case 1:
                textView.setText("Как дела?");
                imageView.setImageResource(R.drawable.image2);
                button.setVisibility(View.INVISIBLE);
                view.setBackgroundResource(R.drawable.gradient2);

                break;
            case 2:
                textView.setText("Че делаешь?");
                imageView.setImageResource(R.drawable.image3);
                button.setVisibility(View.VISIBLE);
                view.setBackgroundResource(R.drawable.gradient3);
                break;
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("setting", MODE_PRIVATE);
                preferences.edit().putBoolean("isShown", true).apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
    }
}
