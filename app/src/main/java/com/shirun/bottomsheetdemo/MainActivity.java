package com.shirun.bottomsheetdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    private BottomSheetBehavior<View> from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        from = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        from.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //这里是bottomSheet 状态的改变回调
                //newState 有四个状态 ：
                //展开 BottomSheetBehavior.STATE_EXPANDED
                //收起 BottomSheetBehavior.STATE_COLLAPSED
                //拖动 BottomSheetBehavior.STATE_DRAGGING
                //下滑 BottomSheetBehavior.STATE_SETTLING
                Log.d(TAG, "newState:" + newState);
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //这里是拖拽中的回调，slideOffset为0-1 完全收起为0 完全展开为1
                Log.d(TAG, "slideOffset:" + slideOffset);
            }
        });

    }

    public void click(View view) {
        if (from.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            from.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            from.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void toActivity(View view){
        startActivity(new Intent(MainActivity.this,BottomSheetActivity.class));
    }
}
