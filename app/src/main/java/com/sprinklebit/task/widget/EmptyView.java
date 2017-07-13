package com.sprinklebit.task.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sprinklebit.task.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by voltazor on 12/07/17.
 */
public class EmptyView extends FrameLayout {

    @BindView(R.id.icon)
    ImageView icon;

    @BindView(R.id.text)
    TextView textView;

    public EmptyView(@NonNull Context context) {
        this(context, null);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EmptyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmptyView, defStyleAttr, defStyleRes);
            try {
                setText(ta.getText(R.styleable.EmptyView_android_text));
                Drawable drawable = ta.getDrawable(R.styleable.EmptyView_android_src);
                if (drawable != null) {
                    setIcon(drawable);
                } else {
                    setIcon(ta.getResourceId(R.styleable.EmptyView_srcCompat, -1));
                }
            } finally {
                ta.recycle();
            }
        }
    }

    public void setText(CharSequence text) {
        textView.setVisibility(text == null ? GONE : VISIBLE);
        textView.setText(text);
    }

    public void setIcon(Drawable drawable) {
        icon.setVisibility(drawable == null ? GONE : VISIBLE);
        icon.setImageDrawable(drawable);
    }

    public void setIcon(@DrawableRes int resId) {
        icon.setVisibility(resId == -1 ? GONE : VISIBLE);
        icon.setImageResource(resId);
    }

}
