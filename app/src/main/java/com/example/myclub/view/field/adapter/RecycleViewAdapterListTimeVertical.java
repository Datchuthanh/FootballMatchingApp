package com.example.myclub.view.field.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemTimeHorizontalBinding;
import com.example.myclub.databinding.ItemTimeVerticalBinding;
import com.example.myclub.model.TimeGame;
import com.example.myclub.session.SessionBookingField;


import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListTimeVertical extends RecyclerView.Adapter<RecycleViewAdapterListTimeVertical.MyViewHolder> {

    private FragmentManager fm;
    private List<TimeGame> listTimes = new ArrayList<>();

    public RecycleViewAdapterListTimeVertical() {

    }

    public RecycleViewAdapterListTimeVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListTime(List<TimeGame> listTime){
        this.listTimes = listTime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTimeVerticalBinding binding = ItemTimeVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemTimeVerticalBinding binding;
        public MyViewHolder(ItemTimeVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionBookingField.getInstance().setTimeLiveData(listTimes.get(position));
                detach();
            }
        });
        holder.binding.setTimeGame(listTimes.get(position));
    }

    @Override
    public int getItemCount() {
        return listTimes.size();
    }

    public void detach() {
        fm.popBackStack();
    }
}


