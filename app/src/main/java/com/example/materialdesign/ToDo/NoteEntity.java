package com.example.materialdesign.ToDo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// each table in our database has its own entity class
// https://developer.android.com/reference/androidx/room/Entity

@Entity(tableName = "note_table")
public class NoteEntity {

    // PRIMARY KEY
    // I suspect we could also use an interface which has the key in it, which can be useful in some instances
    // and make our NoteEntity implement the said interface
    // we would do so in a normal entity which would grant us the ability to call functions that require an objects from different classes which all implement the same interface
    @PrimaryKey(autoGenerate = true)
    private int id;

    //TABLE FIELDS
    // @ColumnInfo (name = " " ) if we want to have 2 names for our variables, one we use in the table the other we use in code
    // or lets say the variable name we get from Json doesn't suit our needs so we rename it
    private String title;
    private String description;
    private int priority;

    public NoteEntity(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    // we need a setter for the id variable so room can use it afterwards
    public void setId(int id) {
        this.id = id;
    }

    //GET METHODS
    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getPriority() {
        return priority;
    }
}
