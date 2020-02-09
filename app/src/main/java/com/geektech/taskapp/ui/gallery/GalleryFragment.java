package com.geektech.taskapp.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.geektech.taskapp.R;

import javax.microedition.khronos.opengles.GL;

public class GalleryFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        ImageView imageView=root.findViewById(R.id.imageView);

        Glide.with(getContext()).load("https://github.com/bumptech/glide/raw/master/static/glide_logo.png").into(imageView);


        return root;
    }
}