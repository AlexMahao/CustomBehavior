package com.spearbothy.custombehavior.behavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.spearbothy.custombehavior.R;

/**
 * Created by mahao on 17-7-20.
 */

public class BehaviorActivity extends AppCompatActivity {

    private View mHeaderView;

    private int mLastY = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);

        mHeaderView = findViewById(R.id.header);
        mHeaderView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int y = (int) event.getRawY();
                        mHeaderView.setTranslationY(mHeaderView.getTranslationY() + y - mLastY);
                        mLastY = y;
                        break;
                }
                return true;
            }
        });

    }
}
