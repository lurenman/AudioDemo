package com.example.administrator.audiodemo.adapter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.audiodemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/23 0023.
 *
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

public class demo2ListViewAdapter extends BaseAdapter implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener
{
    private Context mContext;
    //播放音频的路径集合
    private List<String> mArrays=new ArrayList<String>();
    private int mCurrentPosition=-1;//当前播放条目索引
    private Boolean pause=false;
    private MediaPlayer mPlayer;
    private long timeusedinsec=0;//这个是计时初始值的
    private TextView mCurrentMint;//当前播放的分钟
    private TextView mCurrentSec;//当前播放的秒
    private ImageView mCurrentIvPlaying;//当前播放条目的播放按钮
    private TextView mCurrentTotaltime;
    private ProgressBar mProgressBar;

    public demo2ListViewAdapter(Context context, List<String> array)
    {
        mArrays=array;
        mContext=context;

    }
    @Override
    public int getCount()
    {
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
            holder.ll_root=(LinearLayout)convertView.findViewById(R.id.ll_root);
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
            holder.ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if (position==mCurrentPosition)
                  {
                      //说明这是刚才播放的条目
                      pause();
                      mPlayHandler.removeMessages(1);
                      mPlayHandler.sendEmptyMessage(1);

                  }else {
                      //这个时候说明点击了其他条目
                        stopPlay();
                        clearUpdateView();
                        timeusedinsec=0;
                        //这个选择其他条目的时候把上个条目改变图片
                        finalHolder.iv_playing.setImageResource(R.drawable.play);
                        mCurrentMint=finalHolder.mint;
                        mCurrentSec=finalHolder.sec;
                        mCurrentIvPlaying=finalHolder.iv_playing;
                        mProgressBar=finalHolder.bar;
                        mCurrentTotaltime=finalHolder.tv_totaltime;
                      //这个时候开始播放录音
                        playing(mArrays.get(position));
                        mPlayHandler.removeMessages(1);
                        mPlayHandler.sendEmptyMessage(1);
                        mCurrentPosition=position;
                  }

                }
            });



        }
        return convertView;
    }
    //开始播放音频
    private void playing(String assetsName)
    {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
        try
        {
//            MediaPlayer的setDataSource一共四个方法：
//            setDataSource (String path)
//            setDataSource (FileDescriptor fd)
//            setDataSource (Context context, Uri uri)
//            setDataSource (FileDescriptor fd, long offset, long length)
            AssetFileDescriptor fileDescriptor = mContext.getAssets().openFd(assetsName);
            mPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mPlayer.prepare();
            //mCurrentTotaltime.setText(Integer.toString(mPlayer.getDuration()));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mPlayer.start();
    }
    /**
     * 暂停
     */
    public boolean pause() {
        if (mPlayer.isPlaying()) {// 如果正在播放
            mPlayer.pause();// 暂停
            mCurrentIvPlaying.setImageResource(R.drawable.suspend);
            pause = true;
        } else {
            if (pause) {// 如果处于暂停状态
                mPlayer.start();// 继续播放
                mCurrentIvPlaying.setImageResource(R.drawable.play);
                pause = false;
            }
        }
        return pause;
    }
//停止播放
    public void stopPlay()
    {
        if (mPlayer!=null)
        {
            mPlayer.stop();
            //释放mediaplayer否则的话会占用内存
            mPlayer.release();
            mPlayer=null;
        }

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
    //改变上个播放条目的状态
  private void clearUpdateView()
  {
      if (mProgressBar!=null)
      {
          mProgressBar.setProgress(0);
      }

      if (mCurrentPosition!=-1&&mCurrentIvPlaying!=null)
      {
          mCurrentIvPlaying.setImageResource(R.drawable.suspend);
      }
      if ( mCurrentMint!=null&&mCurrentSec!=null)
      {
          mCurrentMint.setText("00");
          mCurrentSec.setText("00");
      }
  }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer)
    {
        mPlayHandler.removeMessages(1);
        mPlayHandler.sendEmptyMessage(0);
        mCurrentIvPlaying.setImageResource(R.drawable.suspend);
        timeusedinsec=0;
    }
    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int bufferingProgress)
    {
        if (mProgressBar!=null)
        {
//            mProgressBar.setSecondaryProgress(bufferingProgress);
//            int currentProgress = mProgressBar.getMax()
//                    * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
        }

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
             if (mPlayer!=null&&mPlayer.isPlaying())
             {
                 updateView();
                 //更新进度条
                 handleProgress.sendEmptyMessage(0);
                 mPlayHandler.sendEmptyMessageDelayed(1, 1000);
             }
                    break;
                case 0:
                    break;
            }
        }
    };
    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            int position = mPlayer.getCurrentPosition();
            int duration = mPlayer.getDuration();
            if (duration > 0) {
                if (mProgressBar!=null)
                {
                    long pos = mProgressBar.getMax() * position / duration;
                    mProgressBar.setProgress((int) pos);
                }
            }
        };
    };

    static class ViewHolder
    {
        LinearLayout ll_root;
        TextView tv_audioname;//音频的name
        ImageView iv_playing;//播放的按钮
        TextView mint;//分
        TextView sec;//秒
        ProgressBar bar;//进度条
        TextView tv_totaltime;//总共时间
    }
}
