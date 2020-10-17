package com.example.myclub.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myclub.Interface.LoadListField;
import com.example.myclub.data.repository.FieldRepository;
import com.example.myclub.model.Field;
import com.example.myclub.view.field.Adapter.RecycleViewAdapterListFieldVertical;


import java.util.List;

public class ListFieldViewModel extends ViewModel {

    private FieldRepository fieldRepository = FieldRepository.getInstance();

    private RecycleViewAdapterListFieldVertical adapterListField = new RecycleViewAdapterListFieldVertical();
    private MutableLiveData<List<Field>> listFieldLiveData = new MutableLiveData<>();


    public ListFieldViewModel() {
        fieldRepository.getListField(new LoadListField() {
            @Override
            public void FirebaseLoadListTodo(List<Field> fields) {
                listFieldLiveData.setValue(fields);
                adapterListField.setListField(fields);
                adapterListField.notifyDataSetChanged();
            }
        });
    }



    public RecycleViewAdapterListFieldVertical getAdapterListField() {
        return adapterListField;
    }

}