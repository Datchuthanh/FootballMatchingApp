package com.example.myclub.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.LoadListOtherPlayerCallBack;
import com.example.myclub.Interface.LoadListOtherTeamCallBack;
import com.example.myclub.Interface.LoadListPlayerCallBack;
import com.example.myclub.Interface.LoadListPlayerRequestCallBack;
import com.example.myclub.Interface.LoadListTeamCallBack;
import com.example.myclub.Interface.RegisterTeamCallBack;
import com.example.myclub.data.enumeration.Result;
import com.example.myclub.data.repository.PlayerRepository;
import com.example.myclub.model.Player;
import com.example.myclub.model.Team;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListPlayerRequestVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListPlayerVertical;
import com.example.myclub.view.Team.Adapter.RecycleViewAdapterListTeamVertical;

import java.util.ArrayList;
import java.util.List;

public class ListPlayerViewModel extends ViewModel {
    private PlayerRepository playerRepository = PlayerRepository.getInstance();
    private TeamViewModel teamViewModel = TeamViewModel.getInstance();
    private static ListPlayerViewModel instance;
    private RecycleViewAdapterListPlayerVertical adapterListPlayer = new RecycleViewAdapterListPlayerVertical();
    private RecycleViewAdapterListPlayerVertical adapterListOtherPlayer = new RecycleViewAdapterListPlayerVertical();
    private RecycleViewAdapterListPlayerRequestVertical adapterListPlayerRequest = new RecycleViewAdapterListPlayerRequestVertical();
    private MutableLiveData<List<Player>> listPlayerLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Player>> listOtherOtherLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Player>> listPlayerRequestLiveData = new MutableLiveData<>();
    private MutableLiveData<Result> resultLiveData = new MutableLiveData<>(null);
    private String resultMessage = null;


    public static ListPlayerViewModel getInstance() {
        if (instance == null) {
            instance = new ListPlayerViewModel();
        }
        return instance;
    }

    public void getListPlayer(String idTeam) {
        playerRepository.getListPlayer(idTeam, new LoadListPlayerCallBack() {
            @Override
            public void onSuccess(List<Player> listPlayers) {
                if (listPlayers == null) {
                    adapterListPlayer.setListPlayer(new ArrayList<Player>());
                } else {
                    listPlayerLiveData.setValue(listPlayers);
                    adapterListPlayer.setListPlayer(listPlayers);
                    adapterListPlayer.notifyDataSetChanged();
                    resultLiveData.setValue(Result.SUCCESS);
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }


    public void getListOtherPlayer(String idTeam) {
        playerRepository.getListOtherPlayer(idTeam, new LoadListOtherPlayerCallBack() {
            @Override
            public void onSuccess(List<Player> listPlayers) {
                if (listPlayers == null) {
                    adapterListOtherPlayer.setListPlayer(new ArrayList<Player>());
                } else {
                    listOtherOtherLiveData.setValue(listPlayers);
                    adapterListOtherPlayer.setListPlayer(listPlayers);
                    adapterListOtherPlayer.notifyDataSetChanged();
                    resultLiveData.setValue(Result.SUCCESS);
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public void getListPlayerRequest(String idTeam) {
        playerRepository.getListPlayerRequest(idTeam, new LoadListPlayerRequestCallBack() {
            @Override
            public void onSuccess(List<Player> listPlayers) {
                if (listPlayers == null) {
                    adapterListPlayerRequest.setListPlayer(new ArrayList<Player>());
                } else {
                    listPlayerRequestLiveData.setValue(listPlayers);
                    adapterListPlayerRequest.setListPlayer(listPlayers);
                    adapterListPlayerRequest.notifyDataSetChanged();
                    resultLiveData.setValue(Result.SUCCESS);
                }
            }

            @Override
            public void onFailure(String message) {
                resultMessage = message;
                resultLiveData.setValue(Result.FAILURE);
            }
        });
    }

    public RecycleViewAdapterListPlayerVertical getAdapterListPlayer() {
        return adapterListPlayer;
    }

    public RecycleViewAdapterListPlayerVertical getAdapterListOtherTeam() {
        return adapterListOtherPlayer;
    }


    public RecycleViewAdapterListPlayerRequestVertical getAdapterListPlayerRequest() {
        return adapterListPlayerRequest;
    }


    public LiveData<Result> getResultLiveData() {
        return resultLiveData;
    }

    public String getResultMessage() {
        return resultMessage;
    }


}