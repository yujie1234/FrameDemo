package com.android.common.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.common.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Werwolf on 2017/3/23.
 */

public class CustomEditText extends AppCompatEditText {

    private Context mContext;
    private Drawable deleteDrawable;
    private Drawable leftDrawable = null;
    private boolean isDelete;

    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    public CustomEditText(Context context) {
        super(context);
        mContext = context;
        init(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        deleteDrawable = mContext.getResources().getDrawable(R.mipmap.delete);
        int leftImageId = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res/android", "drawableLeft", 0);
        if (leftImageId != 0) {
            leftDrawable = mContext.getResources().getDrawable(leftImageId);
        }

        showDeleteImage();
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!resetText) {
                    cursorPos = getSelectionEnd();
                    // 这里用s.toString()而不直接用s是因为如果用s，
                    // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
                    // inputAfterText也就改变了，那么表情过滤就失败了
                    inputAfterText = s.toString();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!resetText) {
                    if (count >= 1) {//表情符号的字符长度最小为2
                        int afterPosition = cursorPos + count;
                        if(afterPosition <= s.length()){
                            CharSequence input = s.subSequence(cursorPos, afterPosition);
                            if(isEmoji(input.toString())){
                                resetText = true;
                                Toast.makeText(mContext, "不支持输入Emoji表情符号", Toast.LENGTH_SHORT).show();
                                //是表情符号就将文本还原为输入表情符号之前的内容
                                setText(inputAfterText);
                                CharSequence text = getText();
                                if (text instanceof Spannable) {
                                    Spannable spanText = (Spannable) text;
                                    Selection.setSelection(spanText, text.length());
                                }
                            }
                        }
                    }
                } else {
                    resetText = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
//                String str=s.toString().trim();
                showDeleteImage();
            }
        });
    }

    public boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    private void showDeleteImage() {
        if (this.length() < 1) {
            isDelete = false;
            this.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
        } else {
            isDelete = true;
            this.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, deleteDrawable, null);
        }
    }

    public void setErrorNotice(String errorNotice) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.RED);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(errorNotice);
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, errorNotice.length(), 0);
        requestFocus();
        this.setError(spannableStringBuilder);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (deleteDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY) && isDelete) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
