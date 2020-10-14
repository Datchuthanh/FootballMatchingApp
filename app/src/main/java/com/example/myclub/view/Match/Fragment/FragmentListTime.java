package com.example.myclub.view.Match.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.databinding.FragmentListTimeBinding;
import com.example.myclub.view.Match.Adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.viewModel.BookingFieldSession;

public class FragmentListTime extends Fragment {
    private BookingFieldSession viewModel = BookingFieldSession.getInstance();
    private FragmentListTimeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListTimeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       binding.recycleViewListTimeVertical.setLayoutManager(new LinearLayoutManager(getContext()));
        //Khởi tạo màn hình ban đầu của fragment
        RecycleViewAdapterListTimeVertical adapter = viewModel.getAdapterListTimeVertical();
        adapter.setFm(getParentFragmentManager());
        binding.recycleViewListTimeVertical.setAdapter(viewModel.getAdapterListTimeVertical());


    }
}
