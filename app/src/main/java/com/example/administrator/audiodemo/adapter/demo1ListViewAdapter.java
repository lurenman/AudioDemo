package com.example.administrator.audiodemo.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.audiodemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 */

public class demo1ListViewAdapter extends BaseAdapter implements MediaPlayer.OnCompletionListener
{
    private Context mContext;
    //播放音频的路径集合
    private List<String> mArrays=new ArrayList<String>();
    private Boolean isPlaying=false;//是否处于播放状态
    private MediaPlayer mPlayer;
    private long timeusedinsec=0;//
    private TextView mCurrentMint;
    private TextView mCurrentSec;
    private ImageView mCurrentIvPlaying;

    public demo1ListViewAdapter(Context context, List<String> array)
    {
        mArrays=array;
        mContext=context;
    }

    @Override
    public int getCount()
    {
        //mArrays.size()
        return mArrays.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup)
    {
        ViewHolder holder= null;
        if (convertView==null)
        {
            holder= new ViewHolder();
            convertView=View.inflate(mContext, R.layout.lv_demo1_item,null);
            holder.tv_audioname= (TextView) convertView.findViewById(R.id.tv_audioname);
            holder.iv_playing= (ImageView) convertView.findViewById(R.id.iv_playing);
            holder.mint= (TextView) convertView.findViewById(R.id.mint);
            holder.sec= (TextView) convertView.findViewById(R.id.sec);
            holder.bar=(ProgressBar)convertView.findViewById(R.id.bar);
            holder.tv_totaltime= (TextView) convertView.findViewById(R.id.tv_totaltime);

            convertView.setTag(holder);

        }else {
            holder=(ViewHolder)convertView.getTag();
        }
        if (mArrays!=null&&!mArrays.isEmpty())
        {
            final ViewHolder finalHolder = holder;
            holder.iv_playing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (isPlaying==false)
                    {
                        finalHolder.iv_playing.setImageResource(R.drawable.play);
                        mCurrentMint=finalHolder.mint;
                        mCurrentSec=finalHolder.sec;
                        mCurrentIvPlaying=finalHolder.iv_playing;
                        //这个时候开始播放录音
                        playing(mArrays.get(position));
                        mPlayHandler.removeMessages(1);
                        mPlayHandler.sendEmptyMessage(1);
                        //updateView(finalHolder.mint,finalHolder.sec);
                        isPlaying=true;
                    } else {
                        //停止播放录音
                        finalHolder.iv_playing.setImageResource(R.drawable.suspend);
                        mPlayer.stop();
                        mPlayHandler.sendEmptyMessage(0);
                        timeusedinsec=0;
                        isPlaying=false;
                    }
                }
            });
        }

        return convertView;
    }
    //开始播放音频
    private void playing(String audioSdpath)
    {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        try
        {
            mPlayer.setDataSource(audioSdpath);
            mPlayer.prepare();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mPlayer.start();
    }
    //更新播放时长时间的
    private void updateView() {
        timeusedinsec += 1;
        int minute = (int) (timeusedinsec / 60)%60;
        int second = (int) (timeusedinsec % 60);
        if (minute < 10)
            mCurrentMint.setText("0" + minute);
        else
            mCurrentMint.setText("" + minute);
        if (second < 10)
            mCurrentSec.setText("0" + second);
        else
            mCurrentSec.setText("" + second);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {
        mPlayHandler.removeMessages(1);
        mPlayHandler.sendEmptyMessage(0);
        mCurrentIvPlaying.setImageResource(R.drawable.suspend);
        timeusedinsec=0;
        isPlaying=false;

    }
    private Handler mPlayHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    // 添加更新ui的代码
                    if (isPlaying) {
                        updateView();
                        mPlayHandler.sendEmptyMessageDelayed(1, 1000);
                    }
                    break;
                case 0:
                    break;
            }
        }
    };
    static class ViewHolder
    {
        TextView tv_audioname;//音频的name
        ImageView iv_playing;//播放的按钮
        TextView mint;//分
        TextView sec;//秒
        ProgressBar bar;//进度条
        TextView tv_totaltime;//总共时间
    }
}
