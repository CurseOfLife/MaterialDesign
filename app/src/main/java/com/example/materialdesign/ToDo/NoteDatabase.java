package com.example.materialdesign.ToDo;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

// The database class defines the list of entities and data access objects in the database. It is also the main access point for the underlying connection.
// https://developer.android.com/reference/androidx/room/Database

@Database(entities = {NoteEntity.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    //SINGLETON
    private static NoteDatabase instance;

    public abstract NoteDAO noteDAO(); //USED TO ACCESS DAO
    //if we have more tables we would create more dao objects for each dao class

    //CREATION OF THE NEW DATABASE VIA SINGLETON PATTERN
    //take note that it is synchronized
    public static synchronized NoteDatabase getInstance(Context context) {

        if (instance == null){

            // we instantiate the room database via a database builder
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database") //here we set the name of the database
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // after the database is created
    // it's populated
    // it is an async task
    // we are populating it for demonstrational purposes
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabaseAsyncTask(instance).execute();
        }
    };

    // populating the database has to be done via an AsyncTask
    // static ineer class does not have access to the instance variables and methods of the outer class
    private static class PopulateDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDAO noteDAO;

        private PopulateDatabaseAsyncTask(NoteDatabase database){
            noteDAO = database.noteDAO();
        }

        // inserting 5 notes into the databse for demonstrational purposes so the user knows how the app works
        @Override
        protected Void doInBackground(Void... voids) {
            noteDAO.insert(new NoteEntity("Title Example One", "Description Example One", 1));
            noteDAO.insert(new NoteEntity("Title Example Two", "Description Example Two", 2));
            noteDAO.insert(new NoteEntity("Title Example Three", "Description Example Three", 3));
            noteDAO.insert(new NoteEntity("Title Example Four", "Description Example Four", 4));
            noteDAO.insert(new NoteEntity("Title Example Five", "Description Example Five", 5));

            return null;
        }
    }

}
