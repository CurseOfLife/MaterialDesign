package com.example.materialdesign.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.ToDo.NotesWizardActivity;
import com.example.materialdesign.utility.VariousTools;

import java.util.Objects;

public class InitialActivity extends AppCompatActivity {

    private long exitTime = 0;

    private TextView patch_notes_one, patch_notes_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);

        //toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Main Menu");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponents() {

        findViewById(R.id.info1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformationDialog("Material Design Components are an essential part of modern android development.\n\nThis section showcases different ways a material design component might be used inside an environment as well as some of it's available functionalities.");
            }
        });
        findViewById(R.id.info2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInformationDialog("Notepad App uses Material Design Components and Guidances.\n\n In it's development I have used Room library, Repository pattern, MVVM Architecture, Observable pattern and LiveData observable data holder class.\n\n At this point the app has a couple features that aren't yet implemented, but can be interacted by the user. After more research into MVVM the mentioned will be implemented.");
            }
        });
        findViewById(R.id.start1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.start2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, NotesWizardActivity.class);
                startActivity(intent);
            }
        });

        patch_notes_one= findViewById(R.id.patch_notes_one);
        patch_notes_two =findViewById(R.id.patch_notes_two);

        patch_notes_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, MDPatchNotesActivity.class);
                intent.putExtra("MDC","MDC");
                startActivity(intent);
            }
        });

        patch_notes_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitialActivity.this, MDPatchNotesActivity.class);
                intent.putExtra("NOTES","NOTES");
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.error:
                showInformationDialog("made by\nPeon\n\nThis project was originally made for the purpose of defending a thesis of the developer, though the development of it was continued and will continue with additional patches being added in the future.\n\nThe application serves as a one for all tool that showcases many features of android development.\n\nMaterial Design, MVVM, Repository Pattern etc..   ");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInformationDialog(String message) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.more_information_dialog);
        dialog.setCancelable(true);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ((TextView) dialog.findViewById(R.id.version)).setText("Version " + BuildConfig.VERSION_NAME);
        ((TextView) dialog.findViewById(R.id.txtInfo)).setText(message);

        ((ImageButton) dialog.findViewById(R.id.btn_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    @Override
    public void onBackPressed() {
        exitApp();
    }


    public void exitApp() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to fully exit the app", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}
