package com.example.myclub.View.Field.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.R;
import com.example.myclub.View.Field.Adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.View.Team.Adapter.RecycleViewAdapterListTeamVertical;
import com.example.myclub.databinding.ActivityListFieldBinding;
import com.example.myclub.databinding.ActivityListTeamBinding;
import com.example.myclub.viewModel.ViewModelTodo;

public class ActivityListField extends AppCompatActivity {
    private ViewModelTodo viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListFieldBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_list_field);
       binding.recycleViewListFieldVertical.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewModel = new ViewModelProvider(this).get(ViewModelTodo.class);
        RecycleViewAdapterListFieldVertical adapter = viewModel.getAdapterListField();
        adapter.setFm(getSupportFragmentManager());
        binding.recycleViewListFieldVertical.setAdapter(viewModel.getAdapterListField());

    }
}
