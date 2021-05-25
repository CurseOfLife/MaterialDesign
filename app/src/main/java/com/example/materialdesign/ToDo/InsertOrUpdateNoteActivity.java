package com.example.materialdesign.ToDo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class InsertOrUpdateNoteActivity extends AppCompatActivity {

    private TextInputEditText editText_title;
    private TextInputEditText editText_description;
    private NumberPicker numberPicker_priority;

    // i forgot where I've read but apparently some people write  constants as nameOfPackage.ConstName
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PRIORITY = "PRIORITY";

    private ExtendedFloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_or_update_note);

        initComponents();
    }

    private void initComponents() {
        editText_title = findViewById(R.id.edit_text_title);
        editText_description = findViewById(R.id.edit_text_description);
        numberPicker_priority = findViewById(R.id.number_picker_priority);
        fab = findViewById(R.id.add_note_button);

        //min and max value for number picker
        numberPicker_priority.setMinValue(1);
        numberPicker_priority.setMaxValue(5);

        initToolbar();

        // as insert and update are from the UI point of view the same thing
        // we can speed up things by doing the following
        // if the main activity is sending us extras that contain an ID of a note we tell this activity to change its ui to serve as an Update
        Intent intent = getIntent();

        if (!intent.hasExtra(ID)){

            setTitle("Insert New Note"); // setting the app bar tittle

        }else{
            setTitle("Update Note"); // setting the app bar tittle
            editText_title.setText(intent.getStringExtra(TITLE)); // the field text is set to the tittle of the note the user wants changed
            editText_description.setText(intent.getStringExtra(DESCRIPTION)); // the field text is set to the desc of the note the user wants changed
            numberPicker_priority.setValue(intent.getIntExtra(PRIORITY, 5)); // the number picker is set to the priority of the note the user wants changed
        }

        // when the fab is clicked a note is created or updated
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewNote();
            }
        });
    }

    private void initToolbar(){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        // toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save_note:
                saveNewNote(); //saving a new note on menu item click
                return true;

            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Insert new note
    //Update an existing note
    private void saveNewNote() {
        //this method is the guts of our update and insert logic
        //as we are using one activity to do update and insert, we need to know if the user is wishing to update an existing or to insert a new note

        //we ask the user to enter the values and then we get them
        String title = editText_title.getText().toString();
        String description = editText_description.getText().toString();
        int priority = numberPicker_priority.getValue();

        /*
            But the user could have entered nothing as the note title, or desc, or both
            We have to check if the fields are empty before we allow him to submit the data
            We also have to trim the text if the user entered a black space only which would allow him to submit a note, as whitespace is an ascii character
         */

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Title or description might be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(description.trim().length()>60) {
            Toast.makeText(this,"Description too long",Toast.LENGTH_SHORT).show();
            return;

        }

        // now we prepare the intent to sent the information back to the main activity
        // Keep in mind we  entered only tittle, description and priority
        // So where is the id you might ask
        // we get it via extras if the user wishes to update
        // but if its an insert method the id is auto generated via room
        Intent intent = new Intent();
        intent.putExtra(TITLE, title);
        intent.putExtra(DESCRIPTION, description);
        intent.putExtra(PRIORITY, priority);

        // this is how we solve the above commented idea
        // as the id can't be -1 we use the ID we got from the extras
        // if we didnt get the id, the default value is -1
        // ID cant be negative so dont put the default value as any positive number or it might cause problems in big databses

        int id = getIntent().getIntExtra(ID,-1);
        if (id !=-1){
            intent.putExtra(ID,id);

        }

        // main note activity called this activity for data to be entered and returned back to it
        //if the operation was successful we send the intent back to main activity
        setResult(RESULT_OK, intent);
        //closing this activity
        finish();
    }
}