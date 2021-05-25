package com.example.materialdesign.activity.dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.fragment.DialogFullscreenFragment;
import com.example.materialdesign.model.EventDTO;
import com.example.materialdesign.utility.VariousTools;

public class DialogFullscreen extends AppCompatActivity {

    public static final int DIALOG_FULLSCREEN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_fullscreen);

        VariousTools.setSystemBarColor(this,  R.color.colorPrimaryDark);
        initToolbar();

        ((Button) findViewById(R.id.b1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFullscreenDialog();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar =  findViewById(R.id.simple_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Fullscreen Dialog");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void showFullscreenDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        com.example.materialdesign.fragment.DialogFullscreenFragment fragment = new com.example.materialdesign.fragment.DialogFullscreenFragment();
        fragment.setRequestCode(DIALOG_FULLSCREEN);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.add(android.R.id.content, fragment).addToBackStack(null).commit();

        fragment.setCallbackResult(new DialogFullscreenFragment.CallbackResult() {
            @Override
            public void sendResult(int requestCode, Object obj) {


                if (requestCode == DIALOG_FULLSCREEN) {
                    setAndShowDataResult((EventDTO) obj);
                }
            }
        });
    }

    private void setAndShowDataResult(EventDTO obj) {

        ((TextView) findViewById(R.id.email)).setText(obj.email);
        ((TextView) findViewById(R.id.name)).setText(obj.name);
        ((TextView) findViewById(R.id.location)).setText(obj.location);
        ((TextView) findViewById(R.id.from)).setText(obj.from);
        ((TextView) findViewById(R.id.to)).setText(obj.to);
        ((TextView) findViewById(R.id.allday)).setText(obj.is_allday.toString());
        ((TextView) findViewById(R.id.timezone)).setText(obj.timezone);
    }
}
