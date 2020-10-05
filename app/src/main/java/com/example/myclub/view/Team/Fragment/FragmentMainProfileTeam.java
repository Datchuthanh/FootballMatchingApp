package com.example.myclub.view.Team.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.animation.HorizontalFlipTransformation;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentProfileMyTeamBinding;
import com.example.myclub.databinding.FragmentProfileMyselfBinding;
import com.example.myclub.main.ActivityHome;
import com.example.myclub.view.Player.Adapter.AdapterFragmentProfile;
import com.example.myclub.view.Team.Adapter.AdapterFragmentProfileTeam;
import com.example.myclub.viewModel.PlayerViewModel;
import com.example.myclub.viewModel.TeamViewModel;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.File;

public class FragmentMainProfileTeam extends Fragment {


    FragmentProfileMyTeamBinding binding;
    private TeamViewModel teamViewModel = TeamViewModel.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileMyTeamBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
//        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());
        FragmentManager manager = getParentFragmentManager();
        AdapterFragmentProfileTeam adapter = new AdapterFragmentProfileTeam(getChildFragmentManager(), AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        observeLiveData(view.getContext());
        initComponent();




    }

    private  void initComponent(){

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHome activityHome = (ActivityHome) getContext();
                activityHome.addFragment(new FragmentMainEditTeam());
            }
        });
    }



    private void observeLiveData(final Context context) {
        // CreatePhoto
        PlayerViewModel.getInstance().getAvatarLiveData().observe(getViewLifecycleOwner(), new Observer<File>() {
            @Override
            public void onChanged(File file) {
                Picasso.get().load(file).into(binding.avatar);
            }
        });

        PlayerViewModel.getInstance().getCoverLiveData().observe(getViewLifecycleOwner(), new Observer<File>() {
            @Override
            public void onChanged(File file) {
                Picasso.get().load(file).into(binding.cover);
            }
        });

        teamViewModel.getResultPhotoLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    Picasso.get().load(teamViewModel.getAvatarLiveData().getValue()).into(binding.avatar);
                    Picasso.get().load(teamViewModel.getCoverLiveData().getValue()).into(binding.cover);

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, teamViewModel.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void detach() {
        getParentFragmentManager().popBackStack();
        getParentFragmentManager()
                .beginTransaction()
                .detach(this)
                .commit();
    }
}
