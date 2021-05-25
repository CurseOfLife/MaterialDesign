package com.example.materialdesign.activity.text;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.materialdesign.BuildConfig;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class VariousTextTypesActivity extends AppCompatActivity {

    private TextInputLayout textInputLayout12, textInputLayout5;
    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_various_text_types);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initComponents() {
        textInputLayout12 = findViewById(R.id.textField12);
        textInputLayout5 = findViewById(R.id.textField5);
        autoCompleteTextView = findViewById(R.id.dropdown_text);

        String[] items = new String[]{
                "Item 1",
                "Item 2",
                "Item 3",
                "Item 4",
                "Other",
        };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, items);

        autoCompleteTextView.setAdapter(arrayAdapter);


        textInputLayout5.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Respond to end icon presses
            }
        });

        textInputLayout5.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(TextInputLayout textInputLayout) {

                // If any specific changes should be done when the edit text is attached (and
                // thus when the trailing icon is added to it), set an
                // OnEditTextAttachedListener.

                // Example: The clear text icon's visibility behavior depends on whether the
                // EditText has input present. Therefore, an OnEditTextAttachedListener is set
                // so things like editText.getText() can be called.
            }
        });

        textInputLayout5.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(TextInputLayout textInputLayout) {
                // If any specific changes should be done if/when the endIconMode gets
                // changed, set an OnEndIconChangedListener.

                // Example: If the password toggle icon is set and a different EndIconMode
                // gets set, the TextInputLayout has to make sure that the edit text's
                // TransformationMethod is still PasswordTransformationMethod. Because of
                // that, an OnEndIconChangedListener is used.
            }
        });
    }

    private void initToolbar() {


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        // toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Various TextView Types");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String message = "Various types of text fields.\n Click on each and see what happens.";

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.error:
                showInformationDialog(message);
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

}
