package com.example.myclub.view.team.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentEditTeamBasicBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.model.Team;
import com.example.myclub.session.SessionTeam;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentEditTeamBasic extends BottomSheetDialogFragment {


    private FragmentEditTeamBasicBinding binding;
    private SessionTeam sessionTeam = SessionTeam.getInstance();
    private Dialog loadingDialog;
    private LoadingLayoutBinding loadingLayoutBinding;
    private    Map<String, Object> data = new HashMap<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditTeamBasicBinding.inflate(inflater);
        View view = binding.getRoot();
        return  view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view.getContext());
        observeLiveData(view.getContext());
    }

    private void detach(){
        dismiss();
    }



    private  void initComponent(final Context context){
        binding.imageBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoadingDialog(context);
                loadingDialog.show();
                sessionTeam.updateProfile(getUpdateBasic());
            }
        });
    }

    private void initLoadingDialog(Context context) {
        loadingDialog = new Dialog(context);
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loadingLayoutBinding = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog.setContentView(loadingLayoutBinding.getRoot());
//        loadingLayoutBinding.title.setText(R.string.updating_information);
        loadingDialog.setCancelable(false);
    }

    private void observeLiveData(final Context context) {
        sessionTeam.getResultLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    sessionTeam.resetResult();
                    loadingDialog.dismiss();
                    detach();
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                    updateUITeam();

                } else if (result == Result.FAILURE) {
                    sessionTeam.resetResult();
                    loadingDialog.dismiss();
                    detach();
                    Toast.makeText(context, sessionTeam.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private Map<String, Object> getUpdateBasic() {
        data.put("email", binding.txtEmail.getText().toString());
        data.put("phone", binding.txtPhone.getText().toString());
        data.put("name", binding.txtName.getText().toString());
        data.put("address", binding.txtAddress.getText().toString());
        return data;
    }

    private  void updateUITeam (){
        Team team = SessionTeam.getInstance().getTeamLiveData().getValue();
        team.setInforBasic(data);
        SessionTeam.getInstance().setTeamLiveData(team);
    }
}
