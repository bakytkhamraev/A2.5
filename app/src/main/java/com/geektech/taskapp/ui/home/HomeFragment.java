package com.geektech.taskapp.ui.home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.App;
import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.OnItemClickListener;
import com.geektech.taskapp.OnItemLongListener;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    private TaskAdapter adapter;
    private List<Task> list;


    private Task task;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        list=new ArrayList<>();
        App.getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();
            }
        });



        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);










        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onOItemClick(int position) {
                Toast.makeText(getContext(), "pos=" + list.get(position)
                        .getTitle(), Toast.LENGTH_SHORT).show();
            }
        });


        adapter.setOnItemLongListener(new OnItemLongListener() {
            @Override
            public void onItemLong( final int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
                dialog.setTitle("Вы хотите удалить запись?")
                        .setMessage("Удалить задачу")
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int which) {
                                dialoginterface.cancel();
                            }
                        }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int which) {
                        App.getDatabase().taskDao().delete(list.get(position));
                    }
                }).show();


            }
        });

        return root;









    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            Task task = (Task) data.getSerializableExtra("task");

            list.add(task);
            adapter.notifyDataSetChanged();
        }
    }
}