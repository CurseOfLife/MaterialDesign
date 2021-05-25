package com.example.materialdesign.activity.card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.materialdesign.R;
import com.example.materialdesign.model.WizardDataDTO;
import com.example.materialdesign.utility.VariousTools;

import java.util.ArrayList;
import java.util.List;

    /*
    While exploring the "big" wide web for pictures of cards (pinterest) I stumbled upon a picture of a "thing".
    It didn't take long to find these two posts
    https://stackoverflow.com/questions/30072836/conditional-wizard-initialization-with-viewpager
    https://stackoverflow.com/questions/43464142/how-to-implement-viewpagers-with-dot-sliders-in-xamarin-android/43519931

    The thing from what I can see is called or is being called a setup wizard or just a wizard.
    "It is a multi-page wizard that enables you to go back and forward one page at a time.
    You cannot skip pages, and you must enter all required information on every page before you can proceed to the next page." -Magento2 Developer Documentation
    */
    /*Documentatiion

    While reading the documentation I found about this class, it is suggested to use it for feeding data, but I had to cut corners and save time
    https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/DiffUtil

    ViewPager1 and 2
    https://developer.android.com/jetpack/androidx/releases/viewpager
    https://developer.android.com/jetpack/androidx/releases/viewpager2

    ViewGroup
    https://developer.android.com/reference/android/view/ViewGroup
    */
public class SetupWizard extends AppCompatActivity {

    private LinearLayout ll_dots;

    // pager and adapter
    private ViewPager viewPager;
    private Button button;
    private Button previous;

    private WizardPagerAdapter wizardPagerAdapter;

    // as there is no database I will create some static texts
    private WizardDataDTO obj1 = new WizardDataDTO("Rule One", "You do not use iPhone\nOK?", R.drawable.my_logo);
    private WizardDataDTO obj2 = new WizardDataDTO("Rule Two", "You do not play on Console\nOK?", R.drawable.my_logo);
    private WizardDataDTO obj3 = new WizardDataDTO("Rule Three", "You do not use Xamarin\nOK?", R.drawable.my_logo);
    private WizardDataDTO obj4 = new WizardDataDTO("Congratulations!", "You are a WINNER\n You may proceed", R.drawable.my_logo);

    private List<WizardDataDTO> wizardDataDTOList;

    // we also need to set how many steps/dots our wizard will have
    private static final int MAX_STEP = 4;
    private ImageView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_wizard);
        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        ll_dots = findViewById(R.id.dots);
        viewPager = findViewById(R.id.pager);
        button = findViewById(R.id.btn_wizard);
        previous = findViewById(R.id.btn_previous);

        dots = new ImageView[MAX_STEP];

        // setting the
        setAsCurrentDot(0);

        createList();

        wizardPagerAdapter = new WizardPagerAdapter();

        //setAdapter works with ViewPager NOT with ViewPage2
        viewPager.setAdapter(wizardPagerAdapter);

        // as per documentation
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap));

        /**
         Set the number of pages that should be retained to either side of the current page in the view hierarchy in an idle state.
         Pages beyond this limit will be recreated from the adapter when needed.
         This is offered as an optimization.

         If you know in advance the number of pages you will need to support or have lazy-loading mechanisms in place on your pages,
         tweaking this setting can have benefits in perceived smoothness of paging animations and interaction.
         If you have a small number of pages (3-4) that you can keep active all at once,

         less time will be spent in layout for newly created view subtrees as the user pages back and forth.
         You should keep this limit low, especially if your pages have complex layouts. This setting defaults to 1.
         **/
        viewPager.setOffscreenPageLimit(4);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                setAsCurrentDot(position);

                if (viewPager.getCurrentItem() != wizardDataDTOList.size() - 1) {
                    button.setText("Next");
                } else {
                    button.setText("Proceed");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    finish();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() - 1;
                if (current >= 0) {
                    // move to the previous  screen
                    viewPager.setCurrentItem(current);
                } else {
                    finish();
                }
            }
        });
    }

    private void createList() {
        wizardDataDTOList = new ArrayList<>();
        wizardDataDTOList.add(obj1);
        wizardDataDTOList.add(obj2);
        wizardDataDTOList.add(obj3);
        wizardDataDTOList.add(obj4);
    }

    // current view in focus
    private void setAsCurrentDot(int current_id) {

        ll_dots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width = 16;
            int height = 16;

            LinearLayout.LayoutParams dot_params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width, height));
            dot_params.setMargins(8, 8, 8, 8);

            dots[i].setLayoutParams(dot_params);
            dots[i].setImageResource(R.drawable.circle);
            dots[i].setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_id].setImageResource(R.drawable.circle);
            dots[current_id].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }

    /*
    FragmentStateAdapter vs FragmentPagerAdapter vs PagerAdapter
    https://stackoverflow.com/questions/18747975/what-is-the-difference-between-fragmentpageradapter-and-fragmentstatepageradapte/18748107
    https://stackoverflow.com/questions/8425961/difference-fragmentpageradapter-and-pageradapter
 */
    // to change it up a bit from BooksPagerAdapter where I used FragmentStateAdapter
    // here I will showcase how to use the PagerAdapter
    public class WizardPagerAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        public WizardPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.wizard_item, container, false);


            ((ImageView) view.findViewById(R.id.wizardItemImage)).setImageResource(wizardDataDTOList.get(position).getImage());
            ((TextView) view.findViewById(R.id.wizardItemTitle)).setText(wizardDataDTOList.get(position).getTitle());
            ((TextView) view.findViewById(R.id.wizardItemDescription)).setText(wizardDataDTOList.get(position).getDescription());

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return wizardDataDTOList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
