package com.example.roundimageview;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;


public class RoundDrawable extends Drawable {

    public static final String TAG = "RoundDrawable";
    public static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    //rectangle
    private final RectF mBounds = new RectF();
    private final RectF mDrawableRectangle = new RectF();
    private final RectF mBitmapRectangle = new RectF();

    private final RectF mBorderRectangle = new RectF();
    private Paint mBorderPaint;

    private final Matrix mShaderMatrix = new Matrix();
    private final RectF mSquareCornerRectangle = new RectF();

    private final Bitmap mBitmap;
    private final Paint mBitmapPaint;
    private final int mBitmapWidth;
    private final int mBitmapHeight;


    private Shader.TileMode mTileModeX = Shader.TileMode.CLAMP;
    private Shader.TileMode mTileModeY = Shader.TileMode.CLAMP;
    private boolean mRebuildShader = true;

    private float mCornerRadius = 0f;
    // [ topLeft, topRight, bottomLeft, bottomRight ]
    private final boolean[] mRoundedCorners = new boolean[]{true, true, true, true};

    private boolean mOval = false;
    private float mBorderWidth = 0;
    private ColorStateList mBorderColor = ColorStateList.valueOf(DEFAULT_BORDER_COLOR);
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.FIT_CENTER;


    public RoundDrawable(Bitmap bitmap) {
        mBitmap = bitmap;

        mBitmapWidth = bitmap.getWidth();
        mBitmapHeight = bitmap.getHeight();
        mBitmapRectangle.set(0, 0, mBitmapWidth, mBitmapHeight);

        mBitmapPaint = new Paint();
        mBitmapPaint.setStyle(Paint.Style.FILL);
        mBitmapPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    public static RoundDrawable fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new RoundDrawable(bitmap);
        } else {
            return null;

        }
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable != null) {
            if (drawable instanceof RoundDrawable) {
                return drawable; // just return if its already a RoundDrawable

            } else if (drawable instanceof LayerDrawable) {
                ConstantState constantState = drawable.mutate().getConstantState();
                LayerDrawable layerDrawable = (LayerDrawable) (constantState != null ? constantState.newDrawable() : drawable);

                int num = layerDrawable.getNumberOfLayers();

                //loop through layers and change to RoundDrawables if possible
                for (int i = 0; i < num; i++) {
                    Drawable dw = layerDrawable.getDrawable(i);
                    layerDrawable.setDrawableByLayerId(layerDrawable.getId(i), fromDrawable(dw));
                }
                return layerDrawable;
            }

            Bitmap bitmap = drawableToBitmap(drawable);
            if (bitmap != null) {
                return new RoundDrawable(bitmap);
            }
        }
        return drawable;
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap;
        int width = Math.max(drawable.getIntrinsicWidth(), 2);
        int height = Math.max(drawable.getIntrinsicHeight(), 2);

        try {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);

            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.w(TAG, "Failed to create bitmap from drawable!");

            bitmap = null;
        }

        return bitmap;
    }

    public Bitmap getSourceBitmap() {
        return mBitmap;
    }

    @Override
    public boolean isStateful() {
        return mBorderColor.isStateful();
    }

    @Override
    protected boolean onStateChange(int[] state) {
        int newColor = mBorderColor.getColorForState(state, 0);

        if (mBorderPaint.getColor() != newColor) {
            mBorderPaint.setColor(newColor);
            return true;
        } else {
            return super.onStateChange(state);
        }
    }

    private void updateShaderMatrix() {
        float scale;
        float dx;
        float dy;

        switch (mScaleType) {

            case CENTER:
                mBorderRectangle.set(mBounds);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);

                mShaderMatrix.reset();
                mShaderMatrix.setTranslate((int) ((mBorderRectangle.width() - mBitmapWidth) * 0.5f + 0.5f),
                        (int) ((mBorderRectangle.height() - mBitmapHeight) - 0.5f + 0.f));

                break;

            case CENTER_CROP:
                mBorderRectangle.set(mBounds);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);

                mShaderMatrix.reset();

                dx = 0;
                dy = 0;

                if (mBitmapWidth * mBorderRectangle.height() > mBorderRectangle.width() * mBitmapHeight) {
                    scale = mBorderRectangle.height() / (float) mBitmapHeight;
                    dx = (mBorderRectangle.width() - mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = mBorderRectangle.width() / (float) mBitmapWidth;
                    dy = (mBorderRectangle.height() - mBitmapHeight * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth / 2,
                        (int) (dy + 0.5f) + mBorderWidth / 2);
                break;

            case CENTER_INSIDE:
                mShaderMatrix.reset();

                if (mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height()) {
                    scale = 1.0f;
                } else {
                    scale = Math.min(mBounds.width() / (float) mBitmapWidth,
                            mBounds.height() / (float) mBitmapHeight);
                }

                dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);

                mBorderRectangle.set(mBitmapRectangle);
                mShaderMatrix.mapRect(mBorderRectangle);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBorderRectangle, Matrix.ScaleToFit.FILL);
                break;


            default:
            case FIT_CENTER:
                mBorderRectangle.set(mBitmapRectangle);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBounds, Matrix.ScaleToFit.CENTER);
                mShaderMatrix.mapRect(mBorderRectangle);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBorderRectangle, Matrix.ScaleToFit.FILL);
                break;

            case FIT_END:
                mBorderRectangle.set(mBitmapRectangle);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBounds, Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(mBorderRectangle);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBorderRectangle, Matrix.ScaleToFit.FILL);
                break;

            case FIT_START:
                mBorderRectangle.set(mBitmapRectangle);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBounds, Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(mBorderRectangle);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBorderRectangle, Matrix.ScaleToFit.FILL);
                break;

            case FIT_XY:
                mBorderRectangle.set(mBounds);
                mBorderRectangle.inset(mBorderWidth / 2, mBorderWidth / 2);
                mShaderMatrix.reset();
                mShaderMatrix.setRectToRect(mBitmapRectangle, mBorderRectangle, Matrix.ScaleToFit.FILL);
                break;
        }

        mDrawableRectangle.set(mBorderRectangle);
        mRebuildShader = true;

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        mBounds.set(bounds);
        updateShaderMatrix();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        if (mRebuildShader) {
            BitmapShader bitmapShader = new BitmapShader(mBitmap, mTileModeX, mTileModeY);

            if (mTileModeX == Shader.TileMode.CLAMP && mTileModeY == Shader.TileMode.CLAMP) {
                bitmapShader.setLocalMatrix(mShaderMatrix);
            }
            mBitmapPaint.setShader(bitmapShader);
            mRebuildShader = false;
        }

        if (mOval) {
            if (mBorderWidth > 0) {
                canvas.drawOval(mDrawableRectangle, mBitmapPaint);
                canvas.drawOval(mBorderRectangle, mBorderPaint);
            } else {
                canvas.drawOval(mDrawableRectangle, mBitmapPaint);
            }

        } else {
            if (any(mRoundedCorners)) {
                float radius = mCornerRadius;

                if (mBorderWidth > 0) {

                    canvas.drawRoundRect(mDrawableRectangle, radius, radius, mBitmapPaint);
                    canvas.drawRoundRect(mBorderRectangle, radius, radius, mBorderPaint);

                    redrawBitmapForSquareCorners(canvas);
                    redrawBorderForSquareCorners(canvas);

                } else {
                    canvas.drawRoundRect(mDrawableRectangle, radius, radius, mBitmapPaint);
                    redrawBitmapForSquareCorners(canvas);
                }
            } else {
                canvas.drawRect(mDrawableRectangle, mBitmapPaint);

                if (mBorderWidth > 0) {
                    canvas.drawRect(mBorderRectangle, mBorderPaint);
                }
            }
        }
    }

    private void redrawBitmapForSquareCorners(Canvas canvas) {
        if (all(mRoundedCorners)) {
            // no square corners
            return;
        }

        if (mCornerRadius == 0) {
            return; // no round corners
        }

        float left = mDrawableRectangle.left;
        float top = mDrawableRectangle.top;
        float right = left + mDrawableRectangle.width();
        float bottom = top + mDrawableRectangle.height();
        float radius = mCornerRadius;

        if (!mRoundedCorners[Corner.TOP_LEFT]) {
            mSquareCornerRectangle.set(left, top, left + radius, top + radius);
            canvas.drawRect(mSquareCornerRectangle, mBitmapPaint);
        }

        if (!mRoundedCorners[Corner.TOP_RIGHT]) {
            mSquareCornerRectangle.set(right - radius, top, right, radius);
            canvas.drawRect(mSquareCornerRectangle, mBitmapPaint);
        }

        if (!mRoundedCorners[Corner.BOTTOM_RIGHT]) {
            mSquareCornerRectangle.set(right - radius, bottom - radius, right, bottom);
            canvas.drawRect(mSquareCornerRectangle, mBitmapPaint);
        }

        if (!mRoundedCorners[Corner.BOTTOM_LEFT]) {
            mSquareCornerRectangle.set(left, bottom - radius, left + radius, bottom);
            canvas.drawRect(mSquareCornerRectangle, mBitmapPaint);
        }
    }

    private void redrawBorderForSquareCorners(Canvas canvas) {
        if (all(mRoundedCorners)) {
            // no square corners
            return;
        }

        if (mCornerRadius == 0) {
            return; // no round corners
        }

        float left = mDrawableRectangle.left;
        float top = mDrawableRectangle.top;
        float right = left + mDrawableRectangle.width();
        float bottom = top + mDrawableRectangle.height();
        float radius = mCornerRadius;
        float offset = mBorderWidth / 2;

        if (!mRoundedCorners[Corner.TOP_LEFT]) {
            canvas.drawLine(left - offset, top, left + radius, top, mBorderPaint);
            canvas.drawLine(left, top - offset, left, top + radius, mBorderPaint);
        }

        if (!mRoundedCorners[Corner.TOP_RIGHT]) {
            canvas.drawLine(right - radius - offset, top, right, top, mBorderPaint);
            canvas.drawLine(right, top - offset, right, top + radius, mBorderPaint);
        }

        if (!mRoundedCorners[Corner.BOTTOM_RIGHT]) {
            canvas.drawLine(right - radius - offset, bottom, right + offset, bottom, mBorderPaint);
            canvas.drawLine(right, bottom - radius, right, bottom, mBorderPaint);
        }

        if (!mRoundedCorners[Corner.BOTTOM_LEFT]) {
            canvas.drawLine(left - offset, bottom, left + radius, bottom, mBorderPaint);
            canvas.drawLine(left, bottom - radius, left, bottom, mBorderPaint);
        }

    }

    @Override
    public void setAlpha(int alpha) {
        mBitmapPaint.getAlpha();
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mBitmapPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmapWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmapHeight;
    }


    /**
     * @return the corner radius.
     */
    public float getmCornerRadius (){
        return  mCornerRadius;
    }

    /**
     * @param corner the specific corner to get radius of.
     * @return the corner radius of the specified corner.
     */
    public float getCornerRadius(@Corner int corner) {
        return mRoundedCorners[corner] ? mCornerRadius : 0f;
    }

    /**
     * Sets all corners to the specified radius.
     *
     * @param radius the radius.
     * @return the {@link RoundDrawable} for chaining.
     */
    public RoundDrawable setCornerRadius(float radius) {
        setCornerRadius(radius, radius, radius, radius);
        return this;
    }

    /**
     * Sets the corner radius of one specific corner.
     *
     * @param corner the corner.
     * @param radius the radius.
     * @return the {@link RoundDrawable} for chaining.
     */
    public RoundDrawable setCornerRadius(@Corner int corner, float radius) {
        if (radius != 0 && mCornerRadius != 0 && mCornerRadius != radius) {
            throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
        }

        if (radius == 0) {
            if (only(corner, mRoundedCorners)) {
                mCornerRadius = 0;
            }
            mRoundedCorners[corner] = false;
        } else {
            if (mCornerRadius == 0) {
                mCornerRadius = radius;
            }
            mRoundedCorners[corner] = true;
        }

        return this;
    }


    /**
     * Sets the corner radii of all the corners.
     *
     * @param topLeft top left corner radius.
     * @param topRight top right corner radius
     * @param bottomRight bototm right corner radius.
     * @param bottomLeft bottom left corner radius.
     * @return the {@link RoundDrawable} for chaining.
     */
    public RoundDrawable setCornerRadius(float topLeft, float topRight, float bottomRight,
                                           float bottomLeft) {

        Set<Float> radiusSet = new HashSet<>(4);
        radiusSet.add(topLeft);
        radiusSet.add(topRight);
        radiusSet.add(bottomRight);
        radiusSet.add(bottomLeft);

        radiusSet.remove(0f);

        if (radiusSet.size() > 1) {
            throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
        }

        if (!radiusSet.isEmpty()) {
            float radius = radiusSet.iterator().next();
            if (Float.isInfinite(radius) || Float.isNaN(radius) || radius < 0) {
                throw new IllegalArgumentException("Invalid radius value: " + radius);
            }
            mCornerRadius = radius;
        } else {
            mCornerRadius = 0f;
        }

        mRoundedCorners[Corner.TOP_LEFT] = topLeft > 0;
        mRoundedCorners[Corner.TOP_RIGHT] = topRight > 0;
        mRoundedCorners[Corner.BOTTOM_RIGHT] = bottomRight > 0;
        mRoundedCorners[Corner.BOTTOM_LEFT] = bottomLeft > 0;
        return this;
    }

    public float getBorderWidth() {
        return mBorderWidth;
    }

    public RoundDrawable setBorderWidth(float width) {
        mBorderWidth = width;
        mBorderPaint.setStrokeWidth(mBorderWidth);
        return this;
    }

    public int getBorderColor() {
        return mBorderColor.getDefaultColor();
    }

    public RoundDrawable setBorderColor(@ColorInt int color) {
        return setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return mBorderColor;
    }

    public RoundDrawable setBorderColor(ColorStateList colors) {
        mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
        mBorderPaint.setColor(mBorderColor.getColorForState(getState(), DEFAULT_BORDER_COLOR));
        return this;
    }

    public boolean isOval() {
        return mOval;
    }

    public RoundDrawable setOval(boolean oval) {
        mOval = oval;
        return this;
    }

    public ImageView.ScaleType getScaleType() {
        return mScaleType;
    }

    public RoundDrawable setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ImageView.ScaleType.FIT_CENTER;
        }
        if (mScaleType != scaleType) {
            mScaleType = scaleType;
            updateShaderMatrix();
        }
        return this;
    }

    public Shader.TileMode getTileModeX() {
        return mTileModeX;
    }

    public RoundDrawable setTileModeX(Shader.TileMode tileModeX) {
        if (mTileModeX != tileModeX) {
            mTileModeX = tileModeX;
            mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    public Shader.TileMode getTileModeY() {
        return mTileModeY;
    }

    public RoundDrawable setTileModeY(Shader.TileMode tileModeY) {
        if (mTileModeY != tileModeY) {
            mTileModeY = tileModeY;
            mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    private static boolean only(int index, boolean[] booleans) {
        for (int i = 0, len = booleans.length; i < len; i++) {
            if (booleans[i] != (i == index)) {
                return false;
            }
        }
        return true;
    }

    private static boolean any(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) { return true; }
        }
        return false;
    }

    private static boolean all(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) { return false; }
        }
        return true;
    }

    public Bitmap toBitmap() {
        return drawableToBitmap(this);
    }


}
