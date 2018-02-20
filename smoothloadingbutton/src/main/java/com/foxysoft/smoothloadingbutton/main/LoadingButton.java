package com.foxysoft.smoothloadingbutton.main;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.foxysoft.smoothloadingbutton.R;

import java.util.ArrayList;

/**
 * Created by Fox on 15.02.2018.
 */

public class LoadingButton extends FrameLayout {
    private LoadingButtonState mCurrentState = LoadingButtonState.NORMAL;
    private Transition mFullTransition;

    private TextView mTVText;
    private ImageView mIVImage;
    private ProgressBar mProgressBar;

    private String mText;
    private int mTextSize;
    private int mTextColor;
    private Drawable mNormalBackground;
    private Drawable mFinishedBackground;
    private Drawable mFinishedIcon;

    private int mDefaultTextColor;
    private int mDefaultNormalBackground;
    private int mDefaultFinishedBackground;
    private int mDefaultFinishedIcon;

    public LoadingButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mFullTransition = new AutoTransition();
        mFullTransition.setDuration(150);

        mDefaultTextColor = Color.WHITE;
        mDefaultNormalBackground = Color.RED;
        mDefaultFinishedBackground = Color.BLACK;
        mDefaultFinishedIcon = android.R.drawable.ic_delete;

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LoadingButton,
                0, 0);

        try {
            mText = a.getString(R.styleable.LoadingButton_text);
            mTextSize = a.getInt(R.styleable.LoadingButton_textSizeSP, 12);
            mTextColor = a.getColor(R.styleable.LoadingButton_textColor, mDefaultTextColor);
            mNormalBackground = a.getDrawable(R.styleable.LoadingButton_normalBackground);
            mFinishedBackground = a.getDrawable(R.styleable.LoadingButton_finishedBackground);
            mFinishedIcon = a.getDrawable(R.styleable.LoadingButton_finishedIcon);
        } finally {
            a.recycle();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        init(context);
    }

    public void changeState(LoadingButtonState state) {
        changeView(state, true);
    }

    public void setState(LoadingButtonState state) {
        changeView(state, false);
    }

    private void changeView(LoadingButtonState state, boolean animate) {
        ArrayList<View> views = getAllChildren(this);

        if (animate) {
            TransitionManager.beginDelayedTransition(this, mFullTransition);
        }

        if (state == LoadingButtonState.NORMAL) {
            switch (mCurrentState) {
                case NORMAL:
                    if (!animate) {
                        for (View view : views) {
                            if (view instanceof ProgressBar) {
                                view.setVisibility(INVISIBLE);
                            }
                            if (view instanceof ImageView) {
                                view.setVisibility(INVISIBLE);
                            }
                            if (view instanceof TextView) {
                                view.setVisibility(VISIBLE);
                            }
                        }
                    }
                    break;
                case LOADING:
                    for (View view : views) {
                        if (view instanceof ProgressBar) {
                            view.setVisibility(INVISIBLE);
                        }
                        if (view instanceof TextView) {
                            view.setVisibility(VISIBLE);
                        }
                    }
                    break;
                case FINISHED:
                    for (View view : views) {
                        if (view instanceof ImageView) {
                            view.setVisibility(INVISIBLE);
                        }
                        if (view instanceof TextView) {
                            view.setVisibility(VISIBLE);
                        }
                    }

                    break;
            }

            if (mNormalBackground == null) {
                this.setBackgroundColor(mDefaultNormalBackground);
            } else {
                this.setBackground(mNormalBackground);
            }
        } else if (state == LoadingButtonState.LOADING) {
            switch (mCurrentState) {
                case NORMAL:
                    for (View view : views) {
                        if (view instanceof TextView) {
                            view.setVisibility(GONE);
                        }
                        if (view instanceof ProgressBar) {
                            view.setVisibility(VISIBLE);
                        }
                    }
                    break;
                case LOADING:
                    if (!animate) {
                        for (View view : views) {
                            if (view instanceof ProgressBar) {
                                view.setVisibility(VISIBLE);
                            }
                            if (view instanceof ImageView) {
                                view.setVisibility(INVISIBLE);
                            }
                            if (view instanceof TextView) {
                                view.setVisibility(GONE);
                            }
                        }
                    }
                    break;
                case FINISHED:
                    for (View view : views) {
                        if (view instanceof ImageView) {
                            view.setVisibility(INVISIBLE);
                        }
                        if (view instanceof ProgressBar) {
                            view.setVisibility(VISIBLE);
                        }
                    }
                    break;
            }

            if (mNormalBackground == null) {
                this.setBackgroundColor(mDefaultNormalBackground);
            } else {
                this.setBackground(mNormalBackground);
            }
        } else if (state == LoadingButtonState.FINISHED) {
            switch (mCurrentState) {
                case NORMAL:
                    for (View view : views) {
                        if (view instanceof TextView) {
                            view.setVisibility(GONE);
                        }
                        if (view instanceof ImageView) {
                            view.setVisibility(VISIBLE);
                        }
                    }
                    break;
                case LOADING:
                    for (View view : views) {
                        if (view instanceof ProgressBar) {
                            view.setVisibility(INVISIBLE);
                        }
                        if (view instanceof ImageView) {
                            view.setVisibility(VISIBLE);
                        }
                    }
                    break;
                case FINISHED:
                    if (!animate) {
                        for (View view : views) {
                            if (view instanceof ProgressBar) {
                                view.setVisibility(INVISIBLE);
                            }
                            if (view instanceof ImageView) {
                                view.setVisibility(VISIBLE);
                            }
                            if (view instanceof TextView) {
                                view.setVisibility(GONE);
                            }
                        }
                    }
                    break;
            }

            if (mFinishedBackground == null) {
                this.setBackgroundColor(mDefaultFinishedBackground);
            } else {
                this.setBackground(mFinishedBackground);
            }
        }

        mCurrentState = state;
    }

    public LoadingButtonState getCurrentState() {
        return mCurrentState;
    }


    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);

            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

    private void init(Context context) {
        mTVText = new TextView(context);
        mIVImage = new ImageView(context);
        mProgressBar = new ProgressBar(context);

        FrameLayout.LayoutParams tvParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);

        mTVText.setText(mText);
        if (mTextColor == mDefaultTextColor) {
            mTVText.setTextColor(mDefaultTextColor);
        } else {
            mTVText.setTextColor(mTextColor);
        }

        if (mFinishedIcon == null) {
            mIVImage.setImageDrawable(ContextCompat.getDrawable(getContext(), mDefaultFinishedIcon));
        } else {
            mIVImage.setImageDrawable(mFinishedIcon);
        }

        mIVImage.setVisibility(INVISIBLE);

        mProgressBar.setVisibility(INVISIBLE);

        mTVText.setTextSize(mTextSize);
        tvParams.topMargin = dpToPx(5);
        tvParams.leftMargin = dpToPx(10);
        tvParams.rightMargin = dpToPx(10);
        tvParams.bottomMargin = dpToPx(5);
        mTVText.setLayoutParams(tvParams);

        mTVText.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                        FrameLayout.LayoutParams ivAndPbParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
                        ivAndPbParams.height = mTVText.getHeight();
                        ivAndPbParams.width = mTVText.getHeight();
                        ivAndPbParams.setMargins(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));

                        mIVImage.setLayoutParams(ivAndPbParams);
                        mProgressBar.setLayoutParams(ivAndPbParams);

                        mTVText.getViewTreeObserver().removeOnGlobalLayoutListener( this );
                    }
                });

        if (mNormalBackground == null) {
            this.setBackgroundColor(mDefaultNormalBackground);
        } else {
            this.setBackground(mNormalBackground);
        }

        this.addView(mTVText);
        this.addView(mIVImage);
        this.addView(mProgressBar);
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    private int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }


    public enum LoadingButtonState {
        NORMAL, LOADING, FINISHED;
    }
}
