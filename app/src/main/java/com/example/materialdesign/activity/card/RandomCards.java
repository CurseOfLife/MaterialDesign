package com.example.materialdesign.activity.card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.example.materialdesign.R;
import com.example.materialdesign.model.ImageItem;
import com.example.materialdesign.utility.VariousTools;
import com.google.android.material.card.MaterialCardView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class RandomCards extends AppCompatActivity {

    private static final int MAX_DOTS = 4;

    private ViewPager viewPager, vp_credit_card;
    private LinearLayout l_dots;
    private ImageSliderAdapter imageSliderAdapter;
    private CreditCardPagerAdapter creditCardPagerAdapter;
    private Runnable runnable = null;
    private Handler handler = new Handler();

    // images in the view pager
    private static int[] images = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4,
            R.drawable.image_5,
    };

    // titles in the view card
    private static String[] titles = {
            "Title One",
            "Title Two",
            "Title Three",
            "Title Four",
            "Title Five",
    };

    private static String[] dates = {
            "14/02/2000",
            "14/02/2005",
            "8/05/2009",
            "15/04/2008",
            "14/02/1999",
    };

    private String cardnumbers[] = {
            "**** **** **** 7777",
            "**** **** **** 4425",
            "**** **** **** 1234",
            "**** **** **** 7895"
    };
    private String expiration_dates[] = {
            "09/12",
            "1/13",
            "5/18",
            "12/24",
    };
    private String cvvs[] = {
            "888",
            "777",
            "666",
            "554",
    };
    private int logos[] = {
            R.drawable.visa_logo,
            R.drawable.visa_logo,
            R.drawable.my_logo,
            R.drawable.my_logo
    };

    private int colors[] = {
            R.color.indigo_900,
            R.color.indigo_500,
            R.color.deep_purple_900,
            R.color.deep_purple_500
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_cards);

        VariousTools.setSystemBarColor(this, R.color.colorPrimaryDark);

        initToolbar();
        initComponents();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        // toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Random Cards");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initComponents() {
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_1), R.drawable.image_1);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_2), R.drawable.image_2);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_3), R.drawable.image_3);
        VariousTools.displayImage(this, (ImageView) findViewById(R.id.image_4), R.drawable.image_4);


        /// VIEW PAGER
        l_dots = (LinearLayout) findViewById(R.id.l_dots);
        viewPager = (ViewPager) findViewById(R.id.slider_pager);
        imageSliderAdapter = new ImageSliderAdapter(this, new ArrayList<ImageItem>());

        final List<ImageItem> image_list = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            ImageItem imageItem = new ImageItem();
            imageItem.image = images[i];
            imageItem.imageDrw = getResources().getDrawable(imageItem.image);
            imageItem.title = titles[i];
            imageItem.date = dates[i];

            image_list.add(imageItem);
        }

        imageSliderAdapter.setImageList(image_list);
        viewPager.setAdapter(imageSliderAdapter);

        viewPager.setCurrentItem(0);

        //// DOTS
        addDots(l_dots, imageSliderAdapter.getCount(),0);

        /// VIEW PAGER
        ((TextView) findViewById(R.id.title)).setText(image_list.get(0).title);
        ((TextView) findViewById(R.id.date)).setText(image_list.get(0).date);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((TextView) findViewById(R.id.title)).setText(image_list.get(position).title);
                ((TextView) findViewById(R.id.date)).setText(image_list.get(position).date);
                addDots(l_dots, imageSliderAdapter.getCount(),position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        startBannerSlider (imageSliderAdapter.getCount());

        /// CREDIT CARDS
        vp_credit_card = (ViewPager) findViewById(R.id.vp_credit_card);

        creditCardDots(0);

        creditCardPagerAdapter = new CreditCardPagerAdapter ();

        vp_credit_card.setAdapter(creditCardPagerAdapter);
        vp_credit_card.addOnPageChangeListener(viewPagerPageChangeListener);

        vp_credit_card.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin_overlap_payment));
        vp_credit_card.setOffscreenPageLimit(MAX_DOTS);
    }

    private void startBannerSlider(final int count) {
        runnable = new Runnable() {
            @Override
            public void run() {
               int position = viewPager.getCurrentItem();

               position = position +1;
               if(position>=count) position =0;
               viewPager.setCurrentItem(position);

               handler.postDelayed(runnable, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);
    }

    private void addDots(LinearLayout l_dots, int size, int current) {
        ImageView[] dots = new ImageView[size];

        l_dots.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int l_width_height = 16;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(l_width_height, l_width_height));
            params.setMargins(8, 0, 8, 0);

            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.outlined_circle);
            dots[i].setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

            l_dots.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current].setImageResource(R.drawable.outlined_circle);
            dots[current].setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onDestroy() {
        if (runnable != null) handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            creditCardDots(position);
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void creditCardDots(int current_position){
        LinearLayout l_dots = (LinearLayout) findViewById(R.id.credit_card_dots);
        ImageView[] dots = new ImageView[MAX_DOTS];

        l_dots.removeAllViews();

        for (int i = 0; i < dots.length; i++){

            dots[i] = new ImageView(this);
            int width_height = 16;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(8,8,8,8);

            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.circle);
            dots[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

            l_dots.addView(dots[i]);
        }

        if (dots.length > 0){
            dots[current_position].setImageResource(R.drawable.circle);
            dots[current_position].setColorFilter(getResources().getColor(R.color.colorAccentDark), PorterDuff.Mode.SRC_IN);
        }
    }

    public static class ImageSliderAdapter extends PagerAdapter{

        private Activity activity;
        private List<ImageItem> imageList;

        private ImageSliderAdapter.OnItemClickListener onItemClickListener;

        private ImageSliderAdapter(Activity activity, List<ImageItem> items) {
            this.activity = activity;
            this.imageList = items;
        }


        public void setOnItemClickListener(ImageSliderAdapter.OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public int getCount() {
            return this.imageList.size();
        }

        public ImageItem getItem(int pos) {
            return imageList.get(pos);
        }

        public void setImageList(List<ImageItem> imageList) {
            this.imageList = imageList;
            notifyDataSetChanged();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((RelativeLayout) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            final ImageItem image = imageList.get(position);
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.image_slider_item, container, false);

            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            MaterialRippleLayout materialRippleLayout = (MaterialRippleLayout) view.findViewById(R.id.ripple_l_parent);

            VariousTools.displayImage(activity, imageView, image.image);

            materialRippleLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null){
                        onItemClickListener.onItemClick(v, image);
                    }
                }
            });

            ((ViewPager) container).addView(view);

            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            ((ViewPager) container).removeView((RelativeLayout) object);
        }

        private interface OnItemClickListener {
            void onItemClick(View view, ImageItem obj);
        }
    }

    public class CreditCardPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public CreditCardPagerAdapter() { }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.credit_card_item, container, false);
            ((MaterialCardView) view.findViewById(R.id.credit_card)).setCardBackgroundColor(getResources().getColor(colors[position]));
            ((TextView) view.findViewById(R.id.card_number)).setText(cardnumbers[position]);
            ((TextView) view.findViewById(R.id.expiration_date)).setText(expiration_dates[position]);
            ((TextView) view.findViewById(R.id.cvv)).setText(cvvs[position]);
            ((ImageView) view.findViewById(R.id.card_logo)).setImageResource(logos[position]);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return MAX_DOTS;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}


