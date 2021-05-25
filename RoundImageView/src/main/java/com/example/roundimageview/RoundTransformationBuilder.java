package com.example.roundimageview;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

import java.util.Arrays;

// original creators
// ive used the code to learn how to do it myself
// probably know less than when I had started.. lul
// https://github.com/vinc3m1/RoundedImageView

 public  final  class RoundTransformationBuilder {

    private final DisplayMetrics mDisplayMetrics;

    private float[] mCornerRadii = new float[] { 0, 0, 0, 0 };

    private boolean mOval = false;
    private float mBorderWidth = 0;
    private ColorStateList mBorderColor =
            ColorStateList.valueOf(RoundDrawable.DEFAULT_BORDER_COLOR);
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;

    public RoundTransformationBuilder() {
        mDisplayMetrics = Resources.getSystem().getDisplayMetrics();
    }

    public RoundTransformationBuilder scaleType(ImageView.ScaleType scaleType) {
        mScaleType = scaleType;
        return this;
    }

    /**
     * Set corner radius for all corners in px.
     *
     * @param radius the radius in px
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder cornerRadius(float radius) {
        mCornerRadii[Corner.TOP_LEFT] = radius;
        mCornerRadii[Corner.TOP_RIGHT] = radius;
        mCornerRadii[Corner.BOTTOM_RIGHT] = radius;
        mCornerRadii[Corner.BOTTOM_LEFT] = radius;
        return this;
    }

    /**
     * Set corner radius for a specific corner in px.
     *
     * @param corner the corner to set.
     * @param radius the radius in px.
     * @return the builder for chaning.
     */
    public RoundTransformationBuilder cornerRadius(@Corner int corner, float radius) {
        mCornerRadii[corner] = radius;
        return this;
    }

    /**
     * Set corner radius for all corners in density independent pixels.
     *
     * @param radius the radius in density independent pixels.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder cornerRadiusDp(float radius) {
        return cornerRadius(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, mDisplayMetrics));
    }

    /**
     * Set corner radius for a specific corner in density independent pixels.
     *
     * @param corner the corner to set
     * @param radius the radius in density independent pixels.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder cornerRadiusDp(@Corner int corner, float radius) {
        return cornerRadius(corner,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radius, mDisplayMetrics));
    }

    /**
     * Set the border width in pixels.
     *
     * @param width border width in pixels.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder borderWidth(float width) {
        mBorderWidth = width;
        return this;
    }

    /**
     * Set the border width in density independent pixels.
     *
     * @param width border width in density independent pixels.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder borderWidthDp(float width) {
        mBorderWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, mDisplayMetrics);
        return this;
    }

    /**
     * Set the border color.
     *
     * @param color the color to set.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder borderColor(int color) {
        mBorderColor = ColorStateList.valueOf(color);
        return this;
    }

    /**
     * Set the border color as a {@link ColorStateList}.
     *
     * @param colors the {@link ColorStateList} to set.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder borderColor(ColorStateList colors) {
        mBorderColor = colors;
        return this;
    }

    /**
     * Sets whether the image should be oval or not.
     *
     * @param oval if the image should be oval.
     * @return the builder for chaining.
     */
    public RoundTransformationBuilder oval(boolean oval) {
        mOval = oval;
        return this;
    }

    /**
     * Creates a {@link Transformation} for use with picasso.
     *
     * @return the {@link Transformation}
     */
    public Transformation build() {
        return new Transformation() {
            @Override public Bitmap transform(Bitmap source) {
                Bitmap transformed = RoundDrawable.fromBitmap(source)
                        .setScaleType(mScaleType)
                        .setCornerRadius(mCornerRadii[0], mCornerRadii[1], mCornerRadii[2], mCornerRadii[3])
                        .setBorderWidth(mBorderWidth)
                        .setBorderColor(mBorderColor)
                        .setOval(mOval)
                        .toBitmap();
                if (!source.equals(transformed)) {
                    source.recycle();
                }
                return transformed;
            }

            @Override public String key() {
                return "r:" + Arrays.toString(mCornerRadii)
                        + "b:" + mBorderWidth
                        + "c:" + mBorderColor
                        + "o:" + mOval;
            }
        };
    }


}

