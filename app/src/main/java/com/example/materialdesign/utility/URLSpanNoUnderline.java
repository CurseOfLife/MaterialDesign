package com.example.materialdesign.utility;

import android.text.TextPaint;
import android.text.style.URLSpan;

import androidx.annotation.NonNull;

public class URLSpanNoUnderline extends URLSpan {

    public URLSpanNoUnderline(String url) {
        super(url);
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}
