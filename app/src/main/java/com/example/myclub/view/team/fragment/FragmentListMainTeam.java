package com.example.myclub.view.team.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.myclub.R;
import com.example.myclub.databinding.FragmentMainListBinding;
import com.example.myclub.view.player.Adapter.AdapterFragmentProfile;
import com.example.myclub.view.team.adapter.AdapterFragmentListTeam;
import com.google.android.material.tabs.TabLayout;

public class FragmentListMainTeam extends Fragment {


    FragmentMainListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainListBinding.inflate(inflater);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewpager);
//        viewPager.setPageTransformer(true, new HorizontalFlipTransformation());
        FragmentManager manager = getParentFragmentManager();
        AdapterFragmentListTeam adapter = new AdapterFragmentListTeam(getChildFragmentManager(), AdapterFragmentProfile.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



    }
}
