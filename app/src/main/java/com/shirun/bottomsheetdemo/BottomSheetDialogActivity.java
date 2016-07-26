package com.shirun.bottomsheetdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================================
 * <p>
 * 作    者 : ying
 * <p>
 * 创建时间 ： 2016/7/26.
 * <p>
 * 用   途 :
 * <p>
 * <p>
 * ==========================================
 */
public class BottomSheetDialogActivity extends AppCompatActivity{
    private List<String> mList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet);
        initData();
    }

    private void initData() {
        mList = new ArrayList<>();
        for(int i=0; i<20; i++){
            mList.add("item "+i);
        }
    }

    public void click1(View view){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        //创建recyclerView
        RecyclerView recyclerView = new RecyclerView(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(mList,this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View item, int position) {
                Toast.makeText(BottomSheetDialogActivity.this, "item "+position, Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(recyclerView);
        bottomSheetDialog.show();
    }
    public void click2(View view){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View inflate = View.inflate(this, R.layout.bottomsheetdalig_layout, null);
        View qq = inflate.findViewById(R.id.share_qq);
        View wx = inflate.findViewById(R.id.share_wx);
        View sina = inflate.findViewById(R.id.share_sina);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetDialogActivity.this, "分享到qq", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetDialogActivity.this, "分享到wx", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BottomSheetDialogActivity.this, "分享到sina", Toast.LENGTH_SHORT).show();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.setContentView(inflate);
        bottomSheetDialog.show();
    }


}
