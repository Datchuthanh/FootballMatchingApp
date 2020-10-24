package com.example.myclub.view.match.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.databinding.ItemMatchVerticalBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Match;
import com.example.myclub.view.match.fragment.FragmentMainProfileMatch;
import com.example.myclub.viewModel.ListMatchViewModel;

import java.util.ArrayList;
import java.util.List;

//import com.example.managefield.view.Fragment.FragmentMainProfileMatch;


public class RecycleViewAdapterListMatchVertical extends RecyclerView.Adapter<RecycleViewAdapterListMatchVertical.MyViewHolder> {
    private FragmentManager fm;
    private List<Match> matches = new ArrayList<>();
    private ListMatchViewModel listMatchViewModel;

    public RecycleViewAdapterListMatchVertical() {
    }

    public RecycleViewAdapterListMatchVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setListBookingViewModel(ListMatchViewModel listMatchViewModel) {
        this.listMatchViewModel = listMatchViewModel;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListMatch(List<Match> listMatch){
        this.matches = listMatch;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemMatchVerticalBinding binding = ItemMatchVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemMatchVerticalBinding binding;
        public MyViewHolder(ItemMatchVerticalBinding binding) {
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
                activityHome.addFragment(new FragmentMainProfileMatch(matches.get(position).getId()));
            }
        });


        holder.binding.setMatch(matches.get(position));
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }
}
