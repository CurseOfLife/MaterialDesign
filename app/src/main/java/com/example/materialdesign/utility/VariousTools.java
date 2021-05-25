package com.example.materialdesign.utility;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class VariousTools {

    /**
     * Lollipop brought with it the ability to change the system bar color
     * @param activity Activity which called the function
     * @param color resource color used as the system bar color
     */
    public static void setSystemBarColor(Activity activity, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(color));
        }
    }

    //region Image Display
    /**
     *  Displaying a simple image with Glide
     * @param context activity or fragment
     * @param imageView layout
     * @param drawableId drawable image file
     */
    public static void displayImage(Context context, ImageView imageView, @DrawableRes int drawableId) {
        try {
            Glide.with(context)
                    .load(drawableId)
                    .transition (withCrossFade())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(imageView);
        } catch (Exception e) {
        }
    }

    //endregion

    //region Formatting
    //https://www.tutorialspoint.com/simpledateformat-e-dd-mmm-yyyy-hh-mm-ss-z-in-java
    public static String getDateFormat(long dt) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("EEE, MMM dd yyyy");
        return format.format(new Date(dt));
    }

    public static String getTimeFormat(Long time) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("h:mm a");
        return format.format(new Date(time));
    }
   //endregion

    //region Expand View Function
    public static void expand (final View view, final iOnAnimationFinished iOnAnimationFinished) {

        Animation animation = expandAnimation(view);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                iOnAnimationFinished.onFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        view.startAnimation(animation);
    }
    //https://stackoverflow.com/questions/4946295/android-expand-collapse-animation
    private static Animation expandAnimation(final View view) {
        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1
                        ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {

                return true;
            }
        };

        // Expansion speed of 1dp/ms
        animation.setDuration((int) (targtetHeight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
        return animation;
    }
    //endregion

    //region Collapse View Function
    public static void collapse(final View view) {
        final int initialHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        animation.setDuration((int) (initialHeight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }

    //endregion

    //region View Animations
    //https://github.com/DynamicsCRM/Android-Activity-Tracker-for-Dynamics-CRM-Web-API/blob/master/app/src/main/java/com/microsoft/activitytracker/ui/widgets/FloatingActionMenu.java
    //https://github.com/BoBoMEe/Android-Demos/blob/master/blogcodes/app/src/main/java/com/bobomee/blogdemos/view/arcmenu/ArcMenu.java
    public static boolean setFabRotation(final View view, boolean is_expanded) {

        view.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(is_expanded ? 135f : 0f);

        return is_expanded;
    }

    public static void setVisibilityGoneAnimationAtStart(final View view) {
        view.setVisibility(View.GONE);
        view.setTranslationY(view.getHeight());
        view.setAlpha(0f);
    }

    public static void setVisibilityGoneAnimation(final View view) {

        view.setVisibility(View.VISIBLE);
        view.setAlpha(1f);
        view.setTranslationY(0);

        view.animate()
                .setDuration(200)
                .translationY(view.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    public static void setVisibilityVisibleAnimation(final View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.setTranslationY(view.getHeight());
        view.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }

    //endregion

    // stripping the underlines from links
    // check the AdMobBanner to see usage
    public static void stripUnderlines(TextView textView){
        Spannable s = new SpannableString(textView.getText());
        URLSpan[] spans = s.getSpans(0, s.length(), URLSpan.class);

        for (URLSpan span: spans){
            int start = s.getSpanStart(span);
            int end = s.getSpanEnd(span);

            s.removeSpan(span);

            span = new URLSpanNoUnderline(span.getURL());

            s.setSpan(span, start, end , 0);

        }
        textView.setText(s);
    }

    //region Interfaces
    public interface iOnAnimationFinished {
        void onFinish();
    }
    //endregion
}
