package com.example.myclub.data.datasource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myclub.Interface.CallBack;
import com.example.myclub.model.Field;
import com.example.myclub.model.Match;
import com.example.myclub.model.TimeGame;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldDataSource {
    static FieldDataSource instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFunctions functions = FirebaseFunctions.getInstance();

    public static FieldDataSource getInstance() {
        if (instance == null) {
            instance = new FieldDataSource();
        }
        return instance;
    }

    public void loadListField(final CallBack<List<Field>, String> loadListField) {
        db.collection("Field").orderBy("name").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Field> fieldList = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        Field field = document.toObject(Field.class);
                        fieldList.add(field);
                    }
                    loadListField.onSuccess(fieldList);
                } else {
                    loadListField.onSuccess(null);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListField.onFailure(e.getMessage());
            }
        });


    }

    public void loadListTime(String idTeam, final CallBack<List<TimeGame>, String> loadListTimeCallBack) {
        db.collection("Field").document(idTeam).collection("listTimeGame").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final List<TimeGame> listTimeGame = new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                        TimeGame timeGame = document.toObject(TimeGame.class);
                        listTimeGame.add(timeGame);
                    }
                    loadListTimeCallBack.onSuccess(listTimeGame);
                } else {
                    loadListTimeCallBack.onSuccess(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListTimeCallBack.onFailure(e.getMessage());
            }
        });
    }


    public void loadListMatch(String idField, final CallBack<List<Match>, String> loadListMatchCallBack) {
        functions.getHttpsCallable("getListMatchByField").call(idField).addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Gson gson = new Gson();
                List<Map> listTeamMaps = (List<Map>) httpsCallableResult.getData();
                List<Match> listBooking = new ArrayList<>();
                if (listTeamMaps == null) {
                    loadListMatchCallBack.onSuccess(null);
                } else {

                    for (Map teamMap : listTeamMaps) {
                        Match booking = gson.fromJson(gson.toJson(teamMap), Match.class);
                        listBooking.add(booking);
                    }
                    loadListMatchCallBack.onSuccess(listBooking);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loadListMatchCallBack.onFailure("");
            }
        });

    }

}
