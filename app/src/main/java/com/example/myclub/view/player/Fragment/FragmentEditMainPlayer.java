package com.example.myclub.view.player.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.myclub.R;
import com.example.myclub.auth.ActivityLogin;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.databinding.FragmentEditMainPlayerBinding;
import com.example.myclub.databinding.LoadingLayoutBinding;
import com.example.myclub.session.SessionUser;
import com.facebook.login.LoginManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FragmentEditMainPlayer extends Fragment {
    private FragmentEditMainPlayerBinding binding;
    public static final int RESULT_LOAD_IMG_AVATAR = 1012;
    public static final int RESULT_LOAD_IMG_COVER = 1013;
    private  String urlAvatar , urlCover;
    private SessionUser session = SessionUser.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditMainPlayerBinding.inflate(inflater);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         initComponent(view.getContext());
        observeLiveData(view.getContext());
    }

    private  void initComponent(final Context context){
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_back_white_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detach();
            }
        });

        binding.btnEditAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG_AVATAR);


            }
        });

        binding.btnEditCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG_COVER);

            }
        });

        binding.btnEditBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment dialog = new FragmentEditPlayerBasic();
                dialog.show(getParentFragmentManager(), null);

            }
        });

        binding.btnEditPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment dialog = new FragmentEditPlayerPlayer();
                dialog.show(getParentFragmentManager(), null);

            }
        });



        binding.btnEditIntroduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogFragment dialog = new FragmentEditPlayerIntroduce();
                dialog.show(getParentFragmentManager(), null);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                getActivity().finish();
            }
        });
    }

    private void detach(){
        getParentFragmentManager().popBackStack();
    }



    private void observeLiveData(final Context context) {
        //init Photo
        SessionUser.getInstance().getAvatarLiveData().observe(getViewLifecycleOwner(), new Observer<File>() {
            @Override
            public void onChanged(File file) {
                Picasso.get().load(file).into(binding.avatar);
            }
        });

        SessionUser.getInstance().getCoverLiveData().observe(getViewLifecycleOwner(), new Observer<File>() {
            @Override
            public void onChanged(File file) {
                Picasso.get().load(file).into(binding.cover);
            }
        });

        //update Photo
        session.getResultPhotoLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    Picasso.get().load(session.getAvatarLiveData().getValue()).into(binding.avatar);
                    Picasso.get().load(session.getCoverLiveData().getValue()).into(binding.cover);

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, session.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        session.getResultPhotoLiveData().observe(getViewLifecycleOwner(), new Observer<Result>() {
            @Override
            public void onChanged(Result result) {
                if (result == null) return;
                if (result == Result.SUCCESS) {
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();

                } else if (result == Result.FAILURE) {
                    Toast.makeText(context, session.getResultMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == RESULT_LOAD_IMG_AVATAR || requestCode == RESULT_LOAD_IMG_COVER) && resultCode == Activity.RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Cursor returnCursor =
                        getContext().getContentResolver().query(imageUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();

                if(requestCode == RESULT_LOAD_IMG_AVATAR){
                    binding.avatar.setImageBitmap(selectedImage);
                    urlAvatar = returnCursor.getString(nameIndex);
                    updateImage(imageUri,urlAvatar,true);
                }else if(requestCode == RESULT_LOAD_IMG_COVER){
                    binding.cover.setImageBitmap(selectedImage) ;
                    urlCover =  returnCursor.getString(nameIndex);
                    updateImage(imageUri,urlCover,false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getContext(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    private void updateImage(Uri uri, String path , boolean isAvatar) {
        session.updateImage(uri, path,isAvatar);
    }




}
