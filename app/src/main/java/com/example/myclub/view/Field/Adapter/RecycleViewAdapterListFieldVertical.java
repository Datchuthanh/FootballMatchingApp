package com.example.myclub.view.Field.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myclub.main.ActivityHome;
import com.example.myclub.model.Field;
import com.example.myclub.view.Field.Fragment.FragmentProfileField;
import com.example.myclub.databinding.ItemFieldVerticalBinding;
import com.example.myclub.viewModel.BookingFieldSession;

import java.util.ArrayList;
import java.util.List;


public class RecycleViewAdapterListFieldVertical extends RecyclerView.Adapter<RecycleViewAdapterListFieldVertical.MyViewHolder> {
    public Boolean isShow = false;
    private FragmentManager fm;
    private List<Field> fieldList = new ArrayList<>();
    private BookingFieldSession matchViewModel = BookingFieldSession.getInstance();
    public RecycleViewAdapterListFieldVertical() {

    }

    public RecycleViewAdapterListFieldVertical(FragmentManager fm) {
        this.fm = fm;
    }

    public void setFm(FragmentManager fm) {
        this.fm = fm;
    }

    public  void  setListField(List<Field> fieldList){
        this.fieldList = fieldList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemFieldVerticalBinding binding = ItemFieldVerticalBinding.inflate(inflater, parent, false);
        return new MyViewHolder(binding);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemFieldVerticalBinding binding;
        public MyViewHolder(ItemFieldVerticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow) {
                    ActivityHome activityHome = (ActivityHome) holder.itemView.getContext();
                    activityHome.addFragment(new FragmentProfileField());
                }else{
                    matchViewModel.setFieldLiveData(fieldList.get(position));
                    detach();
                }
            }
        });
        holder.binding.setField(fieldList.get(position));
        holder.binding.numberShirt.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }

    public void detach(){
        fm.popBackStack();
    }
}

