package com.foxysoft.smoothloadingbuttontest;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.foxysoft.smoothloadingbutton.LoadingButton;
import com.foxysoft.smoothloadingbutton.LoadingButton.LoadingButtonState;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private LoadingButton mBtnDoSomething;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnDoSomething = findViewById(R.id.btn_test);
        mBtnDoSomething.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (mBtnDoSomething.getCurrentState()) {
            case NORMAL:
                mBtnDoSomething.changeState(LoadingButtonState.LOADING);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBtnDoSomething.changeState(LoadingButtonState.FINISHED);
                    }
                }, 1000);
                break;
            case FINISHED:
                mBtnDoSomething.changeState(LoadingButtonState.NORMAL);
                break;
        }
    }

}
