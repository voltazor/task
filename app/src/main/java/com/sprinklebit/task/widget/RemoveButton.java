package com.sprinklebit.task.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sprinklebit.task.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by voltazor on 16/01/17.
 */
public class RemoveButton extends FrameLayout {

    @BindView(R.id.text)
    TextView textView;

    public RemoveButton(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public RemoveButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RemoveButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public RemoveButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_remove_button, this, true);
        ButterKnife.bind(this);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RemoveButton, defStyleAttr, defStyleRes);

            try {
                CharSequence text = a.getText(R.styleable.RemoveButton_android_text);
                if (text != null) {
                    textView.setText(text);
                }
            } finally {
                a.recycle();
            }
        }
    }

}
