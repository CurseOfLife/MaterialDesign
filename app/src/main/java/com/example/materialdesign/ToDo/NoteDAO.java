package com.example.materialdesign.ToDo;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//has to be interface or abstract class
//Room auto-generates code for the annotated methods
//shortcut on annotation ctrl+b to get more info

//Data Access Objects are the main classes where you define your database interactions. They can include a variety of query methods.
//https://developer.android.com/reference/androidx/room/Dao

// every table in our database has its own dao and entity classes

@Dao
public interface NoteDAO {

    @Insert
    void insert (NoteEntity noteEntity); // QUERY -> INSERT ONE NOTE

    @Update
    void update (NoteEntity noteEntity); // QUERY -> UPDATE ONE NOTE

    @Delete
    void delete (NoteEntity noteEntity);  // QUERY -> DELETE ONE NOTE

    // this query is rarely used
    @Query("DELETE FROM note_table") // QUERY -> DELETE ALL NOTES
    void deleteAllNotes();

    // https://developer.android.com/topic/libraries/architecture/livedata
    // LiveData -> Observes for changes in the note_table and updates the List accordingly
    // we need the list of items to update the UI and the database
    @Query("SELECT * FROM note_table ORDER BY priority DESC")   // QUERY -> GET ALL NOTES in a descending order
    LiveData<List<NoteEntity>> getAllNotes();
}
