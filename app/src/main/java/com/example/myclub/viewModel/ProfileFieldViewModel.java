package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.data.enumeration.LoadingState;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.model.Field;
import com.example.myclub.model.Match;
import com.example.myclub.model.TimeGame;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeHorizontal;
import com.example.myclub.view.field.adapter.RecycleViewAdapterListTimeVertical;
import com.example.myclub.view.match.adapter.RecycleViewAdapterListMatchVertical;

import java.util.ArrayList;
import java.util.List;

public class ProfileFieldViewModel extends ViewModel {
    private MutableLiveData<Field> fieldLiveData = new MutableLiveData<>();
    private RecycleViewAdapterListTimeHorizontal adapterListTimeVertical = new RecycleViewAdapterListTimeHorizontal();
    private RecycleViewAdapterListMatchVertical adapterListMatchVertical = new RecycleViewAdapterListMatchVertical();
    private FieldRepository fieldRepository = FieldRepository.getInstance();
    public void setField(Field field) {
        fieldLiveData.setValue(field);
    }
    private MutableLiveData<LoadingState> matchLoadState = new MutableLiveData<>(LoadingState.INIT);
    public LiveData<Field> getFieldLiveData(){
        return fieldLiveData;
    }

    public void getlistTime(){
        matchLoadState.setValue(LoadingState.INIT);
        fieldRepository.getListTimeByField(fieldLiveData.getValue().getId(), new CallBack<List<TimeGame>, String>() {
            @Override
            public void onSuccess(List<TimeGame> listTimeGames) {
                if(listTimeGames == null){
                    matchLoadState.setValue(LoadingState.LOADED);
                    adapterListTimeVertical.setListTime(new ArrayList<TimeGame>());
                    adapterListTimeVertical.notifyDataSetChanged();
                }else {
                    matchLoadState.setValue(LoadingState.LOADED);
                    adapterListTimeVertical.setListTime(listTimeGames);
                    adapterListTimeVertical.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(String message) {
                matchLoadState.setValue(LoadingState.ERROR);
            }
        });
    }

    public RecycleViewAdapterListTimeHorizontal getAdapterListTimeVertical() {
        return adapterListTimeVertical;
    }

    public void  getListMatch(){
        fieldRepository.getListMatch(fieldLiveData.getValue().getId(),new CallBack<List<Match>, String>() {
            @Override
            public void onSuccess(List<Match> matchList) {
                if(matchList == null){
                    adapterListMatchVertical.setListMatch(new ArrayList<Match>());
                    adapterListMatchVertical.notifyDataSetChanged();
                }else{
                    adapterListMatchVertical.setListMatch(matchList);
                    adapterListMatchVertical.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(String message) {

            }
        });
    }


    public RecycleViewAdapterListMatchVertical getAdapterListMatch() {
        return adapterListMatchVertical;
    }


    public MutableLiveData<LoadingState> getMatchLoadState() {
        return matchLoadState;
    }

}
