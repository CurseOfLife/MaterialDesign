package com.example.materialdesign.activity.other.scratch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cooltechworks.views.ScratchTextView;
import com.example.materialdesign.R;
import com.example.materialdesign.utility.FlipAnimator;

public class ScratchTextViewDemo extends AppCompatActivity {

    private TextView mScratchTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_text_view_demo);

        mScratchTitleView = (TextView) findViewById(R.id.scratch_title_text);
        ScratchTextView scratchTextView = (ScratchTextView) findViewById(R.id.scratch_view);

        if(scratchTextView != null) {
            scratchTextView.setRevealListener(new ScratchTextView.IRevealListener() {
                @Override
                public void onRevealed(ScratchTextView tv) {
                    showPrice();
                    mScratchTitleView.setText(R.string.flat_200_offer);
                }

                @Override
                public void onRevealPercentChangedListener(ScratchTextView stv, float percent) {
                    // on percent reveal.
                }
            });
        }
    }

    private void showPrice() {
        View priceBeforeView = findViewById(R.id.price_before_text);
        View priceAfterText = findViewById(R.id.price_after_text);
        View priceContainer = findViewById(R.id.price_container);
        FlipAnimator animator = new FlipAnimator(priceBeforeView, priceAfterText, priceContainer.getWidth()/2, priceContainer.getHeight()/2);
        animator.setDuration(800);
        animator.setRotationDirection(FlipAnimator.DIRECTION_Y);
        priceContainer.startAnimation(animator);
    }
}