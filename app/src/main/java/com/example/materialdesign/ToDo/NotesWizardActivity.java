package com.example.materialdesign.ToDo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;

public class NotesWizardActivity extends AppCompatActivity {

    private static final int MAX_STEP = 5;

    private ViewPager viewPager;
    private AboutNotesViewPagerAdapter viewPagerAdapter;

    private Button btn_continue;

    private int background_colors[] = {
            R.color.indigo_600,
            R.color.purple_600,
            R.color.indigo_600,
            R.color.purple_600,
            R.color.indigo_600
    };

    private int images[] = {
            R.drawable.mvvm,
            R.drawable.room,
            R.drawable.lifecycle,
            R.drawable.repository,
            R.drawable.observer
    };

    private String titles[] = {
            "Model-View-ViewModel (MVVM)",
            "Room",
            "LiveData",
            "Repository",
            "Observer pattern"
    };
    private String descriptions[] = {
            "Modern Architectural Pattern used to cleanly separate the business and presentation logic of an application from its user interface (UI)",
            "A persistence library that provides an abstraction layer over SQLite to allow for more robust database access while harnessing the full power of SQLite",
            "An observable data holder class.\nLifecycle-aware - respects the lifecycle of other app components, such as activities, fragments, or services",
            "A mediators between different data sources, such as persistent models, web services, and cache.\n\nProvides a clean API so that the rest of the app can retrieve data easily.",
            "Defines a one-to-many dependency between objects so that when one object changes state, all of its dependents are notified and updated automatically"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_wizard);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initComponent();
    }

    private void initComponent() {

        viewPager = (ViewPager) findViewById(R.id.viewPager_about_notes);
        btn_continue = (Button) findViewById(R.id.btn_continue);

        // adding wizard dots and setting position to 0
        setCurrentDotPosition(0);

        viewPagerAdapter = new AboutNotesViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(CustomOnPageChangeListener);

        btn_continue.setVisibility(View.GONE);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NotesWizardActivity.this, NoteMainActivity.class);
                startActivity(intent);
            }
        });

        ((Button) findViewById(R.id.btn_skip)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesWizardActivity.this, NoteMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCurrentDotPosition(int dot_position) {
        LinearLayout parent = (LinearLayout) findViewById(R.id.parent_dots);
        ImageView[] dots = new ImageView[MAX_STEP];

        parent.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);

            int width_height = 16;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(8, 8, 8, 8);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.circle);
            dots[i].setColorFilter(getResources().getColor(R.color.grey_400), PorterDuff.Mode.SRC_IN);

            parent.addView(dots[i]);
        }

        //current dot color white
        if (dots.length > 0) {
            dots[dot_position].setImageResource(R.drawable.circle);
            dots[dot_position].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);
        }
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener CustomOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            setCurrentDotPosition(position);

            if (position == titles.length - 1) {
                btn_continue.setVisibility(View.VISIBLE);
            } else {
                btn_continue.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) { }

        @Override
        public void onPageScrollStateChanged(int arg0) { }
    };

    public class AboutNotesViewPagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public AboutNotesViewPagerAdapter() { }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.item_wizard_about_notes, container, false);

            ((RelativeLayout) view.findViewById(R.id.wizard_item_parent)).setBackgroundColor(getResources().getColor(background_colors[position]));
            ((ImageView) view.findViewById(R.id.wizard_item_image)).setImageResource(images[position]);
            ((TextView) view.findViewById(R.id.wizard_item_title)).setText(titles[position]);
            ((TextView) view.findViewById(R.id.wizard_item_description)).setText(descriptions[position]);

            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
