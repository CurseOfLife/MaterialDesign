package com.example.materialdesign.ToDo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

// repository serves as an API to fetch data from the database, web and other sources
// we are only fetching from a mysql database in this example
// https://developer.android.com/jetpack/docs/guide

public class NoteRepository {

    private NoteDAO noteDAO;  // object needed for the repository to communicate with the database, it can communicate only one level below which is the DAO level
    private LiveData<List<NoteEntity>> allNotes; // we have to create an object that holds the complete list of notes from the databse

    // CONST
    //Room auto-generates methods noteDAO(), getAllNotes()
    public NoteRepository (Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);

        noteDAO = database.noteDAO();
        allNotes= noteDAO.getAllNotes();
    }

    //ROOM DOESN'T ALLOW insert; update; delete; deleteAllNotes to be executed on the main thread
    //which is why we need to make ASYNC TASK static classes in order to
    //manually perform the said methods on an background task (thread)
    //from what i have read even if we don't use Room it would be better to use new threads for such executions

    //INSERT METHOD -> tells the dao to insert a new note into the databse and provides him with the note object; AsyncTask
    public void insert (NoteEntity noteEntity){
        new InsertNoteAsyncTask(noteDAO).execute(noteEntity);
    }

    //UPDATE METHOD -> tells the dao to update an existing note  and provides him with the note object; AsyncTask
    public void update (NoteEntity noteEntity){
        new UpdateNoteAsyncTask(noteDAO).execute(noteEntity);
    }

    //DELETE METHOD -> tells the dao which note to delete from the database; AsyncTask
    public void delete (NoteEntity noteEntity){
        new DeleteNoteAsyncTask(noteDAO).execute(noteEntity);
    }

    //DELETEALLNOTES METHOD -> tells the dao to delete all notes from the databse; AsyncTask
    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDAO).execute();
    }

    //GETALLNOTES METHOD -> asks the dao to recieve all notes from the database; we sorted them DESC in the dao
    public LiveData<List<NoteEntity>> getAllNotes() {
        return allNotes;
    }

    //ASYNCTASK STATIC CLASS FOR INSERT
    //static -> could otherwise cause a memory leak
    private static class InsertNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDAO noteDAO;

        //CONSTRUCTOR -> needed cuz our class is a static one
        private InsertNoteAsyncTask (NoteDAO noteDAO){this.noteDAO = noteDAO;}

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {

            noteDAO.insert(noteEntities[0]);
            return null;
        }
    }

    //ASYNCTASK STATIC CLASS FOR UPDATE
    //static -> could otherwise cause a memory leak
    private static class UpdateNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDAO noteDAO;

        //CONSTRUCTOR -> needed cuz our class is a static one
        private UpdateNoteAsyncTask (NoteDAO noteDAO){this.noteDAO = noteDAO;}

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {

            noteDAO.update(noteEntities[0]);
            return null;
        }
    }

    //ASYNCTASK STATIC CLASS FOR DELETE
    //static -> could otherwise cause a memory leak
    private static class DeleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void> {

        private NoteDAO noteDAO;

        //CONSTRUCTOR -> needed cuz our class is a static one
        private DeleteNoteAsyncTask (NoteDAO noteDAO){this.noteDAO = noteDAO;}

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {

            noteDAO.delete(noteEntities[0]);
            return null;
        }
    }

    //ASYNCTASK STATIC CLASS FOR DeleteAllNotes
    //static -> could otherwise cause a memory leak
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private NoteDAO noteDAO;

        //CONSTRUCTOR -> needed cuz our class is a static one
        private DeleteAllNotesAsyncTask (NoteDAO noteDAO){this.noteDAO = noteDAO;}

        @Override
        protected Void doInBackground(Void... voids) {

            noteDAO.deleteAllNotes();
            return null;
        }
    }
}
