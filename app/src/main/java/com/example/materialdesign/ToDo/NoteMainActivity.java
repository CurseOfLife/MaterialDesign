package com.example.materialdesign.ToDo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.materialdesign.R;
import com.example.materialdesign.activity.InitialActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoteMainActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private NoteViewModel noteViewModel;
    private NoteAdapter adapter;

    private Spinner spinner;

    public static final int INSERT_NOTE = 1000;
    public static final int UPDATE_NOTE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);

        initToolbar();
        initComponents();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_first_page_white_24dp);
        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addSpinnerItems();
    }

    private void addSpinnerItems() {

        spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(NoteMainActivity.this,
                R.layout.spinner_dropdown_item,
                getResources().getStringArray(R.array.spinner_list_item_array));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        Toast.makeText(NoteMainActivity.this, "ASC", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Toast.makeText(NoteMainActivity.this, "DESC", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        showFilterDiaog();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        spinner.setSelection(1);
    }

    private void initComponents() {

        fab = findViewById(R.id.add_note_button);

        //sometimes the fab wont be the color we need
        //we can change the icon color programmatically
        Drawable src_fab_icon = getResources().getDrawable(android.R.drawable.ic_input_add);  //get the drawable
        Drawable white_icon = src_fab_icon.getConstantState().newDrawable();  // copy it in a new one
        white_icon.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);    //set the color filter and place it on top of the existing
        fab.setImageDrawable(white_icon);   //set it to your fab button initialized before

        // starting a new intent on fab click
        // enables users to add a new note
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoteMainActivity.this, InsertOrUpdateNoteActivity.class);
                startActivityForResult(intent, INSERT_NOTE);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter); // setting the note adapter to be used as the recycler view adapter

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // getting the whole list of notes from the view model into our recycler view holder
        // we also observe it here
        noteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
                adapter.submitList(noteEntities);
            }
        });

        // delete a single note
        // by swiping left
        // reminder ADD UNDO BUTTON IN SNACKBAR
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNotePosition(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteMainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        //UPDATING A NOTE
        //Passes information to InsertOrUpdateNoteActivity
        //InsertOrUpdateNoteActivity sends back information in onActivityResult
        // we do this by creating an intent and putting extras
        adapter.setOnNoteItemClickListener(new NoteAdapter.OnNoteItemClickListener() {
            @Override
            public void onNoteItemClick(NoteEntity noteEntity) {
                Intent intent = new Intent(NoteMainActivity.this, InsertOrUpdateNoteActivity.class);

                intent.putExtra(InsertOrUpdateNoteActivity.ID, noteEntity.getId());
                intent.putExtra(InsertOrUpdateNoteActivity.TITLE, noteEntity.getTitle());
                intent.putExtra(InsertOrUpdateNoteActivity.DESCRIPTION, noteEntity.getDescription());
                intent.putExtra(InsertOrUpdateNoteActivity.PRIORITY, noteEntity.getPriority());

                startActivityForResult(intent, UPDATE_NOTE);
            }
        });


        // hiding the fab when recycler view is scrolled
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fab.show();
                }

                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0 || dy < 0 && fab.isShown()) {
                    fab.hide();
                }
            }
        });

    }

    private void showFilterDiaog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_filter);
        dialog.setCancelable(true);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final NumberPicker numberPicker_filter = dialog.findViewById(R.id.np_filter);

        //min and max value for number picker
        numberPicker_filter.setMinValue(1);
        numberPicker_filter.setMaxValue(5);

        ((Button) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Filter Closed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btn_show_all)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noteViewModel.getAllNotes();

                Toast.makeText(getApplicationContext(), "Successful!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        ((Button) dialog.findViewById(R.id.btn_filter)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Unfinished!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Retrieving extras
        // this method handles the result provided by InsertOrUpdateNoteActivity
        if (requestCode == INSERT_NOTE && resultCode == RESULT_OK) {
            assert data != null;
            String title = data.getStringExtra(InsertOrUpdateNoteActivity.TITLE);
            String description = data.getStringExtra(InsertOrUpdateNoteActivity.DESCRIPTION);
            int priority = data.getIntExtra(InsertOrUpdateNoteActivity.PRIORITY, 5);

            NoteEntity noteEntity = new NoteEntity(title, description, priority);
            noteViewModel.insert(noteEntity);  //inserting a new note in the database

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == UPDATE_NOTE && resultCode == RESULT_OK) {

            assert data != null;
            int id = data.getIntExtra(InsertOrUpdateNoteActivity.ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note couldn't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String title = data.getStringExtra(InsertOrUpdateNoteActivity.TITLE);
            String description = data.getStringExtra(InsertOrUpdateNoteActivity.DESCRIPTION);
            int priority = data.getIntExtra(InsertOrUpdateNoteActivity.PRIORITY, 5);

            NoteEntity noteEntity = new NoteEntity(title, description, priority);
            noteEntity.setId(id);

            noteViewModel.update(noteEntity); // updating an existing note

            Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();

        } else { //user canceled the entry
            Toast.makeText(this, "Entry canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes(); //deleting all notes
                Toast.makeText(this, "All Notes Have Been Deleted", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_about:
                Intent i = new Intent(NoteMainActivity.this, AboutNoteActivity.class);
                startActivity(i);
                return true;

            case android.R.id.home:

                Intent intent = new Intent(NoteMainActivity.this, InitialActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
