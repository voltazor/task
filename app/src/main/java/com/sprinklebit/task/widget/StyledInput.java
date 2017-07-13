package com.sprinklebit.task.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sprinklebit.task.R;
import com.sprinklebit.task.util.AfterTextChangedWatcher;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by voltazor on 15/12/16.
 */
public class StyledInput extends FrameLayout {

    @Nullable
    @BindView(R.id.input_wrapper)
    protected TextInputLayout inputWrapper;

    @BindView(R.id.input)
    protected EditText input;

    private int textColor;
    private int hintTextColor;
    private int disabledTextColor;
    private CharSequence originalHint;
    private boolean isMandatory;

    public StyledInput(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public StyledInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public StyledInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public StyledInput(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StyledInput, defStyleAttr, defStyleRes);
            try {
                inflate();
                config(context, a);
            } finally {
                a.recycle();
            }
        } else {
            inflate();
        }
        input.addTextChangedListener(new AfterTextChangedWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (inputWrapper != null) {
                    inputWrapper.setError(null);
                    inputWrapper.setErrorEnabled(false);
                }
            }
        });
        input.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    input.setSelection(input.getText().length());
                }
            }
        });
    }

    private void inflate() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_input, this, true);
        ButterKnife.bind(this);
    }

    protected void config(Context context, TypedArray a) {
        int textSize = a.getDimensionPixelSize(R.styleable.StyledInput_android_textSize, -1);
        if (textSize != -1) {
            setTextSize(textSize);
        }

        int hintTextSize = a.getDimensionPixelSize(R.styleable.StyledInput_hintTextSize, -1);
        if (hintTextSize != -1) {
            setHintTextSize(hintTextSize);
        }

        int maxLines = a.getInt(R.styleable.StyledInput_android_maxLines, -1);
        if (maxLines != -1) {
            setMaxLines(maxLines);
        }

        int lines = a.getInt(R.styleable.StyledInput_android_lines, -1);
        if (lines != -1) {
            setLines(lines > input.getMaxLines() ? input.getMaxLines() : lines);
        }

        if (a.hasValue(R.styleable.StyledInput_android_singleLine)) {
            setSingleLine(a.getBoolean(R.styleable.StyledInput_android_singleLine, input.getMaxLines() == 1));
        }

        int maxLength = a.getInt(R.styleable.StyledInput_android_maxLength, -1);
        if (maxLength != -1) {
            setMaxLength(maxLength);
        }

        CharSequence text = a.getText(R.styleable.StyledInput_android_text);
        if (text != null) {
            setText(text);
        }

        CharSequence hint = a.getText(R.styleable.StyledInput_android_hint);
        if (hint != null) {
            if (a.getBoolean(R.styleable.StyledInput_isMandatory, false)) {
                hint = getResources().getString(R.string.mandatory_field, hint);
            }
            setHint(hint);
        }

        if (a.getBoolean(R.styleable.StyledInput_removeUnderline, false)) {
            removeUnderline();
        }

        if (a.getBoolean(R.styleable.StyledInput_errorEnabled, false)) {
            setErrorEnabled(true);
            CharSequence errorText = a.getText(R.styleable.StyledInput_errorText);
            if (errorText != null) {
                setError(errorText);
            }
        } else {
            setErrorEnabled(false);
        }

        int textAppearance = a.getResourceId(R.styleable.StyledInput_android_textStyle, -1);
        if (textAppearance != -1) {
            input.setTextAppearance(context, textAppearance);
        }

        int hintAppearance = a.getResourceId(R.styleable.StyledInput_hintTextStyle, -1);
        if (hintAppearance != -1 && inputWrapper != null) {
            inputWrapper.setHintTextAppearance(hintAppearance);
        }

        textColor = a.getColor(R.styleable.StyledInput_android_textColor, -1);
        if (textColor != -1) {
            setTextColor(textColor);
        }

        hintTextColor = a.getColor(R.styleable.StyledInput_hintTextColor, -1);
        if (hintTextColor != -1) {
            setHintTextColor(hintTextColor);
        }

        disabledTextColor = a.getColor(R.styleable.StyledInput_disabledTextColor, -1);

        int inputType = a.getInteger(R.styleable.StyledInput_android_inputType, EditorInfo.TYPE_CLASS_TEXT);
        if (inputType != -1) {
            setInputType(inputType);
        }

        boolean clickable = a.getBoolean(R.styleable.StyledInput_android_clickable, true);
        if (!clickable) {
            input.setClickable(true);
            input.setFocusable(false);
            input.setFocusableInTouchMode(false);
            input.setLongClickable(false);

            if (disabledTextColor != -1) {
                input.setTextColor(disabledTextColor);
                input.setHintTextColor(disabledTextColor);
            }
        }

        setHintAnimationEnabled(a.getBoolean(R.styleable.StyledInput_hintAnimationEnabled, true));
    }

    public EditText getEditText() {
        return input;
    }

    @Nullable
    public TextInputLayout getTextInputLayout() {
        return inputWrapper;
    }

    public void setTextSizeRes(@DimenRes int textSize) {
        setTextSize(getResources().getDimensionPixelSize(textSize));
    }

    public void setTextSize(int textSize) {
        input.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        setHintTextSize("setExpandedTextSize", textSize);
    }

    public void setHintTextSize(int textSize) {
        setHintTextSize("setCollapsedTextSize", textSize);
    }

    //It's the only way to set hint text size to TextInputLayout programmatically
    private void setHintTextSize(String type, int textSize) {
        if (inputWrapper != null) {
            try {
                Field field = TextInputLayout.class.getDeclaredField("mCollapsingTextHelper");
                field.setAccessible(true);
                Object fieldValue = field.get(inputWrapper);
                Method method = fieldValue.getClass().getDeclaredMethod(type, float.class);
                method.setAccessible(true);
                method.invoke(fieldValue, textSize);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setTextColorRes(@ColorRes int textColor) {
        setTextColor(ContextCompat.getColor(getContext(), textColor));
    }

    public void setTextColor(@ColorInt int textColor) {
        getEditText().setTextColor(textColor);
    }

    public void setHintTextColorRes(@ColorRes int textColor) {
        setHintTextColor(ContextCompat.getColor(getContext(), textColor));
    }

    public void setHintTextColor(@ColorInt int hintTextColor) {
        if (inputWrapper == null) {
            input.setHintTextColor(hintTextColor);
        } else {
            //It's the only way to set hint text color to TextInputLayout programmatically
            try {
                Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
                fDefaultTextColor.setAccessible(true);
                fDefaultTextColor.set(inputWrapper, new ColorStateList(new int[][]{{0}}, new int[]{hintTextColor}));

                Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
                fFocusedTextColor.setAccessible(true);
                fFocusedTextColor.set(inputWrapper, new ColorStateList(new int[][]{{0}}, new int[]{hintTextColor}));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setSingleLine(boolean singleLine) {
        input.setSingleLine(singleLine);
    }

    public void setMaxLines(int maxLines) {
        input.setMaxLines(maxLines);
    }

    public void setLines(int lines) {
        input.setLines(lines);
    }

    public void setMaxLength(int maxLength) {
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    public void setErrorEnabled(boolean enabled) {
        if (inputWrapper != null) {
            inputWrapper.setErrorEnabled(enabled);
        }
    }

    public void setError(CharSequence error) {
        if (inputWrapper == null) {
            input.setError(error);
        } else {
            inputWrapper.setError(error);
        }
    }

    public void setHint(CharSequence hint) {
        originalHint = hint;
        CharSequence newHint = isMandatory
                ? getContext().getString(R.string.mandatory_field, hint)
                : hint;
        if (inputWrapper == null) {
            input.setHint(newHint);
        } else {
            inputWrapper.setHint(newHint);
        }
    }

    public void setMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
        setHint(originalHint);
    }

    public void setText(CharSequence text) {
        setHintAnimationEnabled(false);
        input.setText(text);
        setHintAnimationEnabled(true);
    }

    public CharSequence getText() {
        return input.getText();
    }

    public String getTextString() {
        return getText().toString();
    }

    public void setInputType(int inputType) {
        input.setInputType(inputType);
    }

    public void setHintAnimationEnabled(boolean enabled) {
        if (inputWrapper != null) {
            inputWrapper.setHintAnimationEnabled(enabled);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled != isEnabled()) {
            super.setEnabled(enabled);
            input.setEnabled(enabled);
            if (inputWrapper != null) {
                inputWrapper.setEnabled(enabled);
            }
        }
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        input.setClickable(clickable);
        input.setFocusable(clickable);
        input.setFocusableInTouchMode(clickable);
        input.setLongClickable(clickable);

        if (!clickable) {
            if (disabledTextColor != -1) {
                input.setTextColor(disabledTextColor);
                input.setHintTextColor(disabledTextColor);
            }
        } else {
            if (textColor != -1) {
                input.setTextColor(textColor);
            }
            if (hintTextColor != -1) {
                input.setHintTextColor(hintTextColor);
            }
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        input.setOnClickListener(l);
    }

    public void removeUnderline() {
        input.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener listener) {
        input.setOnEditorActionListener(listener);
    }

    public void setCapitalizeFirstLatter(boolean enabled) {
        if (enabled) {
            input.addTextChangedListener(capitalizeWatcher);
        } else {
            input.removeTextChangedListener(capitalizeWatcher);
        }
    }

    public void addTextChangedListener(TextWatcher watcher) {
        EditText edit;
        if ((edit = getEditText()) != null) {
            edit.addTextChangedListener(watcher);
        }
    }

    public void removeTextChangedListener(TextWatcher watcher) {
        EditText edit;
        if ((edit = getEditText()) != null) {
            edit.removeTextChangedListener(watcher);
        }
    }

    private AfterTextChangedWatcher capitalizeWatcher = new AfterTextChangedWatcher() {

        private String firstLetter;

        @Override
        public void afterTextChanged(Editable s) {
            String text = s.toString();
            if (!TextUtils.isEmpty(s.toString())) {
                String tempFirstLetter = String.valueOf(text.charAt(0));
                String tempFirstLetterUpperCase = tempFirstLetter.toUpperCase();
                if (!tempFirstLetter.equals(firstLetter) && !tempFirstLetter.equals(tempFirstLetterUpperCase)) {
                    firstLetter = tempFirstLetterUpperCase;
                    s.replace(0, 1, firstLetter);
                }
            }
        }
    };

}
