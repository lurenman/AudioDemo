package com.example.administrator.audiodemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.administrator.audiodemo.adapter.demo2ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 */

public class demo2Activity extends Activity
{
    private demo2ListViewAdapter mListViewAdapter;


    //播放音频的路径集合
    private List<String> mArrays=new ArrayList<String>();
    private ListView lv_listview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        initVariables();
        lv_listview= (ListView) findViewById(R.id.lv_listview);
        mListViewAdapter=new demo2ListViewAdapter(getApplicationContext(),mArrays);
        lv_listview.setAdapter(mListViewAdapter);

    }
    private void initVariables()
    {
        //这块找一些音频文件或者本地音乐之类的（音频的长度，名字信息等）
        //添加assets文件
        mArrays.add("Hoaprox-Ngau Hung.mp3");
        mArrays.add("罗晋&唐嫣-天赋.mp3");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mListViewAdapter!=null)
        {
            mListViewAdapter.stopPlay();
        }
    }
}
