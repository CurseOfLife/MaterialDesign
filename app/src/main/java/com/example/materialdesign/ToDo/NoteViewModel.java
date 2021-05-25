package com.example.materialdesign.ToDo;

import android.app.Application;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.List;


//Activity has a reference to the ViewModel
//Activity doesn't have a reference to the Repository
//feature of MVVM 1 layer sees only a single layer below it and doesnt know about the rest

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<NoteEntity>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert (NoteEntity noteEntity){
        repository.insert(noteEntity);
    }

    public void update (NoteEntity noteEntity){
        repository.update(noteEntity);
    }

    public void delete (NoteEntity noteEntity){
        repository.delete (noteEntity);
    }

    public void deleteAllNotes(){
        repository.deleteAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes(){
        allNotes = repository.getAllNotes();

        return allNotes;
    }

}
