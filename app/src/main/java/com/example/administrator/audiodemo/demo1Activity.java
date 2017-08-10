package com.example.administrator.audiodemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.administrator.audiodemo.adapter.demo1ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 * MediaPlayer的setDataSource一共四个方法：
 setDataSource (String path)
 setDataSource (FileDescriptor fd)
 setDataSource (Context context, Uri uri)
 setDataSource (FileDescriptor fd, long offset, long length)

 其中使用FileDescriptor时，需要将文件放到与res文件夹平级的assets文件夹里，然后使用：
 AssetFileDescriptor fileDescriptor = getAssets().openFd("rain.mp3");
 m_mediaPlayer.
 */

public class demo1Activity extends Activity
{
    private demo1ListViewAdapter mListViewAdapter;

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
        mListViewAdapter=new demo1ListViewAdapter(getApplicationContext(),mArrays);
        lv_listview.setAdapter(mListViewAdapter);

    }

    private void initVariables()
    {
        //这块找一些音频文件或者本地音乐之类的
       // mArrays.add()
    }
}
