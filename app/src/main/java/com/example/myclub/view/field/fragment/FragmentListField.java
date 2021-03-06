package com.example.myclub.view.field.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.enumeration.Status;
import com.example.myclub.databinding.FragmentListBinding;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListFieldVertical;
import com.example.myclub.viewModel.ListFieldViewModel;


public class FragmentListField extends Fragment {
    private ListFieldViewModel viewModel;
    public boolean isShown = true;

    public FragmentListField() {
    }

    public
    FragmentListField(Boolean isShown) {
        this.isShown = isShown;
    }

    FragmentListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ListFieldViewModel.class);
        RecycleViewAdapterListFieldVertical adapter = viewModel.getAdapterListField();
        adapter.setFm(getParentFragmentManager());
        adapter.isShow = this.isShown;
        binding.recycleViewListVertical.setAdapter(viewModel.getAdapterListField());
        binding.recycleViewListVertical.setLayoutManager(new LinearLayoutManager(getContext()));


        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.searchField(binding.txtSearch.getText().toString());

            }
        });

        viewModel.getTeamLoadState().observe(getViewLifecycleOwner(), new Observer<LoadingState>() {
            @Override
            public void onChanged(LoadingState loadingState) {
                if (loadingState == null) return;
                if (loadingState == LoadingState.INIT) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADING) {
                    binding.loadingLayout.setVisibility(View.VISIBLE);
                } else if (loadingState == LoadingState.LOADED) {
                    binding.loadingLayout.setVisibility(View.GONE);
                }
            }
        });


        viewModel.getStatusData().observe(getViewLifecycleOwner(), new Observer<Status>() {
            @Override
            public void onChanged(Status status) {

                if(status == Status.NO_DATA){
                    binding.viewNoData.setVisibility(View.VISIBLE);
                    binding.txtNodata.setText("Bạn chưa có đội bóng nào , hãy mau tạo đội !!!");
                }else if(status == status.EXIST_DATA){
                    binding.viewNoData.setVisibility(View.GONE);
                }
            }
        });

        binding.btnCreateTeam.setVisibility(View.GONE);
    }
}
