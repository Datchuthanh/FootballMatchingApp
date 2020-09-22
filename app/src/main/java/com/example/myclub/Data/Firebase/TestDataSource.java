package com.example.myclub.Data.Firebase;

import android.util.Log;

import com.example.myclub.Interface.FirebaseLoadListTodo;
import com.example.myclub.model.Todo;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TestDataSource {
    static TestDataSource instance;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static TestDataSource getInstance() {
        if (instance == null) {
            instance = new TestDataSource();
        }
        return instance;
    }

    //Firestore realtime
    public void loadListTodo(final FirebaseLoadListTodo firebaseLoadListTodo) {
            db.collection("Todo").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<Todo> todos = new ArrayList<>();
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Todo p = new Todo();
                            p.setName((String) document.get("name"));
                            p.setPassword((String) document.get("password"));
                            todos.add(p);
                        }
                        firebaseLoadListTodo.FirebaseLoadListTodo(todos);
                    } else {
                        Log.d("meomeo", "failed");
                        
                    }
                }
            });

    }

//

}
