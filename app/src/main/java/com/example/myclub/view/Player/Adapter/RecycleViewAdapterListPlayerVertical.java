package com.example.myclub.view.Player.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Todo;
import com.example.myclub.view.Player.Fragment.FragmentProfilePlayer;
import com.example.myclub.databinding.ItemPlayerVerticalBinding;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListPlayerVertical extends RecyclerView.Adapter<RecycleViewAdapterListPlayerVertical.MyViewHolder> {

    private FragmentManager fm;
    private List<Todo> todos = new ArrayList<>();
    public RecycleViewAdapterListPlayerVertical() {

    }

    public RecycleViewAdapterListPlayerVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListTodo(List<Todo> todos){
        this.todos = todos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPlayerVerticalBinding binding = ItemPlayerVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemPlayerVerticalBinding binding;
        public MyViewHolder(ItemPlayerVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                activityHome.addFragment(new FragmentProfilePlayer());
            }
        });
        holder.binding.setTodo(todos.get(position));
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}
